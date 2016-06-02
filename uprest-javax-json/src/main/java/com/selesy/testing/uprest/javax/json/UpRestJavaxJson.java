package com.selesy.testing.uprest.javax.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.gen5.api.extension.ExtendWith;

import com.selesy.testing.uprest.UpRestOld;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@ExtendWith({
  UpRestOld.class,
  UpRestJavaxJsonOld.class
})
public @interface UpRestJavaxJson {

}
