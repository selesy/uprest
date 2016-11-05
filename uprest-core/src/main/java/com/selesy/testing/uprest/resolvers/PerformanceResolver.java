/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.lang.reflect.Parameter;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.configuration.Constants;
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

  /* (non-Javadoc)
   * @see org.junit.jupiter.api.extension.ParameterResolver#supports(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("supports()");

    boolean supported = false;
    Parameter parameter = parameterContext.getParameter();
    if (parameter != null && Performance.class.equals(parameter.getType())) {
      supported = true;
    }
    return supported;
  }

  /* (non-Javadoc)
   * @see org.junit.jupiter.api.extension.ParameterResolver#resolve(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("resolve()");

    Store store = StoreUtils.getStoreNamespacedByUniqueId(extensionContext);
    return store.getOrComputeIfAbsent(Constants.STORE_KEY_PERFORMANCE, key -> {
      httpResponseResolver.resolve(parameterContext, extensionContext);
      return store.get(Constants.STORE_KEY_PERFORMANCE, Performance.class);
    }, Performance.class);
  }

}
