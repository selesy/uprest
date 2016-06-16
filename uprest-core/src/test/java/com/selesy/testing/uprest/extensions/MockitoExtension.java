/**
 * 
 */
package com.selesy.testing.uprest.extensions;

import org.junit.gen5.api.extension.BeforeEachCallback;
import org.junit.gen5.api.extension.TestExtensionContext;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

/**
 * Initializes the mocks for a test before each method is called.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class MockitoExtension implements BeforeEachCallback {

	/* (non-Javadoc)
	 * @see org.junit.gen5.api.extension.BeforeEachCallback#beforeEach(org.junit.gen5.api.extension.TestExtensionContext)
	 */
	@Override
	public void beforeEach(TestExtensionContext testExtensionContext) throws Exception {
		log.trace("beforeEach()");
		MockitoAnnotations.initMocks(testExtensionContext.getTestInstance());
	}

}
