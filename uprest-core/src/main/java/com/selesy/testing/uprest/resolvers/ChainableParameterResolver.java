package com.selesy.testing.uprest.resolvers;

import org.junit.gen5.api.extension.ExtensionContext;

/**
 * Defines an interface that allows a MethodParameterResolver to execute a chain
 * of resolution objects when the parameter that JUnit5 is trying to resolve has
 * other dependencies that must be resolved first.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public interface ChainableParameterResolver {

  /**
   * Resolves a paramter to an Object instance, possibly calling other
   * ChainableParameterResolvers to satisfy dependencies.
   * 
   * @param mic
   *          The MethodInvocationContext.
   * @param ec
   *          The ExtensionContext.
   * @return The Object that will be used as a test parameter.
   */
  Object resolve(ExtensionContext ec);

}
