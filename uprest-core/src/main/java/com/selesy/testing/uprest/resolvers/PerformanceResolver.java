/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Store;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.http.Performance;

import lombok.extern.slf4j.Slf4j;

/**
 * If a request has already been made, retrieves the stored Performance data,
 * otherwise it chains to the HttpResponseResolver (which may also cause an HTTP
 * request to be created).
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class PerformanceResolver implements ChainableParameterResolver {

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
  public Object resolve(ExtensionContext ec) {
    log.trace("resolve()");

    Store store = ec.getStore();
    Performance performance = (Performance) store.getOrComputeIfAbsent(UpRest.STORE_KEY_PERFORMANCE, (c) -> {
      HttpResponseResolver httpResponseResolver = new HttpResponseResolver();
      httpResponseResolver.resolve(ec);
      return store.get(UpRest.STORE_KEY_PERFORMANCE);
    });

    return performance;
  }

}
