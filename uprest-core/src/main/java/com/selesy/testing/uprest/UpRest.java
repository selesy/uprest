package com.selesy.testing.uprest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.gen5.api.extension.ExtendWith;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@ExtendWith({
  UpRestOld.class
})
public @interface UpRest {

}
