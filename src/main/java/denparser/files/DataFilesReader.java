package denparser.files;

import java.util.List;
import java.util.ArrayList;
import denparser.read.IDataReader;

public class DataFilesReader implements IDataReader {
  private String[] fileNames;
  private DataFileReader[] readers;

  public DataFilesReader(String[] fileNames) {
    this.fileNames = fileNames;
    initializeReaders();
  }

  @Override
  public String[] getDataLines() {
    List<String> linesList = new ArrayList<>();
    String dataLine;

    do {
      boolean allDone = true;

      for (int i = 0; i < readers.length; i++) {
        dataLine = readers[i].getDataLine();

        if (dataLine != null) {
          linesList.add(dataLine);
          allDone = false;
        }
      }

      if (allDone) {
        break;
      }
    } while (true);

    return linesList.toArray(new String[0]);
  }

  private void initializeReaders() {
    readers = new DataFileReader[fileNames.length];
    int readerIndex = 0;

    for (String fileName : fileNames) {
      readers[readerIndex] = new DataFileReader(fileName);
      readerIndex++;
    }
  }
}
