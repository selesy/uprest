package com.selesy.testing.uprest.resolvers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.gen5.api.Assumptions.assumeTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.api.extension.ParameterContext;
import org.junit.gen5.api.extension.ParameterResolutionException;
import org.junit.gen5.engine.UniqueId;
import org.junit.gen5.engine.junit5.descriptor.MethodBasedTestExtensionContext;
import org.junit.gen5.engine.junit5.descriptor.MethodTestDescriptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractParameterResolverTest {
	
	static final String TEST_KEY = "Test Key";
	static final String TEST_VALUE = "Test Value";
	
	class TestParameterResolver extends AbstractParameterResolver {
		
		static final String STORE_KEY_TEST = "TestKey";
		
		@Override
		public Object resolve(ParameterContext parameterContext, ExtensionContext arg2)
				throws ParameterResolutionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Optional<Class<? extends Annotation>> getAnnotationClass() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		String getKey() {
			return STORE_KEY_TEST;
		}

		@Override
		Class<?> getType() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	class TestClass {
		public void testMethod() {}
	}
	
	ExtensionContext extensionContext;
	Namespace namespace;
	
	@BeforeEach
	public void setUp() throws NoSuchMethodException, SecurityException {
		log.trace("setUp()");
		UniqueId id = UniqueId.root("testType", "testSegment");
		Class<?> clazz = TestClass.class;
		Method method = clazz.getMethod("testMethod", new Class[0]);
		MethodTestDescriptor descriptor = new MethodTestDescriptor(id, clazz, method);
		extensionContext = new MethodBasedTestExtensionContext(null, null, descriptor, null);
		assumeTrue(extensionContext != null);
		namespace = Namespace.of(extensionContext.getUniqueId());
		Store store = extensionContext.getStore(namespace);
		store.put(TEST_KEY, TEST_VALUE);
	}

	@Test
	public void testGetStore() {
		log.trace("testGetStore()");
		Store store = extensionContext.getStore(namespace);
		assertThat(store).isNotNull();
		String value = (String) store.get(TEST_KEY);
		assertThat(value).isEqualTo(TEST_VALUE);
	}

}
