
public class CountSubstringsThatSatisfyKConstraintI {
  public int countKConstraintSubstrings(String s, int k) {
    int ans = 0, zero = 0, one = 0;
    for (int i = 0, j = 0; i < s.length(); i++) {
      if (s.charAt(i) == '0') zero++;
      else one++;
      while (zero > k && one > k) {
        if (s.charAt(j) == '0') zero--;
        else one--;
        j++;
      }
      ans += i - j + 1;
    }
    return ans;
  }
}
