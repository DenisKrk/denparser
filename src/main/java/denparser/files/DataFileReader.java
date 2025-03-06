package denparser.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import denparser.read.IDataLineReader;
import denparser.util.CheckUtils;

public class DataFileReader implements IDataLineReader {
  private String fileName;
  private boolean isDone;
  private BufferedReader reader;

  public DataFileReader(String fileName) {
    this.fileName = fileName;

    try {
      reader = new BufferedReader(new FileReader(fileName));
    } catch (IOException e) {
      isDone = true;
      System.out.println(String.format(
                                        "Критическая ошибка! Невозможно прочитать файл %s",
                                        fileName
                                      )
                        );
      e.printStackTrace();
    }
  }

  @Override
  public String getDataLine() {
    if (isDone || CheckUtils.isNull(reader)) {
      return null;
    }

    String resultLine = null;
    
    try {
      resultLine = reader.readLine();

      if (CheckUtils.isNull(resultLine)) {
        reader.close();
        isDone = true;
      }
    } catch (IOException e) {
      reader.close();
      System.out.println(String.format(
                                        "Критическая ошибка! Невозможно прочитать файл %s",
                                        fileName
                                      )
      );
      e.printStackTrace();
    } finally {
      return resultLine;
    }
  }

  @Override
  protected void finalize() throws Throwable {
    if (CheckUtils.isNotNull(reader)) {
      reader.close();
    }
  }

}
