public class BinaryDigitScore {
  public int calculateScore(int a, int b) {
    int ans = 0;
    int s1 = setBitBinary(a);
    int s2 = setBitBinary(b);
    if (s1 >= s2) ans = s1 * s2;
    else ans = s2 / s1;
    return ans;
  }

  private int setBitBinary(int value) {
    int count = 0;
    while (value > 0) {
      count = value % 2 == 0 ? count : count + 1;
      value /= 2;
    }
    return count;
  }
}
