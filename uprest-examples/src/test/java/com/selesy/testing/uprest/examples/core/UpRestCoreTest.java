package com.selesy.testing.uprest.examples.core;

import static com.selesy.testing.uprest.UpRestAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.gen5.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.gen5.api.Test;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.annotations.EntityBody;
import com.selesy.testing.uprest.annotations.Headers;
import com.selesy.testing.uprest.annotations.Methods;
import com.selesy.testing.uprest.annotations.Paths;
import com.selesy.testing.uprest.http.Method;
import com.selesy.testing.uprest.http.Performance;

import lombok.extern.slf4j.Slf4j;

@UpRest
@Paths("/v2")
@Slf4j
public class UpRestCoreTest {

  @Test
  @Paths("/ResourceTypes")
  public void testWithStringEntityBody(@EntityBody String entityBody) {
    log.trace("test()");

    log.debug("Entity body: " + entityBody);
    assertThat(entityBody)
        .startsWith("{")
        .endsWith("}");
  }

  @Test
  @Paths("/ResourceTypes")
  @Headers({
    "Accept:application/scim+json",
    "User-Agent:upREST/0.1-SNAPSHOT"
  })
  public void simpleTest(HttpResponse response) {
    log.trace("simpleTest()");

    assertNotNull(response);

    assertThat(response.getStatusLine())
        .hasStatusCode(200);

    System.out.println("Status code: " + response.getStatusLine().getStatusCode());
    HttpEntity entity = response.getEntity();
    System.out.println("Content length: " + entity.getContentLength());
    System.out.println("Content type: " + entity.getContentType().getValue());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      entity.writeTo(baos);
      System.out.println("Entity body: " + baos);
      System.out.println("Body length: " + baos.size());
      System.out.println("Content length: " + entity.getContentLength());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // Add verification here
  }

  @Test
  @Paths("/ResourceTypes")
  public void performanceTest(Performance performance) {
    assertNotNull(performance);
    System.out.println("Entity body size (sent): " + performance.getRequestSize());
    System.out.println("Entity body size (received): " + performance.getResponseSize());
    System.out.println("Round trip time (nS): " + performance.getRoundTripInNanoSeconds());
  }

  @Test
  @Paths("/ResourceTypes")
  @Methods({ Method.DELETE, Method.PATCH, Method.POST, Method.PUT })
  public void dynamicMethodTest(StatusLine statusLine) {
    log.trace("dynamicMethodTest()");
    assertThat(statusLine)
        .hasStatusCode(405);
  }

}
