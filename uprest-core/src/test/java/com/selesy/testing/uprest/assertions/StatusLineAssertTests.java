package com.selesy.testing.uprest.assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.selesy.testing.uprest.http.StatusFamily;

/**
 * Tests of the fluent assertions provided for the StatusLine type.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class StatusLineAssertTests {

  StatusLineAssert statusLineAssert;

  /**
   * We need a loaded assertion object for almost all the tests.
   */
  @BeforeEach
  public void beforeEach() {
    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    StatusLine actual = new BasicStatusLine(protocolVersion, 201, "Created");
    statusLineAssert = StatusLineAssert.assertThat(actual);
  }

  /**
   * Verify we pass the assertion when the protocol versions match.
   */
  @Test
  public void testHasProtocolVersionWithMatch() {
    ProtocolVersion expected = new ProtocolVersion("HTTP", 1, 1);
    statusLineAssert.hasProtocolVersion(expected);
  }

  /**
   * Verify we throw an exception when the protocol versions are different.
   */
  @Test
  public void testHasProtocolVersionWithDifference() {
    ProtocolVersion expected = new ProtocolVersion("HTTP", 1, 0);
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasProtocolVersion(expected);
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP protocol version to be");
  }

  /**
   * Verify we throw an exception when the expected protocol version is null
   * (since the Apache HTTP client library doesn't allow null actuals).
   */
  @Test
  public void testHasProtocolVersionWithNullExpected() {
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasProtocolVersion(null);
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP protocol version to be");
  }

  /**
   * Verify that we can compare the expected and actual protocol versions when
   * expected is built from the protocol version components.
   */
  @Test
  public void testHasProtocolVersionFromStatusLineComponents() {
    statusLineAssert.hasProtocolVersion("HTTP", 1, 1);
  }

  /**
   * Verify we pass the assertion when the reason phrases match.
   */
  @Test
  public void testHasReasonPhraseWithMatch() {
    statusLineAssert.hasReasonPhrase("Created");
  }

  /**
   * Verify we throw an exception if the reason phrases are difference.
   */
  @Test
  public void testHasReasonPhraseWithDifference() {
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasReasonPhrase("Not modified");
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP reason phrase to be");
  }

  /**
   * Verify we throw an exception if the actual reason phrase is null (but the
   * expected reason phrase is not).
   */
  @Test
  public void testHasReasonPhraseWithNullActual() {
    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    StatusLine actual = new BasicStatusLine(protocolVersion, 201, null);
    statusLineAssert = StatusLineAssert.assertThat(actual);
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasReasonPhrase("Created");
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP reason phrase to be");
  }

  /**
   * Verify we throw an exception if the expected reason phrase is null (but the
   * actual reason phrase is not).
   */
  @Test
  public void testHasReasonPhraseWithNullExpected() {
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasReasonPhrase(null);
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP reason phrase to be");
  }

  /**
   * Verify we pass the assertion for the special case where both the actual and
   * expected reason phrases are null.
   */
  @Test
  public void testHasReasonPhraseWithNullActualAndNullExpected() {
    statusLineAssert.hasReasonPhrase("Created");
  }

  /**
   * Verify we pass the assertion when the status codes match.
   */
  @Test
  public void testHasStatusCodeWithMatch() {
    statusLineAssert.hasStatusCode(201);
  }

  /**
   * Verify we thrown an exception when the status codes are different.
   */
  @Test
  public void testHasStatusCodeWithDifference() {
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasStatusCode(302);
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP status code to be");
  }

  /**
   * Verify we pass the assertion when the status families match.
   */
  @Test
  public void testHasStatusFamilyWithMatch() {
    statusLineAssert.hasStatusFamily(StatusFamily.OK);
  }

  /**
   * Verify that we throw an assertion when the status families are different.
   */
  @Test
  public void testHasStatusFamilyWithDifference() {
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.hasStatusFamily(StatusFamily.REDIRECTION);
    });
    assertThat(thrown)
        .hasMessageStartingWith("Expected HTTP status family to be");
  }

  /**
   * Verify we pass the assertion when the StatusLine is equal.
   */
  @Test
  public void testIsEqualToWithMatch() {
    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    StatusLine expected = new BasicStatusLine(protocolVersion, 201, "Created");
    statusLineAssert.isEqualTo(expected);
  }

  /**
   * Verify that we throw an assertion when the StatusLines are different.
   */
  @Test
  public void testIsEqualToWithDifference() {
    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    StatusLine expected = new BasicStatusLine(protocolVersion, 302, "Found");
    Throwable thrown = expectThrows(AssertionError.class, () -> {
      statusLineAssert.isEqualTo(expected);
    });
    assertThat(thrown)
        .hasMessageMatching("Expected HTTP .*? to be .*? but was .*?\\.");
  }

}
