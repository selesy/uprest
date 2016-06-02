package com.selesy.testing.uprest;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolutionException;
import org.junit.gen5.api.extension.ParameterResolver;

import com.selesy.testing.uprest.annotations.EntityBody;
import com.selesy.testing.uprest.http.Performance;
import com.selesy.testing.uprest.resolvers.ByteArrayEntityBodyResolver;
import com.selesy.testing.uprest.resolvers.ChainableParameterResolver;
import com.selesy.testing.uprest.resolvers.EntityBodyResolver;
import com.selesy.testing.uprest.resolvers.HttpRequestResolver;
import com.selesy.testing.uprest.resolvers.HttpResponseResolver;
import com.selesy.testing.uprest.resolvers.NullResolver;
import com.selesy.testing.uprest.resolvers.PerformanceResolver;
import com.selesy.testing.uprest.resolvers.StatusLineResolver;
import com.selesy.testing.uprest.resolvers.StringEntityBodyResolver;
import com.selesy.testing.uprest.utilities.BodyAnnotationProcessing;

import lombok.extern.slf4j.Slf4j;

/**
 * A JUnit5 extension that allows HTTP and REST testing to be defined
 * declaratively.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class UpRestOld implements ParameterResolver {

  public static final String STORE_KEY_ENTITY_BODY = "EntityBody";
  public static final String STORE_KEY_HTTP_REQUEST = "HttpRequest";
  public static final String STORE_KEY_HTTP_RESPONSE = "HttpResponse";
  public static final String STORE_KEY_PERFORMANCE = "Performance";
  public static final String STORE_KEY_STATUS_LINE = "StatusLine";

  static final Map<Class<?>, Class<? extends ChainableParameterResolver>> SUPPORTED_PARAMETER_CLASSES = new HashMap<>();
  static final Map<Class<?>, Class<? extends EntityBodyResolver>> SUPPORTED_BODY_CLASSES = new HashMap<>();

  static {
    // Parameter resolvers
    SUPPORTED_PARAMETER_CLASSES.put(HttpRequest.class, HttpRequestResolver.class);
    SUPPORTED_PARAMETER_CLASSES.put(HttpResponse.class, HttpResponseResolver.class);
    SUPPORTED_PARAMETER_CLASSES.put(Performance.class, PerformanceResolver.class);
    SUPPORTED_PARAMETER_CLASSES.put(StatusLine.class, StatusLineResolver.class);

    // @EntityBody parameter resolvers
    SUPPORTED_BODY_CLASSES.put(byte[].class, ByteArrayEntityBodyResolver.class);
    // SUPPORTED_BODY_CLASSES.put(CharSequence.class,
    // CharSequenceEntityBodyResolver.class);
    SUPPORTED_BODY_CLASSES.put(String.class, StringEntityBodyResolver.class);

  }

  /**
   * Resolves the requested parameter if possible.
   * 
   * @param parameter
   *          The parameter that should be resolved.
   * @param mic
   *          The MethodInvocationContext.
   * @param extensionContext
   *          The ExtensionContext.
   * @return The resolved parameter.
   * 
   * @see org.junit.gen5.api.extension.MethodParameterResolver#resolve(java.lang.
   *      reflect.Parameter,
   *      org.junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(Parameter parameter, Optional<Object> target, ExtensionContext extensionContext) throws ParameterResolutionException {
    log.trace("resolve()");

    Class<?> parameterClass = parameter.getType();
    log.debug("Class name: {}", parameterClass.getName());

    ChainableParameterResolver resolver = new NullResolver();
    if (SUPPORTED_PARAMETER_CLASSES.containsKey(parameterClass)) {
      try {
        resolver = SUPPORTED_PARAMETER_CLASSES.get(parameterClass).newInstance();
      } catch (InstantiationException e) {
        log.error("Could not instantiate parameter resolver class.");
      } catch (IllegalAccessException e) {
        log.error("Could not access parameter resolver class.");
      }

    } else if (SUPPORTED_BODY_CLASSES.containsKey(parameterClass)) {
      if (parameter.isAnnotationPresent(EntityBody.class)) {
        try {
          resolver = SUPPORTED_BODY_CLASSES.get(parameterClass).newInstance();
        } catch (InstantiationException e) {
          log.error("Could not instantiate parameter resolver class.");
        } catch (IllegalAccessException e) {
          log.error("Could not access parameter resolver class.");
        }
      }
    }
    log.debug("Parameter resolver class: {}", resolver.getClass().getName());

    return resolver.resolve(extensionContext);
  }

  /**
   * Indicates whether this MethodParameterResolver supports the classes listed
   * above (related to HTTP/REST testing).
   * 
   * @param parameter
   *          The parameter that should be resolved.
   * @param mic
   *          The MethodInvocationContext.
   * @param ec
   *          The ExtensionContext.
   * @return A boolean indicating whether this MethodParameterResolver can
   *         resolve the specified parameter.
   * 
   * @see org.junit.gen5.api.extension.MethodParameterResolver#supports(java.lang.
   *      reflect.Parameter,
   *      org.junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(Parameter parameter, Optional<Object> object, ExtensionContext ec) throws ParameterResolutionException {
    log.trace("supports()");

    if (log.isDebugEnabled()) {
      log.debug("Supported parameter: {}", SUPPORTED_PARAMETER_CLASSES.containsKey(parameter.getType()));
      log.debug("Supported @EntityBody: {}", BodyAnnotationProcessing.supportsBody(parameter, SUPPORTED_BODY_CLASSES.keySet()));
    }

    return SUPPORTED_PARAMETER_CLASSES.containsKey(parameter.getType()) || BodyAnnotationProcessing.supportsBody(parameter, SUPPORTED_BODY_CLASSES.keySet());
  }

}