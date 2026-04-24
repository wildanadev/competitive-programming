public class ReverseStringII {
  public String reverseStr(String s, int k) {
    char[] ans = s.toCharArray();
    for (int i = 0; i < ans.length; i += 2 * k) {
      int l = i, r = Math.min(i + k - 1, ans.length - 1);
      while (l < r) {
        ans[l] ^= ans[r];
        ans[r] ^= ans[l];
        ans[l] ^= ans[r];
        l++;
        r--;
      }
    }
    return String.valueOf(ans);
  }
}
