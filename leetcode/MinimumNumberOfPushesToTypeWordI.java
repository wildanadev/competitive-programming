public class MinimumNumberOfPushesToTypeWordI {
  public int minimumPushes(String word) {
    int n = word.length() / 8;
    int a = 8;
    int d = 8;
    int nForm = n * (2 * a + (n - 1) * d) / 2;
    return nForm + (word.length() - (n * 8)) * (n + 1);
  }
}
