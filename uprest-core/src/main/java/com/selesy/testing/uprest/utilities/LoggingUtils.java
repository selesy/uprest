package com.selesy.testing.uprest.utilities;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

/**
 * Provides utility methods helpful when using SLF4J.
 * 
 * @author Steve Moyer &lt;smoyer1@selesy.com&gt;
 */
@UtilityClass
public class LoggingUtils {

  /**
   * Essentially provides a toString() method that returns a pretty-printed list
   * of the names of the passed enum elements.
   * 
   * @param values
   *          The passed enum elements.
   * @return A human-readable String of the elements names.
   */
  public String prettyPrint(Enum<?>[] values) {
    return prettyPrint(values, (x) -> x.name());
  }

  /**
   * Provides a human-readable list of header values.
   * 
   * @param headers
   *          The HTTP headers.
   * @return A pretty-printed String.
   */
  public <T> String prettyPrint(T[] values, Function<T, String> toString) {
    return Arrays.stream(values)
        .map(toString)
        .collect(Collectors.joining(", "));
  }

  /**
   * Provides a human readable String of the passed String[] values.
   * 
   * @param members
   *          The elements of the String[].
   * @return A pretty-printed String.
   */
  public String prettyPrint(String[] members) {
    return Arrays.stream(members)
        .collect(Collectors.joining(", "));
  }

}
