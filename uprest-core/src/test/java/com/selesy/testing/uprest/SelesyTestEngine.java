/**
 * 
 */
package com.selesy.testing.uprest;

import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine;

/**
 * @author smoyer1
 *
 */
public class SelesyTestEngine extends HierarchicalTestEngine<SelesyEngineExecutionContext> {
  
  static final String ENGINE_ID = "selesy-engine";

  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return ENGINE_ID;
    // return null;
  }

  @Override
  public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
    System.out.println("discover() - UniqueId: " + uniqueId);
    // TODO Auto-generated method stub
    // return new EngineDescriptor(UniqueId.forEngine(ENGINE_ID), "Selesy Engine");
    return null;
  }

  @Override
  protected SelesyEngineExecutionContext createExecutionContext(ExecutionRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

}
