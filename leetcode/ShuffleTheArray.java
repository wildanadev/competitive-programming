public class ShuffleTheArray {
  public int[] shuffle(int[] nums, int n) {
    int[] ans = new int[nums.length];
    int t = 0;
    for (int i = 0; i < n; i++) {
      ans[t++] = nums[i];
      ans[t++] = nums[n + i];
    }
    return ans;
  }
}
