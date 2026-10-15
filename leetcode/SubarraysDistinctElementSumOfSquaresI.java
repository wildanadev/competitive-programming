import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubarraysDistinctElementSumOfSquaresI {
  public int sumCounts(List<Integer> nums) {
    int ans = 0, n = nums.size();
    for (int i = 0; i < n; i++) {
      Set<Integer> set = new HashSet<>();
      for (int j = i; j < n; j++) {
        set.add(nums.get(j));
        int count = set.size();
        ans += count * count;
      }
    }
    return ans;
  }
}
