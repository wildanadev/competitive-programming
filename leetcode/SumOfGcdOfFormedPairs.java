import java.util.Arrays;

public class SumOfGcdOfFormedPairs {
  private int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  public long gcdSum(int[] nums) {
    long ans = 0;
    int mx = 0;
    for (int i = 0; i < nums.length; i++) {
      mx = Math.max(mx, nums[i]);
      nums[i] = gcd(mx, nums[i]);
    }
    Arrays.sort(nums);
    int l = 0, r = nums.length - 1;
    while (l < r) {
      ans += gcd(nums[l], nums[r]);
      l++;
      r--;
    }
    return ans;
  }
}
