public class ShortestSubArrayWithOrAtLeastKI {
  public int minimumSubarrayLength(int[] nums, int k) {
    int ans = Integer.MAX_VALUE;
    for (int i = 0; i < nums.length; i++) {
      int window = 0;
      for (int j = i; j < nums.length; j++) {
        window |= nums[j];
        if (window >= k) {
          ans = Math.min(ans, j - i + 1);
          break;
        }
      }
    }
    return ans == Integer.MAX_VALUE ? -1 : ans;
  }
}
