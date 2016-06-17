package com.selesy.testing.uprest.utilities;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StoreUtils {

  public static Store getNamespacedStore(ExtensionContext extensionContext) {
    String unigueId = extensionContext.getUniqueId();
    Namespace namespace = Namespace.of(unigueId);
    return extensionContext.getStore(namespace);
  }

}
