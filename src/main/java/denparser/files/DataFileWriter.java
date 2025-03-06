package denparser.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import denparser.util.CheckUtils;
import denparser.write.IDataWriter;

public class DataFileWriter implements IDataWriter {
  private String fileName;
  private boolean isAppendMode;
  private BufferedWriter writer;
  private boolean isDone;

  public DataFileWriter(String fileName) {
    this(fileName, false);
  }

  public DataFileWriter(String fileName, boolean isAppendMode) {
    this.fileName = fileName;
    this.isAppendMode = isAppendMode;

    try {
      writer = new BufferedWriter(new FileWriter(fileName, isAppendMode));
    } catch (IOException e) {
      isDone = true;
      System.out.println(String.format(
                                        "Критическая ошибка! Невозможно открыть файл %s для записи",
                                        fileName
                                      )
                        );
      e.printStackTrace();
    }
  }

  @Override
  public void writeDataLines(String[] dataLines) {
    writeDataLines(dataLines, System.lineSeparator());
  }

  @Override
  protected void finalize() throws Throwable {
    if (CheckUtils.isNotNull(writer)) {
      writer.close();
    }
  }

  private void writeDataLines(String[] dataLines, String separator) {
    if (CheckUtils.hasLength(dataLines)) {
      writeDataLine(String.join(separator, dataLines).concat(separator));

      try {
        writer.flush();
      } catch (IOException e) {
        System.out.println(String.format(
                                        "Критическая ошибка! Невозможно записать в файл %s",
                                        fileName
                                      )
                        );
        e.printStackTrace();
      }
    }
  }

  private void writeDataLine(String dataLine) {
    if (isDone || CheckUtils.isNull(dataLine) || CheckUtils.isNull(writer)) {
      return;
    }

    try {
      writer.write(dataLine);
    } catch (IOException e) {
      isDone = true;
      System.out.println(String.format(
                                        "Критическая ошибка! Невозможно записать в файл %s",
                                        fileName
                                      )
                        );
      e.printStackTrace();
    }
  }
}
