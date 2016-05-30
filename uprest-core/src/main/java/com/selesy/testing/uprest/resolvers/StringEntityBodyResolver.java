/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.gen5.api.extension.ExtensionContext;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides method parameter resolution for parameters that specify a
 * String @EntityBody.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class StringEntityBodyResolver extends EntityBodyResolver {

  /**
   * Resolves @EntityBody parameters that are specified as String types.
   * 
   * @param mic
   *          The MethodInvocationContext.
   * @param ec
   *          The ExtensionContext.
   * 
   * @return The resolved String parameter.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ExtensionContext ec) {
    log.trace("resolve()");

    byte[] entityBody = (byte[]) super.resolve(ec);
    String stringEntityBody = new String(entityBody);
    log.debug("Entity body: {}", stringEntityBody);

    return stringEntityBody;
  }

}
