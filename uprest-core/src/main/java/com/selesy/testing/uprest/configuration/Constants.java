package com.selesy.testing.uprest.configuration;

import java.nio.charset.Charset;

import com.selesy.testing.uprest.authentication.Authenticator;
import com.selesy.testing.uprest.http.Method;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final Charset CHARSET = Charset.forName("UTF-8");
  
  //
  // Keys used for saving and retrieving REST life-cycle objects from an
  // ExtensionContext.Store.
  //
  
  public static final String STORE_KEY_ENTITY_BODY = "EntityBody";
  public static final String STORE_KEY_HTTP_REQUEST = "HttpRequest";
  public static final String STORE_KEY_HTTP_RESPONSE = "HttpResponse";
  public static final String STORE_KEY_PERFORMANCE = "Performance";
  public static final String STORE_KEY_STATUS_LINE = "StatusLine";
  
  //
  // Default HTTP Request configuration
  //

  public static final Authenticator[] DEFAULT_AUTHENTICATORS = {};
  public static final String[] DEFAULT_ENTITY_BODIES = {};
  public static final String[] DEFAULT_HEADERS = {};
  public static final Method[] DEFAULT_METHODS = { Method.GET };
  public static final String[] DEFAULT_PATHS = { "/" };
}
