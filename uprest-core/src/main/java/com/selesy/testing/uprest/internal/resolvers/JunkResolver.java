/**
 * 
 */
package com.selesy.testing.uprest.internal.resolvers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * @author smoyer1
 *
 */
public class JunkResolver implements ParameterResolver {

  /* (non-Javadoc)
   * @see org.junit.jupiter.api.extension.ParameterResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.junit.jupiter.api.extension.ParameterResolver#resolve(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    // TODO Auto-generated method stub
    return null;
  }

}
