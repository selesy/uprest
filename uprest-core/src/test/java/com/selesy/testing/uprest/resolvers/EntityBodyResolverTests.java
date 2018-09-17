/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.http.HttpEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.mockito.Mock;

import com.selesy.testing.uprest.annotations.EntityBody;
import com.selesy.testing.uprest.extensions.MockitoExtension;
import com.selesy.testing.uprest.http.Performance;
import com.selesy.testing.uprest.internal.configuration.Constants;
import com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver;

/**
 * @author smoyer1
 *
 */
@ExtendWith(MockitoExtension.class)
public class EntityBodyResolverTests {
  
  class EntityBodyAnnotated {
    
    // @formatter: off
    public void hasEntityBodyAnnotation(@EntityBody String entityBody) {}
    public void missingEntityBodyAnnotation(String entityBody) {}
    // @formatter: on
    
  }
  
  class ChildEntityBodyResolver extends EntityBodyResolver {
    
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
      return super.supports(parameterContext, Object.class);
    }
    
  }
  
  EntityBodyResolver entityBodyResolver;
  
  @BeforeEach
  public void setUp() {
    entityBodyResolver = new ChildEntityBodyResolver();
  }
  
  Parameter getParameter(Class<?> clazz, String methodName, int parameterIndex, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
    Method method = clazz.getMethod(methodName, parameterTypes);
    return method.getParameters()[parameterIndex];
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  @Test
  public void testSupportsWithEntityBodyAnnotation(@Mock ParameterContext parameterContext) throws NoSuchMethodException, SecurityException {
    Parameter parameter = getParameter(EntityBodyAnnotated.class, "hasEntityBodyAnnotation", 0, String.class);
    when(parameterContext.getParameter()).thenReturn(parameter);
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   */
  @Test
  public void testSupportsWithoutEntityBodyAnnotation() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   */
  @Test
  public void testSupportsWithNullParameter() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#resolve(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)}.
   */
  @Test
  public void testResolve() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#getEntityBody(org.apache.http.HttpEntity)}.
   */
  @Test
  public void testGetEntityBody() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetEntityBodyBlah() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetEntityBodyWithException(@Mock HttpEntity entity) {
    //doThrow(new IOException()).when(entity.writeTo(any(OutputStream.class)));
    //when(entity.writeTo(any())).thenThrow(new IOException());
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#updateStoredResponseSize(org.junit.jupiter.api.extension.ExtensionContext.Store, long)}.
   */
  @Test
  public void testUpdateStoredResponseSizeWithEntityBody(@Mock Store store) {
    Performance oldPerformance = new Performance(0, 0, 0);
    Performance newPerformance = new Performance(0, 0, 1);
    when(store.get(eq(Constants.STORE_KEY_PERFORMANCE))).thenReturn(oldPerformance);
    
    entityBodyResolver.updateStoredResponseSize(store, 1);
    
    verify(store).get(eq(Constants.STORE_KEY_PERFORMANCE));
    verify(store).put(eq(Constants.STORE_KEY_PERFORMANCE), any(Performance.class));
    //verify(store).put(eq(UpRestOld.STORE_KEY_PERFORMANCE), eq(newPerformance));
  }

  /**
   * Test method for {@link com.selesy.testing.uprest.internal.resolvers.EntityBodyResolver#updateStoredResponseSize(org.junit.jupiter.api.extension.ExtensionContext.Store, long)}.
   */
  @Test
  public void testUpdateStoredResponseSizeWithEmptyEntityBody(@Mock Store store) {
    entityBodyResolver.updateStoredResponseSize(store, 0);
    
    verify(store, never()).get(any());
    verify(store, never()).put(any(), any());
  }

}
