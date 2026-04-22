public class SortArrayByParity {
  public int[] sortArrayByParity(int[] nums) {
    int l = 0, r = nums.length - 1;
    while (l < r) {
      if ((nums[l] & 1) == 1 && (nums[r] & 1) == 0) {
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r] = tmp;
      }
      if ((nums[l] & 1) == 0) l++;
      if ((nums[r] & 1) == 1) r--;
    }
    return nums;
  }
}
