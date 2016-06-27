/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.mockito.Mock;

/**
 * @author swm16
 *
 */
public abstract class BaseResolverTests {

  ParameterContext parameterContext;
  ExtensionContext extensionContext;
  Store store;

  @BeforeEach
  void setUp(@Mock ParameterContext parameterContext, @Mock ExtensionContext extensionContext, @Mock Store store) {
    this.parameterContext = parameterContext;
    this.extensionContext = extensionContext;
    this.store = store;
    when(extensionContext.getStore()).thenReturn(store);
    when(extensionContext.getStore(any(Namespace.class))).thenReturn(store);
    when(extensionContext.getUniqueId()).thenReturn("testSegment:testId");
  }

}
