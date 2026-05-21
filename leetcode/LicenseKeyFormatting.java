public class LicenseKeyFormatting {
  public String licenseKeyFormatting(String s, int k) {
    StringBuilder ans = new StringBuilder();
    int cnt = 0;
    char[] license = s.toUpperCase().toCharArray();
    for (int i = license.length - 1; i >= 0; i--) {
      if (license[i] != '-') {
        ans.append(license[i]);
        cnt++;
        if (cnt == k) {
          cnt = 0;
          ans.append('-');
        }
      }
    }
    if (ans.length() > 0 && ans.charAt(ans.length() - 1) == '-') ans.deleteCharAt(ans.length() - 1);
    return ans.reverse().toString();
  }
}
