/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.owasp.esapi.ESAPI;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.UpRestOld;
import com.selesy.testing.uprest.utilities.StoreUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides MethodParameterResolution for the Apache HTTP client library's
 * StatusLine object.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class StatusLineResolver implements ParameterResolver {
  
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType().equals(StatusLine.class);
  }

  /**
   * Resolve the HTTP StatusLine, also resolving the HttpResponse if necessary.
   * 
   * @param mic
   *          The MethodInvocationContext.
   * @param ec
   *          The ExtensionContext.
   * @return The resolved StatusLine object.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.
   *      junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("resolve()");

    Store store = StoreUtils.getStoreNamespacedByUniqueId(extensionContext);

    // Retrieve the entity body if it's already been produced, otherwise, get
    // the HTTP response and create it (also updating the Performance object).
    StatusLine statusLine = (StatusLine) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_STATUS_LINE, (e) -> {

      // Retrieve the HTTP response if it's already been produced, otherwise
      // chain to the HttpResponseResolver to create it.
      HttpResponse httpResponse = (HttpResponse) store.getOrComputeIfAbsent(UpRestOld.STORE_KEY_HTTP_RESPONSE, (r) -> {
        HttpResponseResolver httpResponseResolver = new HttpResponseResolver();
        return httpResponseResolver.resolve(parameterContext, extensionContext);
      });

      // Store the status line
      StatusLine sl = httpResponse.getStatusLine();
      store.put(UpRestOld.STORE_KEY_STATUS_LINE, sl);
      return sl;
    });

    log.debug("Status line: {}", ESAPI.encoder().encodeForHTML(statusLine.toString()));
    return statusLine;
  }

}
