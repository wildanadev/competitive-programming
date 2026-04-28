public class LongestEvenOddSubarrayWithThreshold {
  public int longestAlternatingSubarray(int[] nums, int threshold) {
    int ans = 0;
    int i = 0;
    int n = nums.length;
    while (i < n) {
      if (nums[i] % 2 != 0 || nums[i] > threshold) {
        i++;
        continue;
      }
      int j = i + 1;
      while (j < n && nums[j] <= threshold && nums[j] % 2 != nums[j - 1] % 2) {
        j++;
      }
      ans = Math.max(ans, j - i);
      i = j;
    }
    return ans;
  }
}
