public class SortColors {
  public void sortColors(int[] nums) {
    int l = 0, m = 0, h = nums.length - 1;
    while (m <= h) {
      if (nums[m] == 0) {
        int temp = nums[m];
        nums[m++] = nums[l];
        nums[l++] = temp;
      } else if (nums[m] == 1) {
        m++;
      } else {
        int temp = nums[h];
        nums[h--] = nums[m];
        nums[m] = temp;
      }
    }
  }
}
