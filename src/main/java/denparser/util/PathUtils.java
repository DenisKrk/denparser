package denparser.util;

import java.nio.file.Paths;

public class PathUtils {
  
  public static String getPwd() {
    return Paths.get("")
                .toAbsolutePath()
                .toString();
  }

}
