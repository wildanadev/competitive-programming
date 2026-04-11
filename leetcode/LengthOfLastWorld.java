public class LengthOfLastWorld {
  public int lengthOfLastWord(String s) {
    int ans = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      if (s.charAt(i) == ' ' && ans > 0) break;
      if (s.charAt(i) != ' ') ans++;
    }
    return ans;
  }
}
