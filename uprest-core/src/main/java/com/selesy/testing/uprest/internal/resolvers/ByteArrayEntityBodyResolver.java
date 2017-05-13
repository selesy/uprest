/**
 * 
 */
package com.selesy.testing.uprest.internal.resolvers;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

import com.selesy.testing.uprest.utilities.LoggingUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * A pass-through resolver for HTTP entity bodies that are retrieved
 * as a byte[]
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class ByteArrayEntityBodyResolver extends EntityBodyResolver {

  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.ParameterResolver#supports(org.junit.gen5.api.extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(@Nonnull ParameterContext parameterContext, @Nonnull ExtensionContext entityContext) throws ParameterResolutionException {
    return supports(parameterContext, byte[].class);
  }

  /**
   * Simply passes the stored EntityBody byte[] (available from the abstract
   * EntityBodyResolver through to the MethodParameterResolver.
   * 
   * @param mic
   *          The MethodInvocationContext.
   * @param entityContext
   *          The ExtensionContext.
   * 
   * @return The resolved byte[] parameter.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   *      junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.ParameterResolver#resolve(org.junit.gen5.api.extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Nonnull
  @Override
  public Object resolve(@Nonnull ParameterContext parameterContext, @Nonnull ExtensionContext entityContext) {
    log.trace("resolve()");

    byte[] entityBody = (byte[]) super.resolve(parameterContext, entityContext);
    if(entityBody == null) {
      entityBody = new byte[0];
    }
    
    log.debug("Entity body length (bytes): {}", entityBody.length);
    log.debug("Entity body as byte[]: {}", LoggingUtils.safePrint(entityBody));
    return entityBody;
  }

}
