package denparser.util;

import java.util.Objects;

public class CheckUtils {

  public static boolean isNull(Object value) {
    return Objects.isNull(value);
  }

  public static boolean isNotNull(Object value) {
    return !isNull(value);
  }

  public static <T> boolean hasLength(T[] value) {
    return !isNull(value) && value.length > 0;
  }
}
