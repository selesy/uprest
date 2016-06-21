/**
 * 
 */
package com.selesy.testing.uprest.extensions;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

/**
 * Initializes the mocks for a test before each method is called.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class MockitoExtension implements ParameterResolver, TestInstancePostProcessor {

    /* (non-Javadoc)
     * @see org.junit.gen5.api.extension.TestInstancePostProcessor#postProcessTestInstance(org.junit.gen5.api.extension.TestExtensionContext)
     */
  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
    log.trace("postProcessTestInstance()");
    MockitoAnnotations.initMocks(testInstance);
  }

  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.ParameterResolver#supports(org.junit.gen5.api.extension.TestExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    log.trace("supports()");
    return parameterContext.getParameter().isAnnotationPresent(Mock.class);
  }

  /* (non-Javadoc)
   * @see org.junit.gen5.api.extension.ParameterResolver#resolve(org.junit.gen5.api.extension.TestExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    log.trace("resolve()");
    return mock(parameterContext.getParameter().getType());
  }

}
