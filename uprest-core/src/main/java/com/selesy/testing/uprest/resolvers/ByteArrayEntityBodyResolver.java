/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterContext;
import org.junit.gen5.api.extension.ParameterResolutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * A pass-through resolver for HTTP entity bodies that are retrieved
 * as a byte[]
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class ByteArrayEntityBodyResolver extends EntityBodyResolver {

  /**
   * Simply passes the stored EntityBody byte[] (available from the abstract
   * EntityBodyResolver through to the MethodParameterResolver.
   * 
   * @param mic
   *          The MethodInvocationContext.
   * @param ec
   *          The ExtensionContext.
   * 
   * @return The resolved byte[] parameter.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   *      junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ExtensionContext ec) {
    log.trace("resolve()");

    byte[] entityBody = (byte[]) super.resolve(ec);
    log.debug("Entity body as byte[]: {}", entityBody);

    return entityBody;
  }

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
