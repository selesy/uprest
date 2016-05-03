package com.selesy.testing.uprest.javax.json;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.engine.junit5.descriptor.MethodBasedTestExtensionContext;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.annotations.EntityBody;

/**
 * Tests for upREST's javax.json extension and it's associated resolvers.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
public class UpRestJavaxJsonTest {

  /**
   * This class is only used to allow the easy creation of Parameter objects.
   * 
   * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
   */
  public class Junk {

    // @formatter:off
    public void method1(@EntityBody JsonObject jsonObject) {}
    public void method2(@EntityBody JsonArray jsonArray) {}
    public void method3(@EntityBody Object object) {}
    public void method4(JsonObject jsonObject) {}
    public void method5(JsonArray jsonArray) {}
    public void method6(Object object) {}
    // @formatter:on

  }

  static final byte[] ENTITY_BODY_JSON_OBJECT = "{ \"stringAttribute\" : \"string\", \"numberAttribute\" : 12345 }".getBytes();
  static final byte[] ENTITY_BODY_JSON_ARRAY = "[ \"string\", 12345 ]".getBytes();

  ExtensionContext ec;
  Junk junk;
  UpRestJavaxJson upRestJavaxJson;

  @BeforeEach
  public void beforeEach() {
    ec = new MethodBasedTestExtensionContext(null, null, null, null);
    junk = new Junk();
    upRestJavaxJson = new UpRestJavaxJson();
  }

  // This method works with the Junk class above to generate Parameter objects
  // that are used to test the resolvers.
  Parameter getFirstParameter(String methodName, Class<?> parameterClass) throws NoSuchMethodException, SecurityException {
    Parameter parameter = null;

    Method method = Junk.class.getMethod(methodName, parameterClass);
    Parameter[] parameters = method.getParameters();
    if (parameters.length > 0) {
      parameter = parameters[0];
    }

    return parameter;
  }

  /**
   * Tests that the JsonObjectEntityBodyResolver works when supplied with a JSON
   * object in string form.
   */
  @Test
  public void testResolveWithJsonObjectParameterAndJsonObjectEntityBody() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method1", JsonObject.class);
    Store store = ec.getStore();
    store.put(UpRest.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_OBJECT);

    JsonObject jsonObject = (JsonObject) upRestJavaxJson.resolve(parameter, null, ec);
    assertNotNull(jsonObject);
    assertEquals(2, jsonObject.size());
    assertEquals("string", jsonObject.getString("stringAttribute"));
    assertEquals(12345, jsonObject.getInt("numberAttribute"));
  }

  /**
   * Tests that the JsonObjectEntityBodyResolver fails with a JsonException when
   * supplied with a String that does not contain a JSON object.
   */
  @Test
  public void testResolveWithJsonArrayParameterAndJsonObjectEntityBody() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method2", JsonArray.class);
    Store store = ec.getStore();
    store.put(UpRest.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_OBJECT);
    try {
      upRestJavaxJson.resolve(parameter, null, ec);
    } catch (JsonException e) {
      // This is the expected behavior
    } catch (Throwable t) {
      fail("Expected a JsonException");
    }
  }

  /**
   * Tests that the JsonArrayEntityBodyResolver works when supplied with a JSON
   * array in string form.
   */
  @Test
  public void testResolveWithJsonArrayParameterAndJsonArrayEntityBody() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method2", JsonArray.class);
    Store store = ec.getStore();
    store.put(UpRest.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_ARRAY);

    JsonArray jsonArray = (JsonArray) upRestJavaxJson.resolve(parameter, null, ec);
    assertNotNull(jsonArray);
    assertEquals(2, jsonArray.size());
    assertEquals("string", jsonArray.getString(0));
    assertEquals(12345, jsonArray.getInt(1));
  }

  /**
   * Tests that the JsonArrayEntityBodyResolver fails with a JsonException when
   * supplied with a String that does not contain a JSON array.
   */
  @Test
  public void testResolveWithJsonObjectParameterAndJsonArrayEntityBody() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method1", JsonObject.class);
    Store store = ec.getStore();
    store.put(UpRest.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_ARRAY);
    try {
      upRestJavaxJson.resolve(parameter, null, ec);
    } catch (JsonException e) {
      // This is the expected behavior
    } catch (Throwable t) {
      fail("Expected a JsonException");
    }
  }

  /**
   * Tests that the UpRestJavaxJson JUnit5 extension returns null if resolve()
   * is called for an unsupported parameter type.
   */
  @Test
  public void testResolveWithAnUnsupportedParameterType() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method3", Object.class);
    Object object = (Object) upRestJavaxJson.resolve(parameter, null, null);
    assertNull(object);
  }

  /**
   * Tests that support() returns true for a JsonObject parameter that's
   * annotated with @Entitybody.
   */
  @Test
  public void testSupportWithJsonObjectWithBodyAnnotation() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method1", JsonObject.class);
    assertTrue(upRestJavaxJson.supports(parameter, null, null));
  }

  /**
   * Tests that support() returns true for a JsonArray parameter that's
   * annotated with @EntityBody.
   */
  @Test
  public void testSupportWithJsonArrayWithBodyAnnotation() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method2", JsonArray.class);
    assertTrue(upRestJavaxJson.supports(parameter, null, null));
  }

  /**
   * Tests that support() returns false for an unsupported parameter type even
   * when it's annotated with @EntityBody.
   */
  @Test
  public void testSupportWithUnsupportedClassWithBodyAnnotation() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method3", Object.class);
    assertFalse(upRestJavaxJson.supports(parameter, null, null));
  }

  /**
   * Tests that support() returns false for a JsonObject parameter if it's not
   * also annotated with @EntityBody.
   */
  @Test
  public void testSupportWithJsonObjectWithoutBodyAnnotation() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method4", JsonObject.class);
    assertFalse(upRestJavaxJson.supports(parameter, null, null));
  }

  /**
   * Tests that support() returns false for a JsonArray parameter if it's not
   * also annotated with @EntityBody.
   */
  @Test
  public void testSupportWithJsonArrayWithoutBodyAnnotation() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method5", JsonArray.class);
    assertFalse(upRestJavaxJson.supports(parameter, null, null));
  }

  /**
   * Tests that support() returns false for unknown parameters.
   */
  @Test
  public void testSupportWithUnsupportedClassWithoutBodyAnnotation() throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter("method6", Object.class);
    assertFalse(upRestJavaxJson.supports(parameter, null, null));
  }

  /**
   * Tests that support() returns false if a null parameter is provided.
   */
  @Test
  public void testSupportsWithNullParameter() {
    assertFalse(upRestJavaxJson.supports(null, null, null));
  }

}
