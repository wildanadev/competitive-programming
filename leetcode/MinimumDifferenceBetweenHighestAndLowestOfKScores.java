import java.util.Arrays;

public class MinimumDifferenceBetweenHighestAndLowestOfKScores {
  public int minimumDifference(int[] nums, int k) {
    int ans = 1_00_001;
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - k + 1; i++) {
      ans = Math.min(ans, nums[i + k - 1] - nums[i]);
    }
    return ans;
  }
}
