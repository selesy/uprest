/**
 * 
 */
package com.selesy.testing.uprest.examples.core;

import org.apache.http.HttpRequest;
import org.junit.gen5.api.Test;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.annotations.Paths;

/**
 * @author smoyer1
 *
 */
@UpRest
@Paths({"/context1", "/context2", "/context3"})
public class PathBuildingTest {
  
  @Test
  @Paths({"/endpoint1", "/endpoint2", "/endpoint3", "/endpoint4"})
  public void testPathBuildingByPermutation(HttpRequest httpRequest) {
    
  }

}
