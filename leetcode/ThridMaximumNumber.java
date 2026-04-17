import java.util.Arrays;

public class ThridMaximumNumber {
  public int thirdMax(int[] nums) {
    int r = nums.length - 1;
    int c = 1;
    Arrays.sort(nums);
    int ans = nums[r];
    for (int i = r - 1; i >= 0; i--) {
      if (c == 3) break;
      if (ans > nums[i]) {
        ans = nums[i];
        c++;
      }
    }
    return (c == 3) ? ans : nums[r];
  }
}
