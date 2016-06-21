package com.selesy.testing.uprest.javax.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.engine.descriptor.MethodBasedTestExtensionContext;

import com.selesy.testing.uprest.UpRest;
import com.selesy.testing.uprest.UpRestOld;
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
  UpRestJavaxJsonOld upRestJavaxJson;

  @BeforeEach
  public void beforeEach() {
    ec = new MethodBasedTestExtensionContext(null, null, null, null);
    junk = new Junk();
    upRestJavaxJson = new UpRestJavaxJsonOld();
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
  
  ParameterContext getFirstParameterContext(String methodName, Class<?> parameterClass) throws NoSuchMethodException, SecurityException {
    Parameter parameter = getFirstParameter(methodName, parameterClass);
    ParameterContext parameterContext = new ParameterContext() {
      
      @Override
      public Optional<Object> getTarget() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Parameter getParameter() {
        // TODO Auto-generated method stub
        return parameter;
      }
      
      @Override
      public int getIndex() {
        // TODO Auto-generated method stub
        return 0;
      }
    };
    return parameterContext;
  }

  /**
   * Tests that the JsonObjectEntityBodyResolver works when supplied with a JSON
   * object in string form.
   */
  @Test
  public void testResolveWithJsonObjectParameterAndJsonObjectEntityBody() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    Store store = ec.getStore();
    store.put(UpRestOld.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_OBJECT);

    JsonObject jsonObject = (JsonObject) upRestJavaxJson.resolve(parameterContext, ec);
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
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    Store store = ec.getStore();
    store.put(UpRestOld.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_OBJECT);
    try {
      upRestJavaxJson.resolve(parameterContext, ec);
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
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    Store store = ec.getStore();
    store.put(UpRestOld.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_ARRAY);

    JsonArray jsonArray = (JsonArray) upRestJavaxJson.resolve(parameterContext, ec);
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
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    Store store = ec.getStore();
    store.put(UpRestOld.STORE_KEY_ENTITY_BODY, ENTITY_BODY_JSON_ARRAY);
    try {
      upRestJavaxJson.resolve(parameterContext, ec);
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
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    Object object = (Object) upRestJavaxJson.resolve(parameterContext, null);
    assertNull(object);
  }

  /**
   * Tests that support() returns true for a JsonObject parameter that's
   * annotated with @Entitybody.
   */
  @Test
  public void testSupportWithJsonObjectWithBodyAnnotation() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    assertTrue(upRestJavaxJson.supports(parameterContext, null));
  }

  /**
   * Tests that support() returns true for a JsonArray parameter that's
   * annotated with @EntityBody.
   */
  @Test
  public void testSupportWithJsonArrayWithBodyAnnotation() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    assertTrue(upRestJavaxJson.supports(parameterContext, null));
  }

  /**
   * Tests that support() returns false for an unsupported parameter type even
   * when it's annotated with @EntityBody.
   */
  @Test
  public void testSupportWithUnsupportedClassWithBodyAnnotation() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    assertFalse(upRestJavaxJson.supports(parameterContext, null));
  }

  /**
   * Tests that support() returns false for a JsonObject parameter if it's not
   * also annotated with @EntityBody.
   */
  @Test
  public void testSupportWithJsonObjectWithoutBodyAnnotation() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    assertFalse(upRestJavaxJson.supports(parameterContext, null));
  }

  /**
   * Tests that support() returns false for a JsonArray parameter if it's not
   * also annotated with @EntityBody.
   */
  @Test
  public void testSupportWithJsonArrayWithoutBodyAnnotation() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    assertFalse(upRestJavaxJson.supports(parameterContext, null));
  }

  /**
   * Tests that support() returns false for unknown parameters.
   */
  @Test
  public void testSupportWithUnsupportedClassWithoutBodyAnnotation() throws NoSuchMethodException, SecurityException {
    ParameterContext parameterContext = getFirstParameterContext("method1", JsonObject.class);
    assertFalse(upRestJavaxJson.supports(parameterContext, null));
  }

  /**
   * Tests that support() returns false if a null parameter is provided.
   */
  @Test
  public void testSupportsWithNullParameter() {
    assertFalse(upRestJavaxJson.supports(null, null));
  }

}
