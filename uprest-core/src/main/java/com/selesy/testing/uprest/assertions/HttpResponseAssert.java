/**
 * 
 */
package com.selesy.testing.uprest.assertions;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.assertj.core.api.AbstractAssert;

/**
 * @author smoyer1
 *
 */
public class HttpResponseAssert extends AbstractAssert<HttpResponseAssert, HttpResponse> {

  public HttpResponseAssert(HttpResponse actual) {
    super(actual, StatusLineAssert.class);
  }
  
  public static HttpResponseAssert assertThat(HttpResponse actual) {
    return new HttpResponseAssert(actual);
  }
  
//  public void test() {
//    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
//    StatusLine statusLine = new BasicStatusLine(protocolVersion, 201, "Created");
//    HttpResponse httpResponse = new BasicHttpResponse(statusLine);
//    
//    httpResponse.getAllHeaders();
//    httpResponse.getFirstHeader("name");
//    httpResponse.getHeaders("name");
//    httpResponse.getLastHeader("name");
//    httpResponse.getEntity();
//    httpResponse.getLocale();
//    httpResponse.getProtocolVersion();
//    httpResponse.getStatusLine();
//    httpResponse.containsHeader("name");
//  }

}
