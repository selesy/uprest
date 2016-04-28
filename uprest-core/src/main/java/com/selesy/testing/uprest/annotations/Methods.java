package com.selesy.testing.uprest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.selesy.testing.uprest.http.Method;

/**
 * Allows a test writer to specify which HTTP methods should be used to build
 * HTTP requests for tests.
 * 
 * @author
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Methods {

  /**
   * Allows the declarative specification of the HTTP methods that should
   * generate a test (or sub-tests) for this test case.
   * 
   * @return The list of HTTP methods applied to this test case.
   */
  Method[] value();

}
