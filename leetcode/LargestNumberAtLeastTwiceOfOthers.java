public class LargestNumberAtLeastTwiceOfOthers {
  public int dominantIndex(int[] nums) {
    int max = -1;
    int idx = -1;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > max) {
        max = nums[i];
        idx = i;
      }
    }
    for (int i = 0; i < nums.length; i++) {
      if (i != idx && nums[i] * 2 > max) {
        return -1;
      }
    }
    return idx;
  }
}
