public class NumberOfIntersectingIntervalPairsI {
  public int countIntersectingIntervals(int[][] intervals) {
    int ans = 0;
    for (int i = 0; i < intervals.length; i++) {
      int start = intervals[i][0];
      int end = intervals[i][1];
      for (int j = i + 1; j < intervals.length; j++) {
        int startTemp = intervals[j][0];
        int endTemp = intervals[j][1];
        if (end >= startTemp && start <= endTemp) ans++;
      }
    }
    return ans;
  }
}
