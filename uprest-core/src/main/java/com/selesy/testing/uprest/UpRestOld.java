package com.selesy.testing.uprest;

import lombok.experimental.UtilityClass;

/**
 * A JUnit5 extension that allows HTTP and REST testing to be defined
 * declaratively.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@UtilityClass
public class UpRestOld {

  public static final String STORE_KEY_ENTITY_BODY = "EntityBody";
  public static final String STORE_KEY_HTTP_REQUEST = "HttpRequest";
  public static final String STORE_KEY_HTTP_RESPONSE = "HttpResponse";
  public static final String STORE_KEY_PERFORMANCE = "Performance";
  public static final String STORE_KEY_STATUS_LINE = "StatusLine";

}