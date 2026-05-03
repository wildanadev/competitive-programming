public class CountIndicesWithOppositeParity {
  public int[] countOppositeParity(int[] nums) {
    int[] ans = new int[nums.length];
    int oddCount = 0, evenCount = 0;
    for (int i = nums.length - 1; i >= 0; i--) {
      if ((nums[i] & 1) == 1) {
        oddCount++;
        ans[i] = evenCount;
      } else {
        evenCount++;
        ans[i] = oddCount;
      }
    }
    return ans;
  }
}
