package denparser.stats;

public interface IAccumulatorDataStats<T> {
  void nextElement(T next);
  T getMaximal();
  T getMinimal();
  T getSum();
  T getQuantity();
  T getAverage();
}
