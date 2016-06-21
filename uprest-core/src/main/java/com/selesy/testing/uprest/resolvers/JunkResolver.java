/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * @author swm16
 *
 */
public class JunkResolver implements ParameterResolver {

  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.ParameterResolver#resolve(org.junit.gen5.api.extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext arg0, ExtensionContext arg1) throws ParameterResolutionException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.ParameterResolver#supports(org.junit.gen5.api.extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext arg0, ExtensionContext arg1) throws ParameterResolutionException {
    // TODO Auto-generated method stub
    return false;
  }

}
