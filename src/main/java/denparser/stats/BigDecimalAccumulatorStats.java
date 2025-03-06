package denparser.stats;

public class BigDecimalAccumulatorStats implements IAccumulatorDataStats<BigDecimal> {
  private BigDecimal sumOfElements;
  private BigDecimal qantityOfElements;
  private BigDecimal minimum;
  private BigDecimal maximum;

  public DoubleAccumulatorStats() {}

  public DoubleAccumulatorStats(BigDecimal seed) {
    qantityOfElements = new BigDecimal("1");
    minimum = seed;
    maximum = seed;
    sumOfElements = seed
  }

  @Override
  public void nextElement(Double next) {
    considerElement(next);
  }

  @Override
  public BigDecimal getMaximal() {
    return maximum;
  }

  @Override
  public BigDecimal getMinimal() {
    return minimum;
  }

  @Override
  public Double getQuantity() {
    return qantityOfElements;
  }

  @Override
  public BigDecimal getSum() {
    return sumOfElements;
  }

  @Override
  public BigDecimal getAverage() {
    if (qantityOfElements.compareTo(new BigDecimal("1")) < 0) {
      return new BigDecimal("0");
    }
    return sumOfElements.divide(qantityOfElements);
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
