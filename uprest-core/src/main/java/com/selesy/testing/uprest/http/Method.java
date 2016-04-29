package com.selesy.testing.uprest.http;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;

/**
 * Provides a fixed list of the HTTP 1.1 Verbs as well as a means of retrieving
 * a new HttpRequest instance for the specified value.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public enum Method {

  DELETE(HttpDelete.class),
  GET(HttpGet.class),
  HEADER(HttpHead.class),
  OPTIONS(HttpOptions.class),
  PATCH(HttpPatch.class),
  POST(HttpPost.class),
  PUT(HttpPut.class),
  TRACE(HttpTrace.class);

  Class<? extends HttpRequest> httpRequestClass;

  private Method(Class<? extends HttpRequest> httpRequestClass) {
    this.httpRequestClass = httpRequestClass;
  }

  /**
   * Provides an instance of the HttpRequest appropriate to the value of this
   * Method instance.
   * 
   * @return An HttpRequest.
   * @throws InstantiationException
   *           When an instance of the HttpRequest cannot be created.
   * @throws IllegalAccessException
   *           When the current process does not have permission to create an
   *           instance of the HttpRequest object.
   */
  public HttpRequest getHttpRequest() throws InstantiationException, IllegalAccessException {
    return httpRequestClass.newInstance();
  }

}
