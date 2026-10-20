public class DIStringMatch {
  public int[] diStringMatch(String s) {
    int n = s.length();
    int left = 0, right = n;
    int[] ans = new int[n + 1];
    for (int i = 0; i < n; i++) ans[i] = s.charAt(i) == 'I' ? left++ : right--;
    ans[n] = left;
    return ans;
  }
}
