/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.gen5.api.extension.ExtensionContext;

/**
 * The default resolver which returns a null value for any unsupported parameter
 * types.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class NullResolver implements ChainableParameterResolver {

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   * junit.gen5.api.extension.MethodInvocationContext,
   * org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ExtensionContext ec) {
    return null;
  }

}
