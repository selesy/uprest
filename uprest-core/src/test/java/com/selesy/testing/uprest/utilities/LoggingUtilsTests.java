package com.selesy.testing.uprest.utilities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Provides tests for the LoggingUtils.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class LoggingUtilsTests {

  enum enumInput {
    ONE, TWO, THREE;
  };

  static final String EXPECTED = "ONE, TWO, THREE";
  static final String[] STRING_INPUT = { "ONE", "TWO", "THREE" };

  /**
   * Verifies that enum names are properly printed to a concatenated String.
   */
  @Test
  public void testPrettyPrintEnumOfQArray() {
    String actual = LoggingUtils.prettyPrint(enumInput.values());
    assertThat(actual).isEqualTo(EXPECTED);
  }

  /**
   * Verifies that arbitrary arrays are properly printed to a concatenated
   * String using the passed conversion function.
   */
  @Test
  public void testPrettyPrintTArrayFunctionOfTString() {
    String actual = LoggingUtils.prettyPrint(enumInput.values(), (v) -> v.name());
    assertThat(actual).isEqualTo(EXPECTED);
  }

  /**
   * Verifies that a String[] is properly converted to an inline String.
   */
  @Test
  public void testPrettyPrintStringArray() {
    String actual = LoggingUtils.prettyPrint(STRING_INPUT);
    assertThat(actual).isEqualTo(EXPECTED);
  }

}
