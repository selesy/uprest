package com.selesy.testing.uprest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows a test to be annotated with the set of HTTP headers that should be
 * sent with every HTTP request.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Headers {
  
  /**
   * An array of Strings representing HTTP headers in the common colon
   * separated form - "Accept:application/json".
   * 
   * @return The array of header Strings.
   */
  String[] value();

}
