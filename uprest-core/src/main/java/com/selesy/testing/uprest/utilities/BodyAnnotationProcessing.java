package com.selesy.testing.uprest.utilities;

import java.lang.reflect.Parameter;
import java.util.Set;

import com.selesy.testing.uprest.annotations.EntityBody;

/**
 * Utility methods to make @Body annotation processing easier.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public final class BodyAnnotationProcessing {

  private BodyAnnotationProcessing() {
    // Make this a utility class
  }

  /**
   * Indicates that support for method parameter resolution is available if the
   * parameter's type is included in the provided set of supported classes and
   * if the parameter is annotated with the @Body annotation.
   * 
   * @param parameter
   *          The parameter that JUnit5 wishes to resolve.
   * @param supportedBodyClasses
   *          The list of classes that can be resolved.
   * @return True if the requested parameter can be resolved, otherwise false.
   */
  public static boolean supportsBody(Parameter parameter, Set<Class<?>> supportedBodyClasses) {
    boolean supported = false;

    if (parameter != null) {
      if (supportedBodyClasses.contains(parameter.getType())) {
        if (parameter.isAnnotationPresent(EntityBody.class)) {
          supported = true;
        }
      }
    }

    return supported;
  }

}
