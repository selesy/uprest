package com.selesy.testing.uprest.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import com.selesy.testing.uprest.internal.resolvers.HttpRequestResolver;

import lombok.Data;

@Data
public class UpRestInvocationContext implements TestTemplateInvocationContext {
	
	HttpRequest request;
	
	String name;
	
	public UpRestInvocationContext(String name, HttpRequest request) {
		this.name = name;
		this.request = request;
	}
	
	/* (non-Javadoc)
	 * @see org.junit.jupiter.api.extension.TestTemplateInvocationContext#getAdditionalExtensions()
	 */
	@Override
	public List<Extension> getAdditionalExtensions() {
		// TODO Auto-generated method stub
		//return TestTemplateInvocationContext.super.getAdditionalExtensions();
		List<Extension> extensions = new ArrayList<>();
		extensions.add(new HttpRequestResolver(this));
		return extensions;
	}
	
	@Override
	public String getDisplayName(int invocationIndex) {
		// TODO Auto-generated method stub
		return name + "-" + invocationIndex;
	}

}
