/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.api.extension.MethodInvocationContext;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.annotations.Methods;
import com.selesy.testing.uprest.annotations.Paths;
import com.selesy.testing.uprest.configuration.Configuration;
import com.selesy.testing.uprest.http.Method;
import com.selesy.testing.uprest.utilities.AnnotationReflectionUtility;

import lombok.extern.slf4j.Slf4j;

/**
 * @author smoyer1
 *
 */
@Slf4j
public class HttpRequestResolver implements ChainableParameterResolver {

  @Override
  public Object resolve(MethodInvocationContext mic, ExtensionContext ec) {
    log.trace("resolve()");
    log.debug("Test name: {}", mic.getMethod().getName());
    HttpUriRequest httpUriRequest = null;

    Method[] methods = { Method.GET };
    Optional<Methods> optionalMethods = AnnotationReflectionUtility.getMethodAnnotation(Methods.class, mic);
    if (optionalMethods.isPresent()) {
      methods = optionalMethods.get().value();
    }
    log.debug("Methods: {}", getEnumNameList(methods));

    String[] paths = permutePaths(mic);
    log.debug("Permuted path count: {}", paths.length);
    log.debug("Paths:  {}", getJoinedArrayOfStrings(paths));

    if (methods.length > 0 && paths.length > 0) {
      try {
        httpUriRequest = methods[0].getHttpUriRequest(paths[0]);
      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    if (httpUriRequest != null) {
      Store store = ec.getStore();
      store.put(UpRest.STORE_KEY_HTTP_REQUEST, httpUriRequest);
    }

    return httpUriRequest;
  }

  String getEnumNameList(Enum<?>[] values) {
    return Arrays.stream(values)
        .map((x) -> x.name())
        .collect(Collectors.joining(", "));
  }

  String getJoinedArrayOfStrings(String[] members) {
    return Arrays.stream(members)
        .collect(Collectors.joining(", "));
  }

  String[] getPathListOrDefaults(Optional<Paths> optionalPaths, String... defaults) {
    log.trace("getPathListOrDefaults()");

    String[] paths = defaults;
    if (optionalPaths.isPresent()) {
      paths = optionalPaths.get().value();
    }
    log.debug("Paths: {}", getJoinedArrayOfStrings(paths));

    return paths;
  }

  /**
   * Generates a list of String URIs that contains every permutation of
   * base paths, type paths and method paths.
   * 
   * @param mic The test's MethodInvocationContext.
   * @return The List<String> containing the resulting path URIs.
   */
  String[] permutePaths(MethodInvocationContext mic) {
    log.trace("permutePaths()");

    // Get the list of base paths
    String[] basePaths = { Configuration.getBasePath() };
    log.debug("Base paths: {}", getJoinedArrayOfStrings(basePaths));

    // Get a list of type paths (which at a minimum will contain "")
    Optional<Paths> optionalTypePaths = AnnotationReflectionUtility.getTypeAnnotation(Paths.class, mic);
    final String[] typePaths = getPathListOrDefaults(optionalTypePaths, "");
    log.debug("Type paths: {}", getJoinedArrayOfStrings(typePaths));

    // Get  list of method paths (which at a minimum will contain "")
    Optional<Paths> optionalMethodPaths = AnnotationReflectionUtility.getMethodAnnotation(Paths.class, mic);
    final String[] methodPaths = getPathListOrDefaults(optionalMethodPaths, "");
    log.debug("Method paths: {}", getJoinedArrayOfStrings(methodPaths));

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
