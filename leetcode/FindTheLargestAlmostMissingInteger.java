import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindTheLargestAlmostMissingInteger {
  public int largestInteger(int[] nums, int k) {
    int ans = -1;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < nums.length - k + 1; i++) {
      Set<Integer> uniqueNums = new HashSet<>();
      for (int j = i; j < i + k; j++) uniqueNums.add(nums[j]);
      for (int j : uniqueNums) map.put(j, map.getOrDefault(j, 0) + 1);
    }
    for (Map.Entry<Integer, Integer> entry : map.entrySet())
      if (entry.getValue() == 1) ans = Math.max(ans, entry.getKey());
    return ans;
  }
}
