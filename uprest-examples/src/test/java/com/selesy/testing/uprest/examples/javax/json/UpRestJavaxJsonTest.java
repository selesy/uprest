package com.selesy.testing.uprest.examples.javax.json;

import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.fail;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.gen5.api.Test;

import com.selesy.testing.uprest.annotations.EntityBody;
import com.selesy.testing.uprest.annotations.Paths;
import com.selesy.testing.uprest.javax.json.UpRestJavaxJson;

@UpRestJavaxJson
@Paths("/v2")
public class UpRestJavaxJsonTest {

  @Test
  @Paths("/ResourceTypes")
  public void test1(@EntityBody JsonObject jsonObject) {
    assertNotNull(jsonObject);
    fail("Not yet implemented");
  }

  @Test
  @Paths("/ResourceTypes")
  public void test2(@EntityBody JsonArray jsonArray) {
    assertNotNull(jsonArray);
    fail("Not yet implemented");
  }

}
