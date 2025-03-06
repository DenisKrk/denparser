package denparser.stats;

public class StatsOptions implements IStatsOptions {
  private boolean isShortStats;
  private boolean isFullStats;

  public StatsOptions(boolean isShortStats, boolean isFullStats) {
    this.isShortStats = isShortStats;
    this.isFullStats = isFullStats;
  }

  @Override
  public boolean getIsShortStats() {
    return isShortStats;
  }

  @Override
  public boolean getIsFullStats() {
    return isFullStats;
  }
}
