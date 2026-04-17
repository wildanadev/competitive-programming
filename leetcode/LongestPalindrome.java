public class LongestPalindrome {
  public int longestPalindrome(String s) {
    int[] count = new int[128];
    int ans = 0;
    for (char i : s.toCharArray()) {
      count[i]++;
      if ((count[i] & 1) == 0) ans += 2;
    }
    return ans < s.length() ? ans + 1 : ans;
  }
}
