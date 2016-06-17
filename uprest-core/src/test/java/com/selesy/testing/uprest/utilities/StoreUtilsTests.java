/**
 * 
 */
package com.selesy.testing.uprest.utilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.engine.UniqueId;
import org.mockito.Mock;

import com.selesy.testing.uprest.extensions.MockitoExtension;

/**
 * Tests for the StoreUtils class.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@ExtendWith(MockitoExtension.class)
public class StoreUtilsTests {
  
  static final String UNIQUE_ID = UniqueId.root("testType", "testId").toString();
  static final Namespace NAMESPACE = Namespace.of(UNIQUE_ID);
  
  @Mock
  ExtensionContext extensionContext;
  
  @Mock
  Store expectedStore;

  /**
   * Verifies that the store is properly created from the UniqueId contained
   * in the ExtensionContext.
   */
  @Test
  public void testGetNamespacedStore() {
    when(extensionContext.getUniqueId()).thenReturn(UNIQUE_ID);
    when(extensionContext.getStore(NAMESPACE)).thenReturn(expectedStore);
    Store actualStore = StoreUtils.getNamespacedStore(extensionContext);
    verify(extensionContext).getUniqueId();
    verify(extensionContext, never()).getStore();
    verify(extensionContext).getStore(NAMESPACE);
    assertThat(actualStore).isNotNull();
  }

}
