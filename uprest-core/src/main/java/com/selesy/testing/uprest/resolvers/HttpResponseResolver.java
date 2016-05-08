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
import org.junit.gen5.api.extension.MethodInvocationContext;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.http.Performance;

import lombok.extern.slf4j.Slf4j;

/**
 * @author smoyer1
 *
 */
@Slf4j
public class HttpResponseResolver implements ChainableParameterResolver {

  @Override
  public Object resolve(MethodInvocationContext mic, ExtensionContext ec) {
    log.trace("resolve()");
    
    // Retrieve the test's HttpUriRequest from the store (or create it if absent)
    Store store = ec.getStore();
    HttpUriRequest httpUriRequest = (HttpUriRequest) store.getOrComputeIfAbsent(UpRest.STORE_KEY_HTTP_REQUEST, (c) -> {
      HttpRequestResolver httpRequestResolver = new HttpRequestResolver();
      Object hur = httpRequestResolver.resolve(mic, ec);
      store.put(UpRest.STORE_KEY_HTTP_REQUEST, hur);
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
      
      store.put(UpRest.STORE_KEY_HTTP_RESPONSE, httpResponse);
      store.put(UpRest.STORE_KEY_PERFORMANCE, performance);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return httpResponse;
  }

}
