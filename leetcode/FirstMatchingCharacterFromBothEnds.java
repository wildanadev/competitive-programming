public class FirstMatchingCharacterFromBothEnds {
  public int firstMatchingIndex(String s) {
    int i = 0;
    int n = s.length() - 1;
    while (i <= n) {
      if (s.charAt(i) == s.charAt(n)) return i;
      i++;
      n--;
    }
    return -1;
  }
}
