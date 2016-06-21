package com.selesy.testing.uprest.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Test;

/**
 * Tests for the StatusFamily enum.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class StatusFamilyTests {

  /**
   * Tests that a valid StatusFamily can be retrieved via reverse lookup from a
   * valid status code.
   */
  @Test
  public void testFromStatusCodeWithValidCode() {
    assertEquals(StatusFamily.OK, StatusFamily.fromStatusCode(201));
  }

  /**
   * Tests that an IllegalArgumentException is thrown when a lookup is attempted
   * using a status code below the allowed minimum.
   */
  @Test
  public void testFromStatusCodeWithLowCode() {
    Throwable thrown = expectThrows(IllegalArgumentException.class, () -> {
      StatusFamily.fromStatusCode(99);
    });
    assertThat(thrown)
        .hasMessage(StatusFamily.MESSAGE_STATUSCODE_RANGE);
  }

  /**
   * Tests that an IllegalArgumentException is thrown when a lookup is attempted
   * using a status code above the allowed maximum.
   */
  @Test
  public void testFromStatusCodeWithHighCode() {
    Throwable thrown = expectThrows(IllegalArgumentException.class, () -> {
      StatusFamily.fromStatusCode(99);
    });
    assertThat(thrown)
        .hasMessage(StatusFamily.MESSAGE_STATUSCODE_RANGE);
  }

  /**
   * Tests that a valid StatusFamily can be retrieved via reverse lookup from a
   * valid StatusLine.
   */
  @Test
  public void testFromStatusLine() {
    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    StatusLine statusLine = new BasicStatusLine(protocolVersion, 201, "Created");
    StatusFamily statusFamily = StatusFamily.fromStatusLine(statusLine);
    assertThat(statusFamily)
        .isEqualTo(StatusFamily.OK);
  }

  /**
   * Tests that an IllegalArgumentException is thrown when a lookup is attempted
   * passing a null StatusLine parameter.
   */
  @Test
  public void testFromStatusLineWithNull() {
    Throwable thrown = expectThrows(IllegalArgumentException.class, () -> {
      StatusFamily.fromStatusLine(null);
    });
    assertThat(thrown)
        .hasMessage(StatusFamily.MESSAGE_STATUSLINE_NULL);
  }

  /**
   * Test that the toString() method produces a correctly formatted result.
   */
  @Test
  public void testToString() {
    StatusFamily statusFamily = StatusFamily.fromStatusCode(302);
    assertThat(statusFamily.toString())
        .isEqualTo("300 - Redirection");
  }

}
