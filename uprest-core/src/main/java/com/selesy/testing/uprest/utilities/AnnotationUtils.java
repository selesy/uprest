package com.selesy.testing.uprest.utilities;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.extension.ParameterContext;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AnnotationUtils {
  
  @Nonnull
  public static <T extends Annotation> T[] getAnnotation(ParameterContext parameterContext, Class<T> annotationType) {
    return parameterContext.getParameter().getAnnotationsByType(annotationType);
  }

  public static boolean isAnnotationPresent(ParameterContext parameterContext, Class<? extends Annotation> annotationType) {
    return parameterContext.getParameter().isAnnotationPresent(annotationType);
  }

}
