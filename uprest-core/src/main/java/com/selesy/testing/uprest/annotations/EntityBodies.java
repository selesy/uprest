package com.selesy.testing.uprest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows a test to be annotated with a set of HTTP entity bodies (represented
 * as Strings) that should be permuted with the @Paths, @Methods and @Headers
 * to create a list of test cases.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface EntityBodies {

  String[] value();

}
