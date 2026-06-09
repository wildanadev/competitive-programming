public class WaysToMakeAFairArray {
  public int waysToMakeFair(int[] nums) {
    int res = 0, n = nums.length, left[] = new int[2], right[] = new int[2];
    for (int i = 0; i < n; i++) right[i % 2] += nums[i];
    for (int i = 0; i < n; i++) {
      right[i % 2] -= nums[i];
      if (left[0] + right[1] == left[1] + right[0]) res++;
      left[i % 2] += nums[i];
    }
    return res;
  }
}
