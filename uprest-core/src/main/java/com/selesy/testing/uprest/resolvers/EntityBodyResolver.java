/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Store;

import com.selesy.testing.uprest.UpRestOld;
import com.selesy.testing.uprest.http.Performance;

import lombok.extern.slf4j.Slf4j;

/**
 * Base resolver for any classes that provide MethodParameterResolver
 * functionality on parameters annotated with @EntityBody.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public abstract class EntityBodyResolver implements ChainableParameterResolver {

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   * junit.gen5.api.extension.MethodInvocationContext,
   * org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ExtensionContext ec) {
    log.trace("resolve()");

    Store store = ec.getStore();

    // Retrieve the entity body if it's already been produced, otherwise, get
    // the HTTP response and create it (also updating the Performance object).
    byte[] entityBody = (byte[]) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_ENTITY_BODY, (e) -> {

      // Retrieve the HTTP response if it's already been produced, otherwise
      // chain to the HttpResponseResolver to create it.
      HttpResponse httpResponse = (HttpResponse) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_HTTP_RESPONSE, (r) -> {
        HttpResponseResolver httpResponseResolver = new HttpResponseResolver();
        return httpResponseResolver.resolve(ec);
      });

      // Read the HTTP entity body into a byte[]
      HttpEntity httpEntity = httpResponse.getEntity();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] httpEntityContent = {};

      try {
        httpEntity.writeTo(baos);
        httpEntityContent = baos.toByteArray();
        baos.close();
      } catch (IOException ioe) {
        log.error("Failed to read the entity body content.");
      }
      log.debug("Entity body: {}", httpEntityContent);

      // Update the stored performance object
      Performance oldPerformance = (Performance) store.get(UpRestOld.STORE_KEY_PERFORMANCE);
      Performance newPerformance = new Performance(oldPerformance.getRequestSize(), httpEntityContent.length, oldPerformance.getRoundTripInNanoSeconds());
      store.put(UpRestOld.STORE_KEY_PERFORMANCE, newPerformance);

      // Store the retrieved HTTP entity
      store.put(UpRestOld.STORE_KEY_ENTITY_BODY, httpEntityContent);
      return httpEntityContent;
    });

    log.debug("Entity body: {}", entityBody);
    return entityBody;
  }

}
