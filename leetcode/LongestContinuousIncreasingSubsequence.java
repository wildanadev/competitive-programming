public class LongestContinuousIncreasingSubsequence {
  public int findLengthOfLCIS(int[] nums) {
    int ans = 0;
    int cnt = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i == 0 || nums[i - 1] < nums[i]) ans = Math.max(ans, ++cnt);
      else cnt = 1;
    }
    return ans;
  }
}
