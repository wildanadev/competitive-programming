import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumAbsoluteDifference {
  public List<List<Integer>> minimumAbsDifference(int[] arr) {
    Arrays.sort(arr);
    List<List<Integer>> al = new ArrayList<>();
    int minAbs = Integer.MAX_VALUE;
    for (int i = 0; i < arr.length - 1; i++) {
      int min = Math.abs(arr[i] - arr[i + 1]);
      if (min < minAbs) {
        minAbs = min;
        al.clear();
        al.add(List.of(arr[i], arr[i + 1]));
      } else if (min == minAbs) al.add(List.of(arr[i], arr[i + 1]));
    }
    return al;
  }
}
