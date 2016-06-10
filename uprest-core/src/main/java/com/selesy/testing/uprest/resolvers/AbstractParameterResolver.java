/**
 * 
 */
package com.selesy.testing.uprest.resolvers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Optional;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.api.extension.ParameterResolutionException;
import org.junit.gen5.api.extension.ParameterResolver;

/**
 * @author swm16
 *
 */
// public abstract class AbstractParameterResolver<T> implements
// ParameterResolver {
//
// T get(ExtensionContext extensionContext) {
// Store store = getStore(extensionContext);
// String key = getKey();
// T value = (T) store.get(key);
// value = (T) store.getOrComputeIfAbsent(key, (c) -> (T) null);
// return (T) store.get(key);
// }

public abstract class AbstractParameterResolver implements ParameterResolver {

	Object get(ExtensionContext extensionContext) {
		Store store = getStore(extensionContext);
		String key = getKey();
		return store.get(key);
	}

	abstract Optional<Class<? extends Annotation>> getAnnotationClass();

	abstract String getKey();

	Store getStore(ExtensionContext extensionContext) {
		String testId = extensionContext.getUniqueId();
		Namespace namespace = Namespace.of(testId);
		return extensionContext.getStore(namespace);
	}

	abstract Class<?> getType();

	void put(ExtensionContext extensionContext, Object value) {
		Store store = getStore(extensionContext);
		String key = getKey();
		store.put(key, value);
	}

	@Override
	public boolean supports(Parameter parameter, Optional<Object> target, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		boolean supported = false;

		if (getType().equals(parameter.getType())) {
			Optional<Class<? extends Annotation>> optionalAnnotationClass = getAnnotationClass();
			if (optionalAnnotationClass.isPresent()) {
				Class<? extends Annotation> annotationClass = optionalAnnotationClass.get();
				if (parameter.isAnnotationPresent(annotationClass)) {
					supported = true;
				}
			} else {
				supported = true;
			}
		}

		return supported;
	}

}
