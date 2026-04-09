public class FirstMatchingCharacterFromBothEnds {
  public static int firstMatchingIndex(String s) {
    int i = 0;
    int n = s.length() - 1;
    while (i <= n) {
      // System.out.println(i);
      // System.out.println(n);
      // System.out.println(s.charAt(i));
      // System.out.println(s.charAt(n));
      if (s.charAt(i) == s.charAt(n)) return i;
      i++;
      n--;
    }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(firstMatchingIndex("abcdab"));
  }
}
