package com.selesy.testing.uprest.http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.http.StatusLine;

import lombok.Getter;

/**
 * Represents the HTTP status code families according to section 10 of the HTTP
 * 1.1 specification defined by RFC2616
 * (https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html).
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Getter
public enum StatusFamily {

  INFORMATIONAL(1, "Informational"),
  OK(2, "OK"),
  REDIRECTION(3, "Redirection"),
  CLIENT_ERROR(4, "Client Error"),
  SERVER_ERROR(5, "Server Error");

  static final int STATUS_CODE_MINIMUM = 100;
  static final int STATUS_CODE_MAXIMUM = 599;

  static final String MESSAGE_STATUSCODE_RANGE = "The HTTP status code must be between " +
      STATUS_CODE_MINIMUM + " and " + STATUS_CODE_MAXIMUM + " (inclusive).";
  static final String MESSAGE_STATUSLINE_NULL = "The statusLine parameter may not by null.";

  static final Map<Integer, StatusFamily> reverseMapFromStatusCode = new HashMap<>();

  static {
    // Builds a reverse map so that the StatusFamily can be looked up based
    // on the associated HTTP 1.1 status codes.
    Stream.of(StatusFamily.values()).forEach((f) -> reverseMapFromStatusCode.put(f.familyCode, f));
  }

  int familyCode;
  String familyMessage;

  private StatusFamily(int familyCode, String familyMessage) {
    this.familyCode = familyCode;
    this.familyMessage = familyMessage;
  }

  /**
   * Retrieves the StatusFamily for the provided HTTP status code.
   * 
   * @param statusCode
   *          The HTTP status code.
   * @return The StatusFamily associated with the provided HTTP status code.
   * @throws IllegalArgumentException
   *           When the statusCode parameter is outside the valid range for HTTP
   *           status codes (100 <-> 599 inclusive).
   */
  public static StatusFamily fromStatusCode(int statusCode) {
    if (statusCode < STATUS_CODE_MINIMUM || statusCode > STATUS_CODE_MAXIMUM) {
      throw new IllegalArgumentException(MESSAGE_STATUSCODE_RANGE);
    }
    return reverseMapFromStatusCode.get(statusCode / 100);
  }

  /**
   * Retrieves the StatusFamily for the provided StatusLine.
   * 
   * @param statusLine
   *          The StatusLine containing an HTTP status code.
   * @return The StatusFamily associated with the provided StatusLine.
   * @throws IllegalArgumentException
   *           When the statusLine parameter is null.
   */
  public static StatusFamily fromStatusLine(StatusLine statusLine) {
    if (statusLine == null) {
      throw new IllegalArgumentException(MESSAGE_STATUSLINE_NULL);
    }
    return StatusFamily.fromStatusCode(statusLine.getStatusCode());
  }
  
  /**
   * Returns a string containing a "pretty" compilation of the status family
   * code and message.
   * 
   * @return The presentable contents of a StatusFamily object.
   */
  public String toString() {
    return familyCode + "00 - " + familyMessage;
  }

}
