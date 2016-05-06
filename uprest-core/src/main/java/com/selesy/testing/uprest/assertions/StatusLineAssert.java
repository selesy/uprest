/**
 * 
 */
package com.selesy.testing.uprest.assertions;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.assertj.core.api.AbstractAssert;

import com.selesy.testing.uprest.http.StatusFamily;

/**
 * Fluent assertions for StatusLine objects.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class StatusLineAssert extends AbstractAssert<StatusLineAssert, StatusLine> {

  static final String MESSAGE_ERROR_PROTOCOL_VERSIONS_NOT_EQUAL = "Expected HTTP protocol version to be <%s> but was <%s>.";
  static final String MESSAGE_ERROR_REASON_PHRASES_NOT_EQUAL = "Expected HTTP reason phrase to be <%s> but was <%s>.";
  static final String MESSAGE_ERROR_STATUS_CODES_NOT_EQUAL = "Expected HTTP status code to be <%s> but was <%s>.";
  static final String MESSAGE_ERROR_STATUS_FAMILIES_NOT_EQUAL = "Expected HTTP status family to be <%s> but was <%s>.";

  /**
   * Construct a StatusLineAssert object (which requires a StatusLine
   * instance as the "actual").
   * 
   * @param actual the target StatusLine.
   */
  public StatusLineAssert(StatusLine actual) {
    super(actual, StatusLineAssert.class);
  }

  /**
   * A static factory method that calls the constructor and returns the
   * StatusLineAssert object.
   * 
   * @param actual the target StatusLine.
   * @return the created StatusLineAssert.
   */
  public static StatusLineAssert assertThat(StatusLine actual) {
    return new StatusLineAssert(actual);
  }

  /**
   * Tests that the expected ProtocolVersion matches the actual target.
   * 
   * @param protocolVersion The expected ProtocolVersion.
   * @return The StatusLineAssert object for method chaining.
   */
  public StatusLineAssert hasProtocolVersion(ProtocolVersion protocolVersion) {
    isNotNull();

    ProtocolVersion actualProtocolVersion = actual.getProtocolVersion();
    // Note that the Apache HTTP client library disallows a StatusLine with a
    // null ProtocolVersion.
    if(!actualProtocolVersion.equals(protocolVersion)) {
      failWithMessage(MESSAGE_ERROR_PROTOCOL_VERSIONS_NOT_EQUAL, protocolVersion, actualProtocolVersion);
    }

    return this;
  }

  /**
   * Tests that the expected ProtocolVersion (constructed from the passed
   * components) matches the actual target.
   * 
   * @param protocol The expected protocol.
   * @param major The expected major version number.
   * @param minor The expected minor version number.
   * @return The StatusLineAssert object for method chaining.
   */
  public StatusLineAssert hasProtocolVersion(String protocol, int major, int minor) {
    ProtocolVersion expectedProtocolVersion = new ProtocolVersion(protocol, major, minor);
    return hasProtocolVersion(expectedProtocolVersion);
  }

  /**
   * Tests that the expected reason phrase matches the actual target.
   * 
   * @param reasonPhrase The expected reason phrase.
   * @return The StatusLineAssert object for method chaining.
   */
  public StatusLineAssert hasReasonPhrase(String reasonPhrase) {
    isNotNull();

    String actualReasonPhrase = actual.getReasonPhrase();
    if ((actualReasonPhrase != null && !actualReasonPhrase.equals(reasonPhrase)) ||
        (actualReasonPhrase == null && reasonPhrase != null)) {
      failWithMessage(MESSAGE_ERROR_REASON_PHRASES_NOT_EQUAL, actualReasonPhrase, actualReasonPhrase);
    }

    return this;
  }

  /**
   * Tests that the expected status code matches the actual target.
   * 
   * @param statusCode The expected status code.
   * @return The StatusLineAssert object for method chaining.
   */
  public StatusLineAssert hasStatusCode(int statusCode) {
    isNotNull();

    if (actual.getStatusCode() != statusCode) {
      failWithMessage(MESSAGE_ERROR_STATUS_CODES_NOT_EQUAL, statusCode, actual.getStatusCode());
    }

    return this;
  }

  /**
   * Tests that the expected status family matches the actual target.
   * 
   * @param statusFamily The expected status family.
   * @return The StatusLineAssert object for method chaining.
   */
  public StatusLineAssert hasStatusFamily(StatusFamily statusFamily) {
    isNotNull();

    StatusFamily actualStatusFamily = StatusFamily.fromStatusLine(actual);
    if (actualStatusFamily != statusFamily) {
      failWithMessage(MESSAGE_ERROR_STATUS_FAMILIES_NOT_EQUAL, statusFamily, actualStatusFamily);
    }

    return this;
  }

}
