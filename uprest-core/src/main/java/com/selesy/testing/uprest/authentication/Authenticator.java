package com.selesy.testing.uprest.authentication;

/**
 * A class that implements can perform any configuration it requires via its
 * default constructor.  Once ready, upREST will call the authenticate method.
 * 
 * @author Steve Moyer
 */
public interface Authenticator {
  
  /**
   * This method will be called by upREST before running the annotated test.
   * Any required configuration should be completed before this method is
   * called.
   * 
   * @throws AuthenticationException When an Authenticator fails to log in.
   */
  void authenticate() throws AuthenticationException;

}
