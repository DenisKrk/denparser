package denparser.util;

import java.lang.NumberFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
  public static final String EMTPY_STRING = "";
  private static final String INTEGER_REGEX = "[+-]?[0-9]+";
  private static final String FLOAT_REGEX = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";

  public static boolean isNullOrEmpty(String value) {
    return (value == null) || (EMTPY_STRING.equals(value)) || (value.trim().length() == 0);
  }

  public static int parseInteger(String value) throws NumberFormatException {
    return Integer.parseInt(value);
  }

  public static double parseDouble(String value) throws NumberFormatException {
    return Double.parseDouble(value);
  }

  public static boolean isInteger(String value) {
    Pattern integerPattern = Pattern.compile(INTEGER_REGEX);
    Matcher matcher = integerPattern.matcher(value);
    return (matcher.find() && matcher.group().equals(value));
  }

  public static boolean isDouble(String value) {
    Pattern floatPattern = Pattern.compile(FLOAT_REGEX);
    Matcher matcher = floatPattern.matcher(value);
    return (matcher.find() && matcher.group().equals(value));
  }
}
