/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.UpRestOld;
import com.selesy.testing.uprest.http.Performance;
import com.selesy.testing.uprest.utilities.StoreUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * If a request has already been made, retrieves the stored Performance data,
 * otherwise it chains to the HttpResponseResolver (which may also cause an HTTP
 * request to be created).
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class PerformanceResolver implements ParameterResolver {
  
  HttpResponseResolver httpResponseResolver = new HttpResponseResolver();

  /**
   * Uses the MethodInvocationContext and ExtensionContext to retrieve or create
   * a Performance instance for this test.
   * 
   * @param mic
   *          The MethodInvocationContext
   * @param ec
   *          The ExtensionContext
   * @return A Performance instance describing the request/response cycle
   *         associated with this test.
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   *      junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */

  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return Performance.class.equals(parameterContext.getParameter().getType());
  }

  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    log.trace("resolve()");

    Store store = StoreUtils.getNamespacedStore(extensionContext);
    return (Performance) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_PERFORMANCE, (k) -> {
      httpResponseResolver.resolve(parameterContext, extensionContext);
      return store.get(UpRestOld.STORE_KEY_PERFORMANCE);
    });
  }

}
