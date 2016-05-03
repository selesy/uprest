package com.selesy.testing.uprest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A "marker annotation" that indicates the annotated method parameter should be
 * parsed from the HttpResponse's entity body.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface EntityBody {

}
