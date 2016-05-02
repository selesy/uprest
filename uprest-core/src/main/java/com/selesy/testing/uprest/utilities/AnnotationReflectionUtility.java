/**
 * 
 */
package com.selesy.testing.uprest.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.gen5.api.extension.MethodInvocationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides utility methods to make retrieving annotation easier. This also
 * avoids the problem of mixing the reflection Method class and the HTTP Method
 * class.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class AnnotationReflectionUtility {

  private AnnotationReflectionUtility() {
    // This is a utility class
  }

  /**
   * Returns an Optional containing the requested annotation from the Method
   * described by the MethodInvocation context.
   * 
   * @param annotationClass
   *          The class of the desired annotation.
   * @param mic
   *          The MethodInvocationContext to search for the annotation.
   * @return An Optional containing the Annotation (if present).
   */
  public static <T extends Annotation> Optional<T> getMethodAnnotation(Class<T> annotationClass, MethodInvocationContext mic) {
    log.trace("getMethodAnnotation()");

    Method method = mic.getMethod();
    log.debug("Method class: {}", method.getClass().getName());
    log.debug("Method name: {}", method.getName());

    return getOptionalAnnotation(annotationClass, method);
  }

  /**
   * Returns an Optional containing the requested annotation from the Class of
   * the Method described by the MethodInvocationContext.
   * 
   * @param annotationClass
   *          The class of the desired annotation.
   * @param mic
   *          The MethodInvocationContext to search for the annotation.
   * @return An Optional containing the Annotation (if present).
   */
  public static <T extends Annotation> Optional<T> getTypeAnnotation(Class<T> annotationClass, MethodInvocationContext mic) {
    log.trace("getTypeAnnotation()");

    Method method = mic.getMethod();
    Class<?> type = method.getDeclaringClass();
    log.debug("Type class: {}", type.getName());

    return getOptionalAnnotation(annotationClass, method);
  }

  // Abstracts the work of actually retrieving the requested annotation and
  // getting it into an Optional.
  static <T extends Annotation> Optional<T> getOptionalAnnotation(Class<T> annotationClass, AnnotatedElement annotatedElement) {
    log.trace("getOptionalAnnotation()");
    log.debug("Annotation class: {}", annotationClass.getName());
    log.debug("Annotation present: {}", annotatedElement.isAnnotationPresent(annotationClass));
    T annotation = annotatedElement.getDeclaredAnnotation(annotationClass);
    return Optional.ofNullable(annotation);
  }

}
