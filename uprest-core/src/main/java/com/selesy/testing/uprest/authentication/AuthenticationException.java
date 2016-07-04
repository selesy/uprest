/**
 * 
 */
package com.selesy.testing.uprest.authentication;

/**
 * This exception is thrown when an Authenticator has failed to log into a
 * server providing REST APIs.  Nice authenticators will describe why the
 * failure occurred.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class AuthenticationException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -5852829991921048689L;

  /**
   * @param message
   */
  public AuthenticationException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public AuthenticationException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
