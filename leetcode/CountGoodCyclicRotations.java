public class CountGoodCyclicRotations {
  public int countGoodRotations(int[] nums) {
    int ans = 0;
    int n = nums.length;
    int half = n / 2;
    long[] prefixSum = new long[2 * n];
    prefixSum[0] = nums[0];
    for (int i = 1; i < 2 * n; i++) prefixSum[i] = prefixSum[i - 1] + nums[i % n];
    for (int i = 0; i < n; i++) {
      long first = sumQuery(prefixSum, i, half - 1 + i, half);
      long last = sumQuery(prefixSum, (half + i) % n, (n - 1 + i) % n, half);
      if (first > last) ans++;
    }
    return ans;
  }

  public long sumQuery(long[] prefixSum, int l, int r, int half) {
    if (r < l) r = half + l - 1;
    return l == 0 ? prefixSum[r] : prefixSum[r] - prefixSum[l - 1];
  }
}
