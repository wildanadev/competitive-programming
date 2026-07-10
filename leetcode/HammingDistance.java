public class HammingDistance {
  public int hammingDistance(int x, int y) {
    int xor = x ^ y;
    int ans = 0;
    while (xor > 0) {
      // check lsb
      if ((xor & 1) == 1) ans++;
      // right shift
      xor = xor >> 1;
    }
    return ans;
  }
}
