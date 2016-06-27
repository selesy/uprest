package com.selesy.testing.uprest.utilities;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ParameterContextUtils {

  public static ParameterContext create(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    return null;
  }
  
  public static Parameter getParameter(Class<?> clazz, String methodName, int parameterIndex, Class<?>... parameterTypes) {
    Method method = getMethod(clazz, methodName, parameterTypes);
    try {
      return method.getParameters()[parameterIndex]; 
    } catch(IndexOutOfBoundsException e) {
      throw new ParameterResolutionException("The provided parameterIndex was out-of-bounds.", e); 
    }
  }

  static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    try {
      return clazz.getMethod(methodName, parameterTypes);
    } catch (Throwable t) {
      throw new ParameterResolutionException("Method: " + methodName + " not found in Class: " + clazz.getName(), t);
    }
  }

  static int getMethodIndex(@Nonnull Class<?> clazz, @Nonnull Method method) {
    List<Method> clazzMethods = Arrays.asList(clazz.getMethods());
    if (!clazzMethods.contains(method)) {
      throw new ParameterResolutionException("");
    }
    return clazzMethods.indexOf(method);
  }

}
