package com.selesy.testing.uprest.annotations;

/**
 * One or more path segments that can accumulate to form a URL.  The first
 * segment of the path is the "base URL" which when derived from an environment
 * variable, system property or properties file allows the test suite to be
 * targeted at multiple systems.
 * 
 * Using the @Paths annotation at the top of a type will add that @Paths
 * segment to the "base URL" for all the enclosed tests.  Putting this
 * annotation on a method annotated with @BeforeAll has the same effect.
 * 
 * Finally, @Path can annotate individual test methods.  In this case, the
 * "base URL", the top-of-class (or @BeforeAll) @Paths and the method's
 * @Paths values are combined to form the complete URL.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public @interface Paths {
  
  /**
   * Specifies one ore more path segments for use in building the test's
   * complete URL.  When more than one path is provided, tests will be
   * generated for each combination of "base URL", top-of-class path and
   * test method path.
   * 
   * @return A list of path segments.
   */
  String[] value();

}
