import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
  public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    int minValue = intervals[0][0], maxValue = intervals[0][1];
    ArrayList<List<Integer>> list = new ArrayList<>();

    for (int i = 1; i < intervals.length; i++) {
      if (intervals[i][0] <= maxValue) {
        maxValue = Math.max(maxValue, intervals[i][1]);
        continue;
      } else {
        list.add(List.of(minValue, maxValue));
        minValue = intervals[i][0];
        maxValue = intervals[i][1];
      }
    }

    list.add(List.of(minValue, maxValue));

    int[][] result = new int[list.size()][2];

    for (int i = 0; i < list.size(); i++) {
      result[i][0] = list.get(i).get(0);
      result[i][1] = list.get(i).get(1);
    }

    return result;
  }
}
