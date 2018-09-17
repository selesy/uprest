/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.selesy.testing.uprest.extensions.MockitoExtension;
import com.selesy.testing.uprest.http.Performance;
import com.selesy.testing.uprest.internal.configuration.Constants;
import com.selesy.testing.uprest.internal.resolvers.HttpResponseResolver;
import com.selesy.testing.uprest.internal.resolvers.PerformanceResolver;

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

  @Mock
  Store store;
  
  Parameter parameter;

  PerformanceResolver performanceResolver;

  @BeforeEach
  public void setUp() {
    performanceResolver = new PerformanceResolver();
    when(parameterContext.getParameter())
        .thenReturn(parameter);
    when(extensionContext.getStore())
        .thenReturn(store);
    when(extensionContext.getStore(any(Namespace.class)))
        .thenReturn(store);
    when(extensionContext.getUniqueId())
        .thenReturn("test");
  }

  Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
      throws NoSuchMethodException, SecurityException {
    return clazz.getMethod(name, parameterTypes);
  }

  Parameter getParameter(Class<?> clazz, String name, int parameterIndex, Class<?>... parameterTypes)
      throws NoSuchMethodException, SecurityException {
    return (getMethod(clazz, name, parameterTypes)).getParameters()[parameterIndex];
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.internal.resolvers.PerformanceResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * 
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testSupportsWithSupportedClass() throws NoSuchMethodException, SecurityException {
    parameter = getParameter(TestMethods.class, "supportedParameter", 0, Performance.class);
    when(parameterContext.getParameter())
        .thenReturn(parameter);

    assertThat(parameter)
        .isNotNull();
    assertThat(parameter.getType())
        .isEqualTo(Performance.class);
    assertThat(performanceResolver.supports(parameterContext, extensionContext))
        .isTrue();
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.internal.resolvers.PerformanceResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * 
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testSupportsWithUnsupportedClass() throws NoSuchMethodException, SecurityException {
    parameter = getParameter(TestMethods.class, "unsupportedParameter", 0, Object.class);
    when(parameterContext.getParameter())
        .thenReturn(parameter);

    assertThat(parameter)
        .isNotNull();
    assertThat(parameter.getType())
        .isNotEqualTo(Performance.class);
    assertThat(performanceResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.internal.resolvers.PerformanceResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * 
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
   * {@link com.selesy.testing.uprest.internal.resolvers.PerformanceResolver#resolve(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   */
  @Test
  public void testResolveWhenPerformanceIsInStore() {
    Performance performance = new Performance(0, 0, 0);
    when(store.getOrComputeIfAbsent(eq(Constants.STORE_KEY_PERFORMANCE), any()))
        .thenReturn(performance);

    assertThat(performanceResolver.resolve(parameterContext, extensionContext))
        .isSameAs(performance);
  }

  /**
   * Test method for
   * {@link com.selesy.testing.uprest.internal.resolvers.PerformanceResolver#resolve(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   */
  @Test
  public void testResolveWhenPerformanceIsNotInStore(@Mock HttpResponseResolver httpResponseResolver) {
    Performance performance = new Performance(0, 0, 0);
    performanceResolver.httpResponseResolver = httpResponseResolver;
    when(store.getOrComputeIfAbsent(eq(Constants.STORE_KEY_PERFORMANCE), any()))
        .thenAnswer(new Answer<Object>() {

          @Override
          public Object answer(InvocationOnMock invocation) throws Throwable {
            Object key = invocation.getArgument(0);
            Function<Object, Object> function = invocation.getArgument(1);
            return function.apply(key);
          }

        });
    when(store.get(eq(Constants.STORE_KEY_PERFORMANCE)))
        .thenReturn(performance);

    assertThat(performanceResolver.resolve(parameterContext, extensionContext))
        .isSameAs(performance);
    verify(httpResponseResolver).resolve(parameterContext, extensionContext);
    verify(store).get(eq(Constants.STORE_KEY_PERFORMANCE));
    verify(store).getOrComputeIfAbsent(eq(Constants.STORE_KEY_PERFORMANCE), any());
  }

}
