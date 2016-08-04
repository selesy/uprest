package com.selesy.testing.uprest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.runner.SelectPackages;
import org.junit.runner.RunWith;

/**
 * Uses JUnit5's pseudo-runner to define a suite of tests that can be run using
 * JUnit4.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@RunWith(JUnitPlatform.class)
@SelectPackages("com.selesy.testing.uprest")
public class Junit4Suite {
  
  @BeforeAll
  static void beforeAll() {
    System.out.println("SuiteBeforeAll");
  }

}
