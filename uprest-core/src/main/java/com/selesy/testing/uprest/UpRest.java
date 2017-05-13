package com.selesy.testing.uprest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import com.selesy.testing.uprest.internal.UpRestInvocationContextProvider;

/**
 * The UpRest meta-annotation extends the TestTemplate annotation to expand the
 * number of executed tests and to register parameter resolvers that are
 * specific to REST testing.
 *
 * TODO - Example code
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@TestTemplate
@ExtendWith(UpRestInvocationContextProvider.class)
public @interface UpRest {

}
