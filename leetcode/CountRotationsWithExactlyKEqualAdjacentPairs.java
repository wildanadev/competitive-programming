public class CountRotationsWithExactlyKEqualAdjacentPairs {
  public int countRotations(String s, int k) {
    int ans = 0;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      sb.setLength(0);
      sb.append(s.substring(i));
      sb.append(s.substring(0, i));
      int score = countAdjacentPairs(sb.toString());
      if (score == k) ans++;
    }
    return ans;
  }

  private int countAdjacentPairs(String value) {
    int count = 0;
    for (int i = 0; i < value.length() - 1; i++) {
      if (value.charAt(i) == value.charAt(i + 1)) count++;
    }
    return count;
  }
}
