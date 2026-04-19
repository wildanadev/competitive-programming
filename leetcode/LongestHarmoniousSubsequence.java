import java.util.HashMap;

public class LongestHarmoniousSubsequence {
  public int findLHS(int[] nums) {
    int ans = 0;
    HashMap<Integer, Integer> dict = new HashMap<>();
    for (int i : nums) dict.put(i, dict.getOrDefault(i, 0) + 1);
    for (int i : dict.keySet()) {
      if (dict.containsKey(i + 1)) {
        ans = Math.max(ans, dict.get(i) + dict.get(i + 1));
      }
    }
    return ans;
  }
}
