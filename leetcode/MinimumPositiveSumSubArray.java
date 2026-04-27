import java.util.List;

public class MinimumPositiveSumSubArray {
  public int minimumSumSubarray(List<Integer> nums, int l, int r) {
    int ans = Integer.MAX_VALUE;
    while (l <= r) {
      int window = 0;
      for (int i = 0; i < l; i++) window += nums.get(i);
      if (window > 0) ans = Math.min(ans, window);
      for (int i = l; i < nums.size(); i++) {
        window += nums.get(i) - nums.get(i - l);
        if (window > 0) ans = Math.min(ans, window);
      }
      l++;
    }
    return ans != Integer.MAX_VALUE ? ans : -1;
  }
}
