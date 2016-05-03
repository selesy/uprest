package com.selesy.testing.uprest.configuration;

import lombok.Getter;

/**
 * Allows the Restfuse Destination to be specified via environment variables
 * or JVM properties so that tests can be executed against different
 * environments (dev, test, acc, sb, prod).
 * 
 * @author Steve Moyer &lt;smoyer@psu.edu&gt;
 */
public final class Configuration {

  public static final String DEFAULT_PATH = "http://localhost";
  public static final String DEFAULT_PROXY_HOST = "";
  public static final String DEFAULT_PROXY_PORT = "";

  public static final String ENV_PATH = "UPREST_BASE_PATH";
  public static final String ENV_PROXY_HOST = "UPREST_PROXY_HOST";
  public static final String ENV_PROXY_PORT = "UPREST_PROXY_PORT";

  public static final String PROPERTY_PATH = "uprest.base.path";
  public static final String PROPERTY_PROXY_HOST = "uprest.proxy.host";
  public static final String PROPERTY_PROXY_PORT = "uprest.proxy.port";

  @Getter
  static String basePath;

  @Getter
  static String proxyHost;

  @Getter
  static Integer proxyPort;

  /*
  * Processes the environment variables and JVM properties statically so that
  * this processing only happens once per test run.
  */
  static {
    basePath = getValue(DEFAULT_PATH, ENV_PATH, PROPERTY_PATH);
    proxyHost = getValue(DEFAULT_PROXY_HOST, ENV_PROXY_HOST, PROPERTY_PROXY_HOST);
    String proxyPortString = getValue(DEFAULT_PROXY_PORT, ENV_PROXY_HOST, PROPERTY_PROXY_PORT);
    
    if (proxyPortString != null && !proxyPortString.isEmpty()) {
      proxyPort = Integer.parseInt(proxyPortString);
    }
  }

  private Configuration() {
    // Make this a utility class
  }

  /*
   * Generic way to get a default string value, environment variable value or
   * property value in that order of priority.
   */
  private static String getValue(String defaultValue, String envName, String propertyName) {
    String value = System.getenv(envName);
    if (value == null) {
      value = defaultValue;
    }
    value = System.getProperty(propertyName, value);
    return value;
  }

}
