public class IsSubsequence {
  public boolean isSubsequence(String s, String t) {
    if (s.length() == 0) return true;
    char[] s1 = s.toCharArray();
    char[] t1 = t.toCharArray();
    int c = 0;
    for (char i : t1) {
      if (c >= s1.length) break;
      if (s1[c] == i) c++;
    }
    return c == s1.length;
  }
}
