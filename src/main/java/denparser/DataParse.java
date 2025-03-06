package denparser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import denparser.read.IDataReader;
import denparser.files.DataFilesReader;
import denparser.util.CommandLineArguments;
import denparser.stats.IStatsOptions;
import denparser.stats.StatsOptions;

public class DataParse {
  private final String NO_FLAG = "";
  private final String APPEND_MODE_FLAG = "a";
  private final String PREFIX_FLAG = "p";
  private final String OUTPUT_FILES_PATH_FLAG = "o";
  private final String SHORT_STATS_FLAG = "s";
  private final String FULL_STATS_FLAG = "f";
  private final String[] FLAGS_WITH_VALUE = {OUTPUT_FILES_PATH_FLAG, PREFIX_FLAG}; 
  private String[] arguments;
  private final CommandLineArguments commandArguments;

  public DataParse(String[] arguments) {
    this.arguments = arguments;
    commandArguments = new CommandLineArguments(arguments);
  }

  public void parse() {
    IDataReader reader = new DataFilesReader(getInputFileNames());
    String[] dataLines = reader.getDataLines();
    ParseAndSave parseAndSave;
    String prefix;
    boolean isAppendMode = false;

    if (commandArguments.hasFlag(PREFIX_FLAG)) {
      prefix = commandArguments.getFirstArgumentValue(PREFIX_FLAG);
    } else {
      prefix = "";
    }

    if (commandArguments.hasFlag(APPEND_MODE_FLAG)) {
      isAppendMode = true;
    }

    IStatsOptions statsOptions = new StatsOptions(
                                                  commandArguments.hasFlag(SHORT_STATS_FLAG),
                                                  commandArguments.hasFlag(FULL_STATS_FLAG)
                                                  );
    parseAndSave = new ParseAndSave(dataLines, prefix, isAppendMode, statsOptions);
    parseAndSave.processDataLines();
  }

  private String[] getInputFileNames() {
    String[] fileNames = commandArguments.getArgumentValue(NO_FLAG);
    LinkedHashMap<String, ArrayList<String>> argumentsMap = commandArguments.getArgumentsMap();
    String key = NO_FLAG;
    ArrayList<String> value;

    for (Map.Entry<String, ArrayList<String>> entry : argumentsMap.entrySet()) {
      key = entry.getKey();
      value = entry.getValue();
    }

    if (Arrays.asList(FLAGS_WITH_VALUE).contains(key)) {
      return Arrays.copyOfRange(fileNames, 1, fileNames.length);
    } else {
      return fileNames;
    }
  }
}
