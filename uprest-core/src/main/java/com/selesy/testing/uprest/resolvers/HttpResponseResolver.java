/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Store;

import com.selesy.testing.uprest.UpRestOld;
import com.selesy.testing.uprest.http.Performance;

import lombok.extern.slf4j.Slf4j;

/**
 * @author smoyer1
 *
 */
@Slf4j
public class HttpResponseResolver implements ChainableParameterResolver {

  @Override
  public Object resolve(ExtensionContext ec) {
    log.trace("resolve()");
    
    // Retrieve the test's HttpUriRequest from the store (or create it if absent)
    Store store = ec.getStore();
    HttpUriRequest httpUriRequest = (HttpUriRequest) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_HTTP_REQUEST, (c) -> {
      HttpRequestResolver httpRequestResolver = new HttpRequestResolver();
      Object hur = httpRequestResolver.resolve(null, ec);
      store.put(UpRestOld.STORE_KEY_HTTP_REQUEST, hur);
      return hur;
    });
    
    HttpClient client = HttpClientBuilder.create().build();
    
    HttpResponse httpResponse = null;
    try {
      // Execute the request, keeping track of how long it takes
      long start = System.nanoTime();
      httpResponse = client.execute(httpUriRequest);
      long end = System.nanoTime();
      
      Performance performance = new Performance(0, 0, end-start);
      
      store.put(UpRestOld.STORE_KEY_HTTP_RESPONSE, httpResponse);
      store.put(UpRestOld.STORE_KEY_PERFORMANCE, performance);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return httpResponse;
  }

}
