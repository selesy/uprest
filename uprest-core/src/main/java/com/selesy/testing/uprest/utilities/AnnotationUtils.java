package com.selesy.testing.uprest.utilities;

import java.lang.annotation.Annotation;

import org.junit.gen5.api.extension.ParameterContext;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AnnotationUtils {

  public static boolean isAnnotationPresent(ParameterContext parameterContext, Class<? extends Annotation> annotationType) {
    return parameterContext.getParameter().isAnnotationPresent(annotationType);
  }

}
