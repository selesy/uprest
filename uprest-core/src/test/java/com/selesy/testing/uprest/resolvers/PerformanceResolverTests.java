/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.mockito.Mock;

import com.selesy.testing.uprest.extensions.MockitoExtension;
import com.selesy.testing.uprest.http.Performance;

/**
 * @author swm16
 *
 */
@ExtendWith(MockitoExtension.class)
public class PerformanceResolverTests {

  class TestMethods {

    // @formatter:off
    public void supportedParameter(Performance performance) {}
    public void unsupportedParameter(Object object) {}
    // @formatter:on

  }

  @Mock
  ParameterContext parameterContext;

  @Mock
  ExtensionContext extensionContext;

  Parameter parameter;

  PerformanceResolver performanceResolver;

  @BeforeEach
  public void setUp() {
    performanceResolver = new PerformanceResolver();
    when(parameterContext.getParameter()).thenReturn(parameter);
  }
  
  Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
    return clazz.getMethod(name, parameterTypes);
  }
  
  Parameter getParameter(Class<?> clazz, String name, int parameterIndex, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
    return (getMethod(clazz, name, parameterTypes)).getParameters()[parameterIndex];
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.resolvers.PerformanceResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  @Test
  public void testSupportsWithSupportedClass() throws NoSuchMethodException, SecurityException {
    parameter = getParameter(TestMethods.class, "supportedParameter", 0, Performance.class);
    when(parameterContext.getParameter()).thenReturn(parameter);
    
    assertThat(parameter)
        .isNotNull();
    assertThat(parameter.getType())
        .isEqualTo(Performance.class);
    assertThat(performanceResolver.supports(parameterContext, extensionContext))
        .isTrue();
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.resolvers.PerformanceResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  @Test
  public void testSupportsWithUnsupportedClass() throws NoSuchMethodException, SecurityException {
    parameter = getParameter(TestMethods.class, "unsupportedParameter", 0, Object.class);
    when(parameterContext.getParameter()).thenReturn(parameter);
    
    assertThat(parameter)
        .isNotNull();
    assertThat(parameter.getType())
        .isNotEqualTo(Performance.class);
    assertThat(performanceResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.resolvers.PerformanceResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  @Test
  public void testSupportsWithNull() throws NoSuchMethodException, SecurityException {
    when(parameterContext.getParameter()).thenReturn(null);
    
    assertThat(performanceResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.resolvers.PerformanceResolver#resolve(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   */
  @Test
  public void testResolve() {
    fail("Not yet implemented");
  }

}
