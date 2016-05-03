/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.api.extension.MethodInvocationContext;

import com.selesy.testing.uprest.UpRest;

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
  public Object resolve(MethodInvocationContext mic, ExtensionContext ec) {
    log.trace("resolve()");

    Store store = ec.getStore();

    // Retrieve the entity body if it's already been produced, otherwise, chain
    // to the HttpResponseResolver (which will produce it).
    byte[] entityBody = (byte[]) store.getOrComputeIfAbsent(UpRest.STORE_KEY_ENTITY_BODY, (c) -> {
      HttpResponseResolver httpResponseResolver = new HttpResponseResolver();
      httpResponseResolver.resolve(mic, ec);
      return store.get(UpRest.STORE_KEY_ENTITY_BODY);
    });

    return entityBody;
  }

}
