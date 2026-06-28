import java.util.Arrays;

public class MaximumTotalSumOfKSelectedElements {
  public long maxSum(int[] nums, int k, int mul) {
    long ans = 0;
    Arrays.sort(nums);
    for (int i = nums.length - 1; i >= nums.length - k; i--) {
      ans += Math.max((long) mul * nums[i], nums[i]);
      mul--;
    }
    return ans;
  }
}
