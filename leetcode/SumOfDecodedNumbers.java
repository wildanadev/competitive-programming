public class SumOfDecodedNumbers {
  public int sumDecoded(long[] nums) {
    long ans = 0;
    long mod = 1_000_000_007L;

    for (var i : nums) {
      long width = i % 10;
      long d = i / 10;

      String dStr = String.valueOf(d);
      int len = dStr.length();
      int shift = len - (int) width;
      long divisor = 1;
      for (int s = 0; s < shift; s++) {
        divisor *= 10;
      }

      long x = d / divisor;
      long y = d % divisor;

      long resultPow = powerMod(x, y, mod);

      ans = (ans + resultPow) % mod;
    }

    return (int) ans;
  }

  private long powerMod(long base, long exp, long mod) {
    long res = 1;
    base = base % mod;
    while (exp > 0) {
      if (exp % 2 == 1) {
        res = (res * base) % mod;
      }
      base = (base * base) % mod;
      exp /= 2;
    }
    return res;
  }
}
