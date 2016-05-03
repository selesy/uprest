/**
 * 
 */
package com.selesy.testing.uprest.javax.json;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.MethodInvocationContext;
import org.junit.gen5.api.extension.MethodParameterResolver;
import org.junit.gen5.api.extension.ParameterResolutionException;

import com.selesy.testing.uprest.javax.json.resolvers.JsonArrayEntityBodyResolver;
import com.selesy.testing.uprest.javax.json.resolvers.JsonObjectEntityBodyResolver;
import com.selesy.testing.uprest.resolvers.ChainableParameterResolver;
import com.selesy.testing.uprest.resolvers.NullResolver;
import com.selesy.testing.uprest.utilities.BodyAnnotationProcessing;

import lombok.extern.slf4j.Slf4j;

/**
 * Supports resolution of JsonObject and JsonArray parameters that are annotated
 * with @EntityBody to allow javax-json processing.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class UpRestJavaxJson implements MethodParameterResolver {

  public static final Map<Class<?>, Class<? extends ChainableParameterResolver>> SUPPORTED_BODY_CLASSES = new HashMap<>();

  static {
    SUPPORTED_BODY_CLASSES.put(JsonArray.class, JsonArrayEntityBodyResolver.class);
    SUPPORTED_BODY_CLASSES.put(JsonObject.class, JsonObjectEntityBodyResolver.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.junit.gen5.api.extension.MethodParameterResolver#resolve(java.lang.
   * reflect.Parameter, org.junit.gen5.api.extension.MethodInvocationContext,
   * org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(Parameter parameter, MethodInvocationContext mic, ExtensionContext ec) throws ParameterResolutionException {
    log.trace("resolve()");

    Class<?> parameterClass = parameter.getType();
    log.debug("Class name: {}", parameterClass.getName());

    ChainableParameterResolver resolver = new NullResolver();
    if (SUPPORTED_BODY_CLASSES.containsKey(parameterClass)) {
      try {
        resolver = SUPPORTED_BODY_CLASSES.get(parameterClass).newInstance();
      } catch (IllegalAccessException e) {
        log.error("Could not access parameter resolver class.");
      } catch (InstantiationException e) {
        log.error("Could not instantiate parameter resolver class.");
      }
    }
    log.debug("Body parameter resolver class: {}", resolver.getClass().getName());

    return resolver.resolve(mic, ec);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.junit.gen5.api.extension.MethodParameterResolver#supports(java.lang.
   * reflect.Parameter, org.junit.gen5.api.extension.MethodInvocationContext,
   * org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(Parameter parameter, MethodInvocationContext arg1, ExtensionContext arg2) throws ParameterResolutionException {
    return BodyAnnotationProcessing.supportsBody(parameter, SUPPORTED_BODY_CLASSES.keySet());
  }

}
