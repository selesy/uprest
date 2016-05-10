package com.selesy.testing.uprest.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Provides a fixed list of the HTTP 1.1 Verbs as well as a means of retrieving
 * a new HttpUriRequest instance for the specified value.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public enum Method {

  DELETE(HttpDelete.class),
  GET(HttpGet.class),
  HEAD(HttpHead.class),
  OPTIONS(HttpOptions.class),
  PATCH(HttpPatch.class),
  POST(HttpPost.class),
  PUT(HttpPut.class),
  TRACE(HttpTrace.class);

  Class<? extends HttpUriRequest> httpUriRequestClass;

  private Method(Class<? extends HttpUriRequest> httpUriRequestClass) {
    this.httpUriRequestClass = httpUriRequestClass;
  }

  /**
   * Provides an instance of the HttpRequest appropriate to the value of this
   * Method instance.
   * 
   * @param uri
   *          A String representation of the URI that this method will target.
   * @return An HttpUriRequest.
   * @throws InstantiationException
   *           When an instance of the HttpRequest cannot be created.
   * @throws IllegalAccessException
   *           When the current process does not have permission to create an
   *           instance of the HttpRequest object.
   * @throws SecurityException
   *           When the selected constructor is not available with a public
   *           scope.
   * @throws NoSuchMethodException
   *           When the selected constructor does not exist.
   * @throws InvocationTargetException
   *           When an error occurs invoking the target method.
   * @throws IllegalArgumentException
   *           When the passed URI is not a string.
   */
  public HttpUriRequest getHttpUriRequest(String uri) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    Constructor<? extends HttpUriRequest> constructor = httpUriRequestClass.getConstructor(String.class);
    return constructor.newInstance(uri);
  }

}
