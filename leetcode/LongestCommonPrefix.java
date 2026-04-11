import java.util.Arrays;

public class LongestCommonPrefix {
  public String longestCommonPrefix(String[] strs) {
    Arrays.sort(strs);
    String ans = "";
    for (int i = 0; i < strs[0].length(); i++) {
      char c = strs[0].charAt(i);
      for (int j = 1; j < strs.length; j++) {
        if (c != strs[j].charAt(i)) return ans;
      }
      ans += c;
    }
    return ans;
  }
}
