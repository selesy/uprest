package com.selesy.testing.uprest.http;

import lombok.Data;

/**
 * A collection of rudimentary performance metrics.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Data
public class Performance {

  // The number of bytes in the HttpRequest's entity body (sent to the server)
  long requestSize;

  // The number of bytes in the HttpResponse's entity body (received from the
  // server)
  long responseSize;

  // The round-trip response time to send the HttpRequest and receive the
  // HttpResponse.
  long roundTripInNanoSeconds;

}
