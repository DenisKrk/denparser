package denparser.parse;

import denparser.util.StringUtils;

public class DataLineParser implements IDataLineParser {
  private String value;
  private boolean isNullOrEmpty;

  public DataLineParser(String value) {
    this.value = value;
    isNullOrEmpty = StringUtils.isNullOrEmpty(value);
  }

  @Override
  public boolean isInteger() {
    return StringUtils.isInteger(value);
  }

  @Override
  public boolean isDouble() {
    return StringUtils.isDouble(value);
  }

  @Override
  public Double parseInteger() {
    return Double.parseDouble(value);
  }

  @Override
  public Double parseDouble() {
    return Double.parseDouble(value);
  }
}
