public class Sqrt {
  public int mySqrt(int x) {
    if (x == 0 || x == 1) return x;
    int l = 0, r = x;
    while (l <= r) {
      int m = l + (r - l) / 2;
      if ((long) m * m == x) return m;
      else if ((long) m * m > x) r = m - 1;
      else l = m + 1;
    }
    return r;
  }
}
