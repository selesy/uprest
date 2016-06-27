/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Parameter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.UpRestOld;
import com.selesy.testing.uprest.annotations.EntityBody;
import com.selesy.testing.uprest.http.Performance;
import com.selesy.testing.uprest.utilities.StoreUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Base resolver for any classes that provide ParameterResolver functionality on
 * parameters annotated with @EntityBody.  Common code for extending classes is
 * provided
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public abstract class EntityBodyResolver implements ParameterResolver {

  HttpResponseResolver httpResponseResolver = new HttpResponseResolver();

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.junit.gen5.api.extension.ParameterResolver#supports(org.junit.gen5.api.
   * extension.ParameterContext, org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("supports()");

    Parameter parameter = parameterContext.getParameter();
    return parameter != null && parameter.isAnnotationPresent(EntityBody.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   * junit.gen5.api.extension.MethodInvocationContext,
   * org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("resolve()");

    Store store = StoreUtils.getNamespacedStore(extensionContext);

    return store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_ENTITY_BODY, (e) -> {
      HttpResponse httpResponse = (HttpResponse) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_HTTP_RESPONSE, (r) -> {
        return httpResponseResolver.resolve(parameterContext, extensionContext);
      });
      byte[] entityBody = getEntityBody(httpResponse.getEntity());
      updateStoredResponseSize(store, entityBody.length);
      return entityBody;
    });
  }

  byte[] getEntityBody(HttpEntity entity) {
    log.trace("gettEntityBody()");
    byte[] entityBody = {};

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      entity.writeTo(baos);
      entityBody = baos.toByteArray();
      log.debug("Entity body: {}", entityBody);
    } catch (IOException ioe) {
      log.error("Failed to read the entity body content.");
    }

    return entityBody;
  }

  void updateStoredResponseSize(Store store, long entityBodySize) {
    log.trace("updateStoredResponseSize()");
    if (entityBodySize > 0) {
      Performance oldPerformance = (Performance) store.get(UpRestOld.STORE_KEY_PERFORMANCE);
      Performance newPerformance = new Performance(oldPerformance.getRequestSize(), entityBodySize,
          oldPerformance.getRoundTripInNanoSeconds());
      store.put(UpRestOld.STORE_KEY_PERFORMANCE, newPerformance);
    }
  }

}
