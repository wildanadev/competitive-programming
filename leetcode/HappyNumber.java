public class HappyNumber {
  public boolean isHappy(int n) {
    int s = getNextNumber(n);
    int f = getNextNumber(getNextNumber(n));
    while (s != f) {
      if (f == 1) return true;
      s = getNextNumber(s);
      f = getNextNumber(getNextNumber(f));
    }
    return f == 1;
  }

  private int getNextNumber(int n) {
    int res = 0;
    while (n > 0) {
      int digit = n % 10;
      res += digit * digit;
      n /= 10;
    }
    return res;
  }

  public static void main(String[] args) {
    System.out.println(new HappyNumber().isHappy(20));
  }
}
