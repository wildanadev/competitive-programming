public class SubStringOfSizeThreeWithDistinctCharacters {
  public int countGoodSubstrings(String s) {
    int ans = 0;
    if (s.length() < 3) return ans;
    for (int i = 0; i < s.length() - 3 + 1; i++) {
      if (s.charAt(i) != s.charAt(i + 1)
          && s.charAt(i) != s.charAt(i + 2)
          && s.charAt(i + 1) != s.charAt(1 + 2)) {
        ans++;
      }
    }
    return ans;
  }
}
