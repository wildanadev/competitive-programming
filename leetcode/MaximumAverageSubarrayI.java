public class MaximumAverageSubarrayI {
  public double findMaxAverage(int[] nums, int k) {
    double ans = 0;

    // count first window
    for (int i = 0; i < k; i++) {
      ans += nums[i];
    }

    // count rest of window
    double window = ans;
    for (int i = k; i < nums.length; i++) {
      window += nums[i] - nums[i - k];
      ans = Math.max(ans, window);
    }

    return ans / k;
  }
}
