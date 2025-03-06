package denparser;

import java.util.List;
import java.util.ArrayList;
import denparser.parse.IDataLineParser;
import denparser.parse.DataLineParser;
import denparser.write.IDataWriter;
import denparser.files.DataFileWriter;
import denparser.stats.IAccumulatorDataStats;
import denparser.stats.DoubleAccumulatorStats;
import denparser.stats.IStatsOptions;
import denparser.util.CheckUtils;

public class ParseAndSave {
  private String[] dataLines;
  private String outputFileNamePrefix;
  private boolean isAppendMode;
  private IStatsOptions statsOptions;
  private List<String> integers = new ArrayList<>();
  private List<String> floats = new ArrayList<>();
  private List<String> strings = new ArrayList<>();

  public ParseAndSave(String[] dataLines) {
    this(dataLines, "", false, null);
  }

 public ParseAndSave(
                      String[] dataLines,
                      String outputPrefix,
                      boolean isAppendMode
                      ) {
    this(dataLines, outputPrefix, isAppendMode, null);
  }

  public ParseAndSave(
                      String[] dataLines,
                      String outputPrefix,
                      boolean isAppendMode,
                      IStatsOptions statsOptions
                      ) {
    this.dataLines = dataLines;
    outputFileNamePrefix = outputPrefix;
    this.isAppendMode = isAppendMode;
    this.statsOptions = statsOptions;
  }

  public void processDataLines() {
    IAccumulatorDataStats<Double> integerStats = new DoubleAccumulatorStats();
    IAccumulatorDataStats<Double> doubleStats = new DoubleAccumulatorStats();

    for (String line : dataLines) {
      IDataLineParser lineParser = new DataLineParser(line);

      if (lineParser.isInteger()) {
        integers.add(line);

        if (isShortStats()) {
          integerStats.nextElement(lineParser.parseInteger());
        }
      } else if (lineParser.isDouble()) {
        floats.add(line);

        if (isShortStats()) {
          doubleStats.nextElement(lineParser.parseDouble());
        }
      } else {
        strings.add(line);
      }
    }

    saveLines(integers, outputFileNamePrefix.concat("integers.txt"));
    saveLines(floats, outputFileNamePrefix.concat("floats.txt"));
    saveLines(strings, outputFileNamePrefix.concat("strings.txt"));
    showStats(integerStats, doubleStats);
  }

  private void saveLines(List<String> lines, String fileName) {
    if (lines.size() > 0) {
      IDataWriter linesWriter = new DataFileWriter(fileName, isAppendMode);
      linesWriter.writeDataLines(lines.toArray(new String[0]));
    }
  }

  private boolean isShortStats() {
    return CheckUtils.isNotNull(statsOptions) && 
           (statsOptions.getIsShortStats() || statsOptions.getIsFullStats());
  }

  private boolean isFullStats() {
    return CheckUtils.isNotNull(statsOptions) && statsOptions.getIsFullStats();
  }

  private void showStats(
                        IAccumulatorDataStats<Double> integerStats,
                        IAccumulatorDataStats<Double> doubleStats
                        ) {
    double integersQuantity = integerStats.getQuantity();

    if (integersQuantity > 0) {
      if (isShortStats()) {
        System.out.println(String.format("Записано целых чисел: %.0f", integersQuantity));
      }

      if (isFullStats()) {
        System.out.println(String.format("Сумма целых: %.0f", integerStats.getSum()));
        System.out.println(String.format("Минимальное целое: %.0f", integerStats.getMinimal()));
        System.out.println(String.format("Максимальное целое: %.0f", integerStats.getMaximal()));
        System.out.println(String.format("Среднее целых : %f", integerStats.getAverage()));
      }
    }
    
    double doublesQuantity = doubleStats.getQuantity();

    if (doublesQuantity > 0) {
      if (isShortStats()) {
        System.out.println(String.format("Записано дробных чисел: %.0f", doublesQuantity));
      }

      if (isFullStats()) {
        System.out.println(String.format("Сумма дробных: %f", doubleStats.getSum()));
        System.out.println(String.format("Минимальное дробное: %f", doubleStats.getMinimal()));
        System.out.println(String.format("Максимальное дробное: %f", doubleStats.getMaximal()));
        System.out.println(String.format("Среднее дробных : %f", doubleStats.getAverage()));
      }
    }
  }
}
