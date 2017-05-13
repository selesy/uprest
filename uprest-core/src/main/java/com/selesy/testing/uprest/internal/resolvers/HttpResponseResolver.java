/**
 * 
 */
package com.selesy.testing.uprest.internal.resolvers;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.http.Performance;
import com.selesy.testing.uprest.internal.configuration.Constants;
import com.selesy.testing.uprest.utilities.StoreUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author smoyer1
 *
 */
@Slf4j
public class HttpResponseResolver implements ParameterResolver {
  
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("resolve()");

    Store store = StoreUtils.getStoreNamespacedByUniqueId(extensionContext);
    return store.getOrComputeIfAbsent(Constants.STORE_KEY_HTTP_RESPONSE, (k1) -> {
      HttpUriRequest httpRequest = (HttpUriRequest) (new HttpRequestResolver()).resolve(parameterContext, extensionContext);
      return execute(httpRequest, store);
    });

  }

  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return HttpResponse.class.equals(parameterContext.getParameter().getType());
  }

  HttpResponse execute(HttpUriRequest httpUriRequest, Store store) {
    HttpClient client = HttpClientBuilder.create().build();

    HttpResponse httpResponse = null;
    try {
      // Execute the request, keeping track of how long it takes
      long start = System.nanoTime();
      httpResponse = client.execute(httpUriRequest);
      long end = System.nanoTime();

      Performance performance = new Performance(0, 0, end - start);

      store.put(Constants.STORE_KEY_PERFORMANCE, performance);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return httpResponse;
  }

}
