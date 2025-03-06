package denparser.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * based on 
 * https://dev.to/oshanoshu/simple-java-command-line-argument-parser-implementation-n40
 **/

public class CommandLineArguments {
  private List<String> arguments = new ArrayList<>();
  private LinkedHashMap<String, ArrayList<String>> argumentKeysAndValues = new LinkedHashMap<>();
  private Set<String> flags = new HashSet<>();

  public CommandLineArguments(String[] arguments) {
    this.arguments = Arrays.asList(arguments);
    packUpArguments();
  }

  public Set<String> getArgumentNames() {
    return argumentKeysAndValues.keySet();
  }

  public boolean hasFlag(String flagName) {
    if (flags.contains(flagName))
      return true;
    return false;
  }

  public String[] getArgumentValue(String argumentName) {
    if (argumentKeysAndValues.containsKey(argumentName)) {
      return argumentKeysAndValues.get(argumentName).toArray(new String[0]);
    } else {
      return null;
    }
  }

  public String getFirstArgumentValue(String argumentName) {
    if (argumentKeysAndValues.containsKey(argumentName)) {
      String[] values = argumentKeysAndValues.get(argumentName).toArray(new String[0]);

      if (values.length > 0) {
        return values[0];
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public LinkedHashMap<String, ArrayList<String>> getArgumentsMap() {
    return argumentKeysAndValues;
  }

  public void debug() {
    System.out.println("flags length:" + flags.size());
    System.out.println("flags:" + getArgumentNames());

    for (Map.Entry<String, ArrayList<String>> entry : argumentKeysAndValues.entrySet()) {
      String key = entry.getKey();
      ArrayList<String> value = entry.getValue();

      System.out.println(key);

      for (String val : value) {
        System.out.println("=== " + val);
      }

      System.out.println();
    }
  }

  private void packUpArguments() {
    defineNoFlagValues();

    for (String argument: arguments) {
      if (argument.startsWith("-")) {
        String key = argument.replace("-", "");
        flags.add(key);

        if (arguments.indexOf(argument) == (arguments.size() - 1) ||
            arguments.get(arguments.indexOf(argument)+1).startsWith("-")
          ) {
          argumentKeysAndValues.put(key, new ArrayList<>());
        } else {
          ArrayList<String> argumentValues = new ArrayList<>();
          int i = 1;

          while (arguments.indexOf(argument)+i != arguments.size() &&
                !arguments.get(arguments.indexOf(argument)+i).startsWith("-")) {
            argumentValues.add(arguments.get(arguments.indexOf(argument)+i));
            i++;
          }

          argumentKeysAndValues.put(key, argumentValues);
        }
      }
    }
  }

  private void defineNoFlagValues() {
    int length = arguments.size();
    ArrayList<String> noFlagValues = new ArrayList<>();

    for (int i = length - 1; i >= 0; i--) {
      String argument = arguments.get(i);

      if (argument.startsWith("-")) {
        break;
      }

      noFlagValues.add(argument);
    }

    Collections.reverse(noFlagValues);
    argumentKeysAndValues.put("", noFlagValues);
  }
}
