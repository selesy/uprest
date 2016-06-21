package com.selesy.testing.uprest.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.jupiter.api.Test;

/**
 * Tests that the correct HttpRequest subclass is returned for each of the HTTP
 * verbs.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class MethodTests {

  static final String TEST_URI = "http://localhost";

  // Assertions for HttpRequests that carry a URI
  void testHttpWithUri(HttpUriRequest httpRequest) {
    assertThat(httpRequest.getURI().toString())
        .isEqualTo(TEST_URI);
  }

  // Assertions for HttpRequests with an entity body
  void testWithEntity(HttpUriRequest httpRequest) {
    testHttpWithUri(httpRequest);
    assertThat(httpRequest)
        .isInstanceOf(HttpUriRequest.class)
        .isInstanceOf(HttpEntityEnclosingRequest.class);
  }

  // Assertions for HttpRequests without an entity body
  void testWithoutEntity(HttpUriRequest httpRequest) {
    testHttpWithUri(httpRequest);
    assertThat(httpRequest)
        .isInstanceOf(HttpUriRequest.class)
        .isNotInstanceOf(HttpEntityEnclosingRequest.class);
  }

  // TODO - Replace these with a single parameterized method when JUnit5
  // supports them.

  /**
   * Verify the HTTP DELETE verb.
   */
  @Test
  public void testGetHttpRequestForDelete() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.DELETE.getHttpUriRequest(TEST_URI);
    testWithoutEntity(httpRequest);
  }

  /**
   * Verify the HTTP HEAD verb.
   */
  @Test
  public void testGetHttpRequestForHead() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.HEAD.getHttpUriRequest(TEST_URI);
    testWithoutEntity(httpRequest);
  }

  /**
   * Verify the HTTP GET verb.
   */
  @Test
  public void testGetHttpRequestForGet() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.GET.getHttpUriRequest(TEST_URI);
    testWithoutEntity(httpRequest);
  }

  /**
   * Verify the HTTP OPTIONS verb.
   */
  @Test
  public void testGetHttpRequestForOptions() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.OPTIONS.getHttpUriRequest(TEST_URI);
    testWithoutEntity(httpRequest);
  }

  /**
   * Verify the HTTP PATCH verb.
   */
  @Test
  public void testGetHttpRequestForPatch() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.PATCH.getHttpUriRequest(TEST_URI);
    testWithEntity(httpRequest);
  }

  /**
   * Verify the HTTP POST verb.
   */
  @Test
  public void testGetHttpRequestForPost() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.POST.getHttpUriRequest(TEST_URI);
    testWithEntity(httpRequest);
  }

  /**
   * Verify the HTTP PUT verb.
   */
  @Test
  public void testGetHttpRequestForPut() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.PUT.getHttpUriRequest(TEST_URI);
    testWithEntity(httpRequest);
  }

  /**
   * Verify the HTTP TRACE verb.
   */
  @Test
  public void testGetHttpRequestForTrace() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    HttpUriRequest httpRequest = Method.TRACE.getHttpUriRequest(TEST_URI);
    testWithoutEntity(httpRequest);
  }

}
