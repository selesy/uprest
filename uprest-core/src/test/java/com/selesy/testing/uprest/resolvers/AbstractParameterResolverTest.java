package com.selesy.testing.uprest.resolvers;

import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Optional;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.api.extension.ParameterResolutionException;
import org.junit.gen5.engine.junit5.descriptor.MethodBasedTestExtensionContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractParameterResolverTest {
	
	static final String TEST_KEY = "Test Key";
	static final String TEST_VALUE = "Test Value";
	
	class TestParameterResolver extends AbstractParameterResolver {
		
		static final String STORE_KEY_TEST = "TestKey";
		
		@Override
		public Object resolve(Parameter arg0, Optional<Object> arg1, ExtensionContext arg2)
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
	
	ExtensionContext extensionContext;
	
	@BeforeEach
	public void setUp() {
		log.trace("setUp()");
		extensionContext = new MethodBasedTestExtensionContext(null, null, null, null);
		Store store = extensionContext.getStore(Namespace.of(extensionContext.getUniqueId()));
		store.put(TEST_KEY, TEST_VALUE);
	}

	@Test
	public void testGetStore() {
		log.trace("testGetStore()");
		fail("Not yet implemented");
	}

}
