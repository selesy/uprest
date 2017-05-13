/**
 * 
 */
package com.selesy.testing.uprest.internal.resolvers;

import org.junit.jupiter.api.extension.ParameterResolver;

import com.selesy.testing.uprest.internal.UpRestInvocationContext;

/**
 * An abstract base class for the upREST ParameterResolvers that allows
 * sub-classes to retrieve the context constructed by the provider.
 */
public abstract class UpRestParameterResolver implements ParameterResolver {

	UpRestInvocationContext context;

	/**
	 * Creates a ParameterResolver storing the enclosing execution
	 * context.
	 * 
	 * @param context The context needed by the ParameterResolver.
	 */
	public UpRestParameterResolver(UpRestInvocationContext context) {
		this.context = context;
	}

	/**
	 * Retrieves the stored instance of the UpRestInvocationContext.
	 * 
	 * @return The stored context.
	 */
	public UpRestInvocationContext getContext() {
		return this.context;
	}

}
