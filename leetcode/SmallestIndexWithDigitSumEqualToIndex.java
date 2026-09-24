public class SmallestIndexWithDigitSumEqualToIndex {
  public int smallestIndex(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
      if (sumDigit(nums[i]) == i) return i;
    }
    return -1;
  }

  private int sumDigit(int value) {
    int ans = 0;
    while (value > 0) {
      ans += value % 10;
      value /= 10;
    }
    return ans;
  }
}
