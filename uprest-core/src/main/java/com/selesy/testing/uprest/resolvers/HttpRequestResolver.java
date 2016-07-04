/**
 * 
 */

package com.selesy.testing.uprest.resolvers;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.annotations.Headers;
import com.selesy.testing.uprest.annotations.Methods;
import com.selesy.testing.uprest.annotations.Paths;
import com.selesy.testing.uprest.configuration.Configuration;
import com.selesy.testing.uprest.configuration.Constants;
import com.selesy.testing.uprest.http.Method;
import com.selesy.testing.uprest.utilities.AnnotationReflectionUtility;
import com.selesy.testing.uprest.utilities.LoggingUtils;
import com.selesy.testing.uprest.utilities.StoreUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Builds an HttpRequest instance based on the values of the @Methods, @Paths,
 * 
 * @Headers and @EntityBodies annotations on the test method. Some of these
 *          annotations can be inherited from the enclosing type and
 *          super-classes. Values that may be environment specific will also be
 *          retrieved from the Configuration object.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class HttpRequestResolver implements ParameterResolver {

  public static final String STORE_KEY_HTTP_REQUEST = "HttpRequest";
  public static final Class<?> SUPPORTED_TYPE = HttpRequest.class;

  static final String HEADER_SPLITING_REGEX = "^([^:]+):(.+)$";

  Pattern pattern = Pattern.compile(HEADER_SPLITING_REGEX);

  /**
   * Resolves an HttpRequest (actually an HttpUriRequest using the annotations
   * decorating the method specified as part of the passed method invocation
   * context.
   * 
   * @param extensionContext
   *          The ExtensionContext.
   * @return The HttpRequest object needed for the test.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   *      junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("resolve()");
    
    Store store = StoreUtils.getStoreNamespacedByUniqueId(extensionContext);
    return store.getOrComputeIfAbsent(Constants.STORE_KEY_HTTP_REQUEST, (key) -> {
      // getAppropriateHttpRequestType()
      // getMethods()
      // getHeaders()
      // getEntityBodies()
      // getPaths()
      return (HttpRequest) null;
    });

    // TODO - Figure out an elegant way of u
    // Optional<Method> optionalMethod = ec.getTestMethod();
    // log.debug("Test name: {}", ec.getTestMethod().getName());
    HttpUriRequest httpUriRequest = null;

    // Get the HTTP methods
    Method[] methods = { Method.GET };
    Optional<Methods> optionalMethods = AnnotationReflectionUtility.getMethodAnnotation(Methods.class,
        extensionContext);
    if (optionalMethods.isPresent()) {
      methods = optionalMethods.get().value();
    }
    log.debug("Methods: {}", LoggingUtils.prettyPrint(methods));

    // Get the HTTP request headers
    Header[] headers = {};
    Optional<Headers> optionalHeaders = AnnotationReflectionUtility.getMethodAnnotation(Headers.class,
        extensionContext);
    if (optionalHeaders.isPresent()) {
      headers = convertHeaders(optionalHeaders.get().value());
    }
    log.debug("Headers: {}", LoggingUtils.prettyPrint(headers, (h) -> h.getName() + ":" + h.getValue()));

    // Get the annotated paths (which will be the suffix of the test URI
    String[] paths = permutePaths(extensionContext);
    log.debug("Permuted path count: {}", paths.length);
    log.debug("Paths:  {}", LoggingUtils.prettyPrint(paths));

    // Build the HttpRequest
    if (methods.length > 0 && paths.length > 0) {
      try {
        httpUriRequest = methods[0].getHttpUriRequest(paths[0]);
        httpUriRequest.setHeaders(headers);
      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
          | IllegalArgumentException | InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    // Store the HttpRequest in the ExtensionContext so it's available
    // during
    // the resolution of other parameters.
    if (httpUriRequest != null) {
      store = StoreUtils.getStoreNamespacedByUniqueId(extensionContext);
      store.put(Constants.STORE_KEY_HTTP_REQUEST, httpUriRequest);
    }

    return httpUriRequest;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.junit.gen5.api.extension.ParameterResolver#supports(org.junit.gen5.api.
   * extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return parameterContext.getParameter().getType().equals(HttpRequest.class);
  }

  /**
   * Converts the String formated headers provided by the @Headers annotation
   * into an array of Apache HTTP client Header objects.
   * 
   * @param headers
   *          The String formated headers.
   * @return An array of Header objects.
   */
  Header[] convertHeaders(String[] headers) {
    log.trace("convertHeaders()");

    return Stream.of(headers).map((h) -> {
      Matcher matcher = pattern.matcher(h);
      log.debug("Header string matches pattern: {}", matcher.matches());
      return new BasicHeader(matcher.group(1), matcher.group(2));
    }).collect(Collectors.toList()).toArray(new Header[0]);
  }
  
  /**
   * Returns the list of URI suffixes returned from an @Path annotation or the
   * defaults.
   * 
   * @param optionalPaths
   *          The potential test URIs.
   * @param defaults
   *          The defaults to use if there are no paths.
   * @return Either the @Paths or the defaults.
   */
  String[] getPathListOrDefaults(Optional<Paths> optionalPaths, String... defaults) {
    log.trace("getPathListOrDefaults()");

    String[] paths = defaults;
    if (optionalPaths.isPresent()) {
      paths = optionalPaths.get().value();
    }
    log.debug("Paths: {}", LoggingUtils.prettyPrint(paths));

    return paths;
  }

  /**
   * Generates a list of String URIs that contains every permutation of base
   * paths, type paths and method paths.
   * 
   * @param extensionContext
   *          The test's MethodInvocationContext.
   * @return The List<String> containing the resulting path URIs.
   */
  String[] permutePaths(ExtensionContext extensionContext) {
    log.trace("permutePaths()");

    // Get the list of base paths
    String[] basePaths = { Configuration.getBasePath() };
    log.debug("Base paths: {}", LoggingUtils.prettyPrint(basePaths));

    // Get a list of type paths (which at a minimum will contain "")
    Optional<Paths> optionalTypePaths = AnnotationReflectionUtility.getTypeAnnotation(Paths.class,
        extensionContext);
    final String[] typePaths = getPathListOrDefaults(optionalTypePaths, "");
    log.debug("Type paths: {}", LoggingUtils.prettyPrint(typePaths));

    // Get list of method paths (which at a minimum will contain "")
    Optional<Paths> optionalMethodPaths = AnnotationReflectionUtility.getMethodAnnotation(Paths.class,
        extensionContext);
    final String[] methodPaths = getPathListOrDefaults(optionalMethodPaths, "");
    log.debug("Method paths: {}", LoggingUtils.prettyPrint(methodPaths));

    // Create a list with all the path permutations
    return Stream.of(basePaths)
        .flatMap((p) -> Stream.of(typePaths)
            .map((t) -> p + t))
        .flatMap((p) -> Stream.of(methodPaths)
            .map((m) -> p + m))
        .collect(Collectors.toList())
        .toArray(new String[0]);
  }

}
