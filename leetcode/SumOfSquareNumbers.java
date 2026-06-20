public class SumOfSquareNumbers {
  public boolean judgeSquareSum(int c) {
    long l = 0, r = (long) Math.sqrt(c);
    while (l <= r) {
      long cur = (l * l) + (r * r);
      if (cur > c) r--;
      else if (cur < c) l++;
      else return true;
    }
    return false;
  }
}
