/**
 * 
 */
package com.selesy.testing.uprest.javax.json;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
 * Supports resolution of JsonObject and JsonArray parameters that are
 * annotated with @EntityBody.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class UpRestJavaxJson implements MethodParameterResolver {
  
  public static final Set<Class<?>> SUPPORTED_BODY_CLASSES = new HashSet<>(Arrays.asList(
      JsonArray.class,
      JsonObject.class
  ));
  
  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.MethodParameterResolver#resolve(java.lang.reflect.Parameter, org.junit.gen5.api.extension.MethodInvocationContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(Parameter parameter, MethodInvocationContext mic, ExtensionContext ec) throws ParameterResolutionException {
    log.trace("resolve()");

    String className = parameter.getType().getName();
    log.debug("Class name: {}", className);
    ChainableParameterResolver resolver = new NullResolver();

    switch (className) {
    case "javax.json.JsonObject":
      resolver = new JsonObjectEntityBodyResolver();
      break;
    case "javax.json.JsonArray":
      resolver = new JsonArrayEntityBodyResolver();
      break;
    default:
      break;
    }

    return resolver.resolve(mic, ec);
  }

  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.MethodParameterResolver#supports(java.lang.reflect.Parameter, org.junit.gen5.api.extension.MethodInvocationContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(Parameter parameter, MethodInvocationContext arg1, ExtensionContext arg2) throws ParameterResolutionException {
    return BodyAnnotationProcessing.supportsBody(parameter, SUPPORTED_BODY_CLASSES);
  }

}
