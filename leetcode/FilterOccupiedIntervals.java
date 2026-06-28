import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterOccupiedIntervals {
  public List<List<Integer>> filterOccupiedIntervals(
      int[][] occupiedIntervals, int freeStart, int freeEnd) {

    // Step 1: Sort dan merge interval yang overlap/bersentuhan
    Arrays.sort(occupiedIntervals, (a, b) -> a[0] - b[0]);
    List<int[]> merged = new ArrayList<>();
    int curStart = occupiedIntervals[0][0];
    int curEnd = occupiedIntervals[0][1];

    for (int i = 1; i < occupiedIntervals.length; i++) {
      int[] iv = occupiedIntervals[i];
      if (iv[0] <= curEnd + 1) {
        curEnd = Math.max(curEnd, iv[1]);
      } else {
        merged.add(new int[] {curStart, curEnd});
        curStart = iv[0];
        curEnd = iv[1];
      }
    }
    merged.add(new int[] {curStart, curEnd});

    // Step 2: Subtract free interval dari setiap merged interval
    List<List<Integer>> result = new ArrayList<>();
    for (int[] iv : merged) {
      int s = iv[0], e = iv[1];

      // Tidak overlap sama sekali dengan free interval
      if (freeEnd < s || freeStart > e) {
        result.add(List.of(s, e));
        continue;
      }

      // Bagian kiri tersisa (sebelum freeStart)
      if (s < freeStart) {
        result.add(List.of(s, freeStart - 1));
      }
      // Bagian kanan tersisa (setelah freeEnd)
      if (e > freeEnd) {
        result.add(List.of(freeEnd + 1, e));
      }
    }
    return result;
  }
}
