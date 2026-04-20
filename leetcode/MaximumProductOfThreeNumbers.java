import java.util.Arrays;

public class MaximumProductOfThreeNumbers {
  public int maximumProduct(int[] nums) {
    Arrays.sort(nums);
    int n = nums.length;
    int l = nums[0] * nums[1] * nums[n - 1];
    int r = nums[n - 1] * nums[n - 2] * nums[n - 3];
    return l > r ? l : r;
  }
}
