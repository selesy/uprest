package com.selesy.testing.uprest.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.http.HttpRequest;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.selesy.testing.uprest.UpRest;

public class UpRestInvocationContextProvider implements TestTemplateInvocationContextProvider {

	@Override
	public boolean supportsTestTemplate(ContainerExtensionContext context) {
		return context.getTestMethod().map(m -> m.isAnnotationPresent(UpRest.class)).orElse(false);
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ContainerExtensionContext context) {
		List<TestTemplateInvocationContext> invocations = new ArrayList<>();
		invocations.add(new UpRestInvocationContext(context.getDisplayName(), (HttpRequest) null));
		invocations.add(new UpRestInvocationContext(context.getDisplayName(), (HttpRequest) null));
		invocations.add(new UpRestInvocationContext(context.getDisplayName(), (HttpRequest) null));
		invocations.add(new UpRestInvocationContext(context.getDisplayName(), (HttpRequest) null));
		
		//context.getTestMethod().
		

		// TODO Auto-generated method stub
		return invocations.stream();
	}
	
//	Stream<HttpRequest> getMethods(ContainerExtensionContext context) {
//		return findAnnotations(context, Methods.class)
//				
//	}
//
//	Stream<String> findAnnotations(ContainerExtensionContext context, Class<? extends Annotation> annotationClass) {
//		return context.getTestMethod()
//				.map()
//				.orElse(Stream.empty());
//	}
	
	Stream<String> findAnnotationValues(ContainerExtensionContext context, Class<? extends Annotation> annotationClass) {
		return null;
	}
	
//	<A extends  Annotation> Stream<A> findAnnotations(ContainerExtensionContext context, Class<A> annotationClass) {
//		return context.getTestMethod()
//				.map(m -> AnnotationSupport.findAnnotation(m, annotationClass))
//				.orElse(Stream.empty());
//	}
//	
//	<A extends Annotation> Stream<A> findAnnotations(AnnotatedElement element, Class<A> annotationType) {
//		return AnnotationSupport.findAnnotation(element, annotationType)
//	}
	
}
