upREST
======

_Verify your REST services with this JUnit5 extension_

Introduction
------------

upREST is a declarative REST testing framework inspired by the (now defunct)
[Restfuse]() project.  This project takes advantage of the JUnit5 redesign to
resolve the test context, issue the HTTP request and pass the resulting HTTP
response into the test body.  Other related parameters are also available
(see the examples below).

Features
--------

-   Declarative test set-up allows your tests to focus on verifying the
    responses.
-   Support for dynamic test generation (when available in JUnit5)
-   Allows non-programmatic configuration of parameters that change with the
    test environment.
-   Integrated (lightweight) performance testing.
-   Compatible with Assert4J and provides its own set of fluent Assertions
    as appropriate for HTTP and REST verification.

Modules
-------

-   uprest-core - Provides the core functionality to send crafted HTTP/REST
    requests then receive, process and verify the HTTP/REST responses.
-   uprest-examples - A comprehensive set of working examples which are also
    used as acceptance tests for the core and supporting modules.
-   uprest-javax-json - Provides additional processing of the HTTP response
    so that a JsonObject (or less commonly a JsonArray) can be verified
    programmatically.
-   uprest-jsonpath - Provides additional processing of the HTTP response so
    that a parsed JsonPath document is available for method parameter
    resolution and can be verified programatically.

Examples
--------

This first example is perhaps the simplest.  Paths in upREST are cumulative,
so "/api/something" is added to the end of the base path (specified as part
of the environment) and the value contained in a @Paths annotation at the top
of the test class.  In this case, there's no @Methods annotation, so the HTTP
GET verb is assumed.  In this case, the entire HttpResponse object is returned
for verification.

    @Test
    @Paths("/api/something")
    @Headers("Accept:application/json")
    public void simpleTest(HttpResponse response) {
      assertThat(response)
          .hasStatusFamily(StatusFamily.OK)
          .hasContentType("application/json")
          .hasEntityBody();
    }

The second example shows the power of JUnit5's test resolver.  This test will
be executed 12 times - once for each combination of path and method.  In all
these cases, the expected response from the server is "403 Forbidden".

    @Test
    @Paths({"/api/first-resource", "/api/second-resource", "/api/third-resource"})
    @Methods({Method.POST, Method.PUT, Method.PATCH, Method.DELETE})
    @Headers("Accept:application/json")
    public void dynamicTest(StatusLine status) {
      assertThat(status)
          .hasStatusCode(HttpStatus.SC_FORBIDDEN);
    }

The unREST framework also allows rudimentary performance testing as shown in
the third example.  This test verifies that the round-trip time is less than
2mS.  As with any performance testing, network latency between the testing
and tested host becomes important.

    @Test
    @Paths("/api/something")
    @Headers("Accept:application/json")
    public void performanceTest(HttpResponse response, Performance performance) {
      assumeThat(response)
          .hasStatusFamily(HttpStatus.SC_OK);
      assertThat(performance)
          .hasRoundTripTimeLessThan(20000);
    }

The final example uses one of the "content specific" modules to unmarshal
the HttpResponse's entity body to a specific format for verification.  As
shown below, the JavaEE 7 JSON APIs are used to unmarshal the entity body
into a JsonObject.  The @EntityBody annotation is used to indicate that
unREST should attempt to provide that parameter.  Once the JsonObject is
retrieved

    @Test
    @Paths("/api/something")
    @Headers("Accept:application/json")
    public void responseBodyTest(@EntityBody JsonObject jsonObject) {
      assertThat(jsonOjbect)
          .containsKey("id")
          .hasKeyWithValue("userName", "abc123");
    }

Other annotations are provided for the following purposes:

-   @EntityBodies - Provides one or more HTTP entity bodies in string form
                    to generate the HTTP requests.
    @EntityBody - Allows the annotation of a test method parameter so that
                  the HTTP response's entity body is returned as a String
                  or byte[].  Other modules marshal these types into
                  Java objects.