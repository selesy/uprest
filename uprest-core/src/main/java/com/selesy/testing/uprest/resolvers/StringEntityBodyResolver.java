/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.owasp.esapi.ESAPI;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides method parameter resolution for parameters that specify a
 * String @EntityBody.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class StringEntityBodyResolver extends ByteArrayEntityBodyResolver {

  /**
   * Resolves @EntityBody parameters that are specified as String types.
   * 
   * @param parameterContext The ParameterContext
   * @param extensionContext
   *          The ExtensionContext.
   * 
   * @return The resolved String parameter.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   * @see org.junit.gen5.api.extension.ParameterResolver#resolve(org.junit.gen5.api.extension.ParameterContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Nonnull
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    log.trace("resolve()");

    byte[] entityBody = (byte[]) super.resolve(parameterContext, extensionContext);
    String stringEntityBody = new String(entityBody, Charset.forName("UTF-8"));
    log.debug("Entity body as String: {}", ESAPI.encoder().encodeForHTML(stringEntityBody));

    return stringEntityBody;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.junit.gen5.api.extension.ParameterResolver#supports(org.junit.gen5.api.
   * extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return supports(parameterContext, String.class);
  }

}
