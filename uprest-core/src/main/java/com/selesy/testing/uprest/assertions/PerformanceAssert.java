package com.selesy.testing.uprest.assertions;

import org.assertj.core.api.AbstractAssert;

import com.selesy.testing.uprest.http.Performance;

public class PerformanceAssert extends AbstractAssert<PerformanceAssert, Performance> {

  protected PerformanceAssert(Performance actual, Class<?> selfType) {
    super(actual, selfType);
    // TODO Auto-generated constructor stub
  }

  public PerformanceAssert(Performance actual) {
    super(actual, PerformanceAssert.class);
  }

  public static PerformanceAssert assertThat(Performance actual) {
    return new PerformanceAssert(actual);
  }

  public PerformanceAssert isFasterThan(long expected) {
    isNotNull();

//    if (actual.getRoundTripInNanoSeconds() >= expected) {
//      // TODO - failWithMessage() here
//    }

    return this;
  }

  public PerformanceAssert isFasterThanOrEqualTo(long expected) {
    isNotNull();

//    if (actual.getRoundTripInNanoSeconds() > expected) {
//      // TODO - failWithMessage() here
//    }

    return this;
  }

}
