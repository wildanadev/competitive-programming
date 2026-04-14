public class FindTheDifference {
  public char findTheDifference(String s, String t) {
    int ans = 0;
    for (char i : s.toCharArray()) {
      ans ^= i;
    }
    for (char i : t.toCharArray()) {
      ans ^= i;
    }
    return (char) ans;
  }
}
