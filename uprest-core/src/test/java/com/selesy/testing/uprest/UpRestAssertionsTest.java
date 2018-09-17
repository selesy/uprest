package com.selesy.testing.uprest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.http.StatusLine;
import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.selesy.testing.uprest.assertions.PerformanceAssert;
import com.selesy.testing.uprest.assertions.StatusLineAssert;
import com.selesy.testing.uprest.http.Performance;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpRestAssertionsTest {

  @Value
  static class ObjectAndAssert {

    Class<?> objectClazz;
    Class<? extends AbstractAssert<?, ?>> assertClazz;

  }

  static final ObjectAndAssert[] ASSERTIONS = {
      new ObjectAndAssert(Performance.class, PerformanceAssert.class),
      new ObjectAndAssert(StatusLine.class, StatusLineAssert.class)
  };

  Iterator<ObjectAndAssert> inputGenerator = Arrays.asList(ASSERTIONS).iterator();

  Function<ObjectAndAssert, String> displayNameGenerator = (t) -> t.getObjectClazz().getSimpleName() + " --> " + t.getAssertClazz().getSimpleName();

  Consumer<ObjectAndAssert> testExecutor = (t) -> {
    log.debug("What's t: {}", t);
    log.debug("What's t: {}", t.getClass().getSimpleName());
    try {
      Object object = t.getObjectClazz().newInstance();
      assertThat(object).isNotNull();
      //AbstractAssert<?, ?> abstractAssert = UpRestAssertions.assertThat(object);
    } catch (Throwable e) {
      fail(e.getLocalizedMessage());
    }
    // Class<?> clazz = t.newInstance();
    // AbstractAssert<?, ?> abstractAssert = UpRestAssertions.assertThat(clazz);
    // UpRestAssertions.assertThat(clazz). isNotNull();
  };

//  @TestFactory
//  public Stream<DynamicTest> testAssertThat() {
//    return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
//  }

}
