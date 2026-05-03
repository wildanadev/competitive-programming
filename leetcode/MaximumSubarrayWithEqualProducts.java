public class MaximumSubarrayWithEqualProducts {
  private int gcd(int a, int b) {
    if (b == 0) return a;
    return gcd(b, a % b);
  }

  private int lcm(int a, int b) {
    return a / gcd(a, b) * b;
  }

  public int maxLength(int[] nums) {
    int n = nums.length;
    int ans = 0;
    for (int i = 0; i < n; i++) {
      int currGCD = nums[i];
      int currLCM = nums[i];
      int currPro = nums[i];
      for (int j = i + 1; j < n; j++) {
        currPro *= nums[j];
        currGCD = gcd(currGCD, nums[j]);
        currLCM = lcm(currLCM, nums[j]);
        if (currPro == currLCM * currGCD) {
          ans = Math.max(ans, j - i + 1);
        }
      }
    }
    return ans;
  }
}
