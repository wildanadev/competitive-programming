public class NumberOfLinesToWriteString {
  public int[] numberOfLines(int[] widths, String s) {
    int[] ans = new int[2];
    int curr = 0;
    for (char i : s.toCharArray()) {
      if (curr + widths[i - 'a'] > 100) {
        ans[0]++;
        curr = 0;
      }
      curr += widths[i - 'a'];
    }
    ans[0]++;
    ans[1] = curr;
    return ans;
  }
}
