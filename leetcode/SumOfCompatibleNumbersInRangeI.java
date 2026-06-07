public class SumOfCompatibleNumbersInRangeI {
  public int sumOfGoodIntegers(int n, int k) {
    int ans = 0;
    for (int i = 1; i <= n + k; i++) if (Math.abs(n - i) <= k && (n & i) == 0) ans += i;
    return ans;
  }
}
