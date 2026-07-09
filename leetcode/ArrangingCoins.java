public class ArrangingCoins {
  public int arrangeCoins(int n) {
    long i = 0;
    long counters = 1;
    int ans = 0;
    while (true) {
      i += counters;
      counters++;
      if (i > n) break;
      ans++;
    }
    return ans;
  }
}
