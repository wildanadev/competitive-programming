import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FindAllNumbersDisappearedInAnArrayII {
  public List<List<Integer>> findDisappearedNumbers(int[] nums, int lower, int upper) {
    List<List<Integer>> ans = new ArrayList<>();
    HashSet<Integer> dict = new HashSet<Integer>();
    for (int i : nums) dict.add(i);
    int start = lower;
    int end = lower;
    for (int i = lower; i <= upper; i++) {
      if (dict.contains(i)) {
        if (start != i) {
          ans.add(List.of(start, end));
        }
        start = i + 1;
      }
      end = i;
    }
    if (start <= end) ans.add(List.of(start, end));
    return ans;
  }
}
