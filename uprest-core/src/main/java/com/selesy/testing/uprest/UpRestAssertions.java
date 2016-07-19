package com.selesy.testing.uprest;

import org.apache.http.StatusLine;

import com.selesy.testing.uprest.assertions.PerformanceAssert;
import com.selesy.testing.uprest.assertions.StatusLineAssert;
import com.selesy.testing.uprest.http.Performance;

/**
 * A collection of fluent (AssertJ) style assertions that provide a single
 * point of access for the static execution of assertThat() methods for
 * upREST specific classes.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class UpRestAssertions {
  
  public static PerformanceAssert assertThat(Performance actual) {
    return PerformanceAssert.assertThat(actual);
  }
  
  /**
   * Delegates the creation of a StatusLineAssert object for verifying
   * StatusLine objects.
   * 
   * @param actual The target StatusLine object.
   * @return A StatusLineAssert object for method chaining.
   */
  public static StatusLineAssert assertThat(StatusLine actual) {
    return StatusLineAssert.assertThat(actual);
  }

}
