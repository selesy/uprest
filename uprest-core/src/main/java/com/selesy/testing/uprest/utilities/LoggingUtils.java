package com.selesy.testing.uprest.utilities;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.owasp.esapi.ESAPI;

import com.selesy.testing.uprest.internal.configuration.Constants;

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
  @Nonnull
  public String prettyPrint(@Nonnull Enum<?>[] values) {
    return safePrint(prettyPrint(values, (x) -> x.name()));
  }

  /**
   * Provides a human-readable list of header values.
   * 
   * @param headers
   *          The HTTP headers.
   * @return A pretty-printed String.
   */
  @Nonnull
  public <T> String prettyPrint(@Nonnull T[] values, @Nonnull Function<T, String> toString) {
    return safePrint(Arrays.stream(values)
        .map(toString)
        .collect(Collectors.joining(", ")));
  }

  /**
   * Provides a human readable String of the passed String[] values.
   * 
   * @param members
   *          The elements of the String[].
   * @return A pretty-printed String.
   */
  @Nonnull
  public String prettyPrint(@Nonnull String[] members) {
    return safePrint(Arrays.stream(members)
        .collect(Collectors.joining(", ")));
  }
  
  @Nonnull
  public String safePrint(byte[] unsafeByteArray) {
    return ESAPI.encoder().encodeForHTML(new String(unsafeByteArray, Constants.CHARSET));
  }
  
  @Nonnull
  public String safePrint(@Nonnull String unsafeString) {
    return ESAPI.encoder().encodeForHTML(unsafeString);
  }

}
