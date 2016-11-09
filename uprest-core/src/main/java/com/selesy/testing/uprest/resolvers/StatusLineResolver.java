/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.owasp.esapi.ESAPI;

import com.selesy.testing.uprest.configuration.Constants;
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

  /*
   * (non-Javadoc)
   * 
   * @see org.junit.jupiter.api.extension.ParameterResolver#supports(org.junit.
   * jupiter.api.extension.ParameterContext,
   * org.junit.jupiter.api.extension.ExtensionContext)
   */
  @Override
  public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType().equals(StatusLine.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.junit.jupiter.api.extension.ParameterResolver#resolve(org.junit.jupiter
   * .api.extension.ParameterContext,
   * org.junit.jupiter.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
    log.trace("resolve()");

    Store store = StoreUtils.getStoreNamespacedByUniqueId(extensionContext);

    // Retrieve the entity body if it's already been produced, otherwise, get
    // the HTTP response and create it (also updating the Performance object).
    StatusLine statusLine = store.getOrComputeIfAbsent(Constants.STORE_KEY_STATUS_LINE, e -> {

      // Retrieve the HTTP response if it's already been produced, otherwise
      // chain to the HttpResponseResolver to create it.
      HttpResponse httpResponse = (HttpResponse) store.getOrComputeIfAbsent(Constants.STORE_KEY_HTTP_RESPONSE, r -> {
        HttpResponseResolver httpResponseResolver = new HttpResponseResolver();
        return (HttpResponse) httpResponseResolver.resolve(parameterContext, extensionContext);
      }, HttpResponse.class);

      // Store the status line
      StatusLine sl = httpResponse.getStatusLine();
      store.put(Constants.STORE_KEY_STATUS_LINE, sl);
      return sl;
    }, StatusLine.class);

    log.debug("Status line: {}", ESAPI.encoder().encodeForHTML(statusLine.toString()));
    return statusLine;
  }

}
