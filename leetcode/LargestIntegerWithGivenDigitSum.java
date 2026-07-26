public class LargestIntegerWithGivenDigitSum {
  public int largestInteger(int n, int s) {
    if (s > (n * 9)) return -1;
    if (s == 0) return 0;
    StringBuilder sb = new StringBuilder();
    int count = s / 9;
    while (count-- > 0) {
      sb.append("9");
      n--;
    }
    if (n > 0) {
      sb.append(String.valueOf(s - (s / 9 * 9)));
      n--;
      for (int i = n; i > 0; i--) sb.append("0");
    }
    return Integer.parseInt(sb.toString());
  }
}
