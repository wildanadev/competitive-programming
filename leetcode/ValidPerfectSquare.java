public class ValidPerfectSquare {
  public boolean isPerfectSquare(int num) {
    int l = 1, r = num;
    while (l <= r) {
      int m = l + (r - l) / 2;
      long sqr = m;
      if (sqr * sqr == (long) num) return true;
      else if (sqr * sqr > (long) num) r = m - 1;
      else l = m + 1;
    }
    return false;
  }
}
