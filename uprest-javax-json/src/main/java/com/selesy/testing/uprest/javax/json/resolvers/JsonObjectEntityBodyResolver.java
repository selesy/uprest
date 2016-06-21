/**
 * 
 */
package com.selesy.testing.uprest.javax.json.resolvers;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.jupiter.api.extension.ExtensionContext;

import com.selesy.testing.uprest.resolvers.StringEntityBodyResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides a MethodParameterResolver for JsonObject objects that are annotated
 * with @EntityBody.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@Slf4j
public class JsonObjectEntityBodyResolver extends StringEntityBodyResolver {

  /**
   * Attempts to resolve a JsonObject from the HTTP response's entity body using
   * the javax.json processing added to Java EE 7.
   * 
   * @param mic
   *          The MethodInvocationContext for this test.
   * @param ec
   *          The ExtensionContext for this test.
   * @return The unmarshalled JsonObject object.
   * @throws JsonException
   *           When the HTTP response's entity body can not be unmarshalled into
   *           a JsonObject.
   * 
   * @see com.selesy.testing.uprest.resolvers.ChainableParameterResolver#resolve(org.junit.gen5.api.extension.MethodInvocationContext,
   *      org.junit.gen5.api.extension.ExtensionContext)
   */
  @Override
  public Object resolve(ExtensionContext ec) {
    log.trace("resolve()");

    String entityBody = (String) super.resolve(ec);
    log.debug("Entity body: {}", entityBody);

    JsonReader jsonReader = Json.createReader(new StringReader(entityBody));
    JsonObject jsonObject = jsonReader.readObject();
    jsonReader.close();
    log.debug("JsonObject: {}", jsonObject);

    return jsonObject;
  }

}
