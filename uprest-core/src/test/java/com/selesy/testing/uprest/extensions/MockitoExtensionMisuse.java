package com.selesy.testing.uprest.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class MockitoExtensionMisuse {

  @Test
  public void testSomethingWithTwoProperties(@Mock Properties properties1, @Mock Properties properties2) {
    assertThat(properties1)
        .isNotNull()
        .isNotEqualTo(properties2);
    assertThat(properties2)
        .isNotNull();
  }

}
