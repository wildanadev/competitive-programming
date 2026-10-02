public class SmallestRangeI {
  public int smallestRangeI(int[] nums, int k) {
    int min = nums[0];
    int max = nums[0];
    for (int i : nums) {
      min = Math.min(min, i);
      max = Math.max(max, i);
    }
    return (min + k >= max - k) ? 0 : (max - k) - (min + k);
  }
}
