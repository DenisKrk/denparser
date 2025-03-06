package denparser.stats;

public class DoubleAccumulatorStats implements IAccumulatorDataStats<Double> {
  private Double sumOfElements = 0.0;
  private Double qantityOfElements = 0.0;
  private Double minimum = Double.MAX_VALUE;
  private Double maximum = Double.MIN_VALUE;

  public DoubleAccumulatorStats() {}

  public DoubleAccumulatorStats(Double seed) {
    considerElement(seed);
  }

  @Override
  public void nextElement(Double next) {
    considerElement(next);
  }

  @Override
  public Double getMaximal() {
    return maximum;
  }

  @Override
  public Double getMinimal() {
    return minimum;
  }

  @Override
  public Double getQuantity() {
    return qantityOfElements;
  }

  @Override
  public Double getSum() {
    return sumOfElements;
  }

  @Override
  public Double getAverage() {
    if (qantityOfElements < 1) {
      return 0.0;
    }
    return sumOfElements / qantityOfElements;
  }

  private void considerElement(Double element) {
    qantityOfElements++;
    sumOfElements += element;

    if (element < minimum) {
      minimum = element;
    }

    if (element > maximum) {
      maximum = element;
    }
  }
}
