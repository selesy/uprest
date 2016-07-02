package com.selesy.testing.uprest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import com.selesy.testing.uprest.resolvers.PerformanceResolver;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@ExtendWith({
  PerformanceResolver.class,
})
public @interface UpRest {

}
