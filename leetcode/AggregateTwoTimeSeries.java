import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

class Solution {
  public List<List<Integer>> aggregateTimeSeries(int[][] series1, int[][] series2) {
    List<List<Integer>> ans = new ArrayList<>();
    int seriesIndx1 = 0, seriesIndx2 = 0;
    HashSet<Integer> markTimeStamp = new HashSet<Integer>();
    while (seriesIndx1 < series1.length) {
      int[] series1Data = series1[seriesIndx1++];
      int sum = 0;
      sum += series1Data[1];
      while (seriesIndx2 < series2.length && series2[seriesIndx2][0] < series1Data[0])
        seriesIndx2++;
      if (seriesIndx2 < series2.length) sum += series2[seriesIndx2][1];
      markTimeStamp.add(series1Data[0]);
      ans.add(new ArrayList(List.of(series1Data[0], sum)));
    }
    seriesIndx1 = 0;
    seriesIndx2 = 0;
    while (seriesIndx2 < series2.length) {
      int[] series2Data = series2[seriesIndx2++];
      if (markTimeStamp.contains(series2Data[0])) continue;
      int sum = 0;
      sum += series2Data[1];
      while (seriesIndx1 < series1.length && series1[seriesIndx1][0] < series2Data[0])
        seriesIndx1++;
      if (seriesIndx1 < series1.length) sum += series1[seriesIndx1][1];
      ans.add(new ArrayList(List.of(series2Data[0], sum)));
    }
    Collections.sort(ans, (a, b) -> a.get(0) - b.get(0));
    return ans;
  }
}
