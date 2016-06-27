package com.selesy.testing.uprest.resolvers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.lang.reflect.Parameter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.selesy.testing.uprest.UpRestOld;
import com.selesy.testing.uprest.annotations.EntityBody;
import com.selesy.testing.uprest.extensions.MockitoExtension;
import com.selesy.testing.uprest.utilities.ParameterContextUtils;

@ExtendWith(MockitoExtension.class)
public class StringEntityBodyResolverTests extends BaseResolverTests {

  class TestMethods {

    // @formatter:off
    public void supports1(@EntityBody String string) {}
    public void supports2(String string) {}
    public void supports3(@EntityBody Integer integer) {}
    public void supports4(Integer integer) {}
    // @formatter:on

  }

  String expected1 = "This is a string entity";
  byte[] entity1 = expected1.getBytes();

  String expected2 = "";
  byte[] entity2 = expected2.getBytes();

  StringEntityBodyResolver stringEntityBodyResolver;

  @BeforeEach
  void setUp() {
    stringEntityBodyResolver = new StringEntityBodyResolver();
  }

  @Test
  public void testSupportsWithCorrectClassAndAnnotation() {
    Parameter parameter = ParameterContextUtils.getParameter(TestMethods.class, "supports1", 0, String.class);
    when(parameterContext.getParameter())
        .thenReturn(parameter);

    assertThat(stringEntityBodyResolver.supports(parameterContext, extensionContext))
        .isTrue();
  }

  @Test
  public void testSupportsWithCorrectClassOnly() {
    Parameter parameter = ParameterContextUtils.getParameter(TestMethods.class, "supports2", 0, String.class);
    when(parameterContext.getParameter())
        .thenReturn(parameter);

    assertThat(stringEntityBodyResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  @Test
  public void testSupportsWithCorrectAnnotationOnly() {
    Parameter parameter = ParameterContextUtils.getParameter(TestMethods.class, "supports3", 0, Integer.class);
    when(parameterContext.getParameter())
        .thenReturn(parameter);

    assertThat(stringEntityBodyResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  @Test
  public void testSupportsWithoutCorrectClassOrAnnotation() {
    Parameter parameter = ParameterContextUtils.getParameter(TestMethods.class, "supports4", 0, Integer.class);
    when(parameterContext.getParameter())
        .thenReturn(parameter);

    assertThat(stringEntityBodyResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  @Test
  public void testSupportsWithNullParameter() {
    when(parameterContext.getParameter())
        .thenReturn(null);

    assertThat(stringEntityBodyResolver.supports(parameterContext, extensionContext))
        .isFalse();
  }

  @Test
  public void testResolveWithValidEntityBody() {
    when(store.getOrComputeIfAbsent(eq(UpRestOld.STORE_KEY_ENTITY_BODY), any()))
        .thenReturn(entity1);

    assertThat(stringEntityBodyResolver.resolve(parameterContext, extensionContext))
        .isEqualTo(expected1);
  }

  @Test
  public void testResolveWithEmptyEntityBody() {
    when(store.getOrComputeIfAbsent(eq(UpRestOld.STORE_KEY_ENTITY_BODY), any()))
        .thenReturn(entity2);

    assertThat(stringEntityBodyResolver.resolve(parameterContext, extensionContext))
        .isEqualTo(expected2);
  }

  @Test
  public void testResolveWithNullEntityBody() {
    when(store.getOrComputeIfAbsent(eq(UpRestOld.STORE_KEY_ENTITY_BODY), any()))
        .thenReturn(null);

    assertThat(stringEntityBodyResolver.resolve(parameterContext, extensionContext))
        .isEqualTo(expected2);
  }

}
