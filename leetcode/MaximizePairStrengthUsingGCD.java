public class MaximizePairStrengthUsingGCD {
  public int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  public long maxPairStrength(int[] nums) {
    long ans = 0;
    for (int i = 0; i < nums.length; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        long numi = nums[i];
        long numj = nums[j];
        long gcdNum = gcd(nums[i], nums[j]);
        ans = Math.max(ans, numi * numj / (gcdNum * gcdNum));
      }
    }
    return ans;
  }
}
