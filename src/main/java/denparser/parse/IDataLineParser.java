package denparser.parse;

public interface IDataLineParser {
  boolean isInteger();
  boolean isDouble();
  Double parseInteger();
  Double parseDouble();
}
