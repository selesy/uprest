package com.selesy.testing.uprest.utilities;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import lombok.experimental.UtilityClass;

/**
 * Utility methods to make working with an ExtensionContext's Store easier.
 *
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@UtilityClass
public class StoreUtils {

  /**
   * Uses the ExtensionContext's UniqueId to retrieve a Namespace(d) store.
   * 
   * @param extensionContext
   *          the current ExtensionContext.
   * @return a unique Store for this test instance.
   */
  @Nonnull
  public static Store getNamespacedStore(@Nonnull ExtensionContext extensionContext) {
    String unigueId = extensionContext.getUniqueId();
    Namespace namespace = Namespace.of(unigueId);
    return extensionContext.getStore(namespace);
  }

}
