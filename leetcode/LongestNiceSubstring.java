public class LongestNiceSubstring {
  public String longestNiceSubstring(String s) {
    if (s.length() == 1) return "";
    String ans = "";
    int length = 0;
    for (int i = 1; i < s.length(); i++) {
      int r = i;
      int[] upper = new int[26];
      int[] lower = new int[26];
      char[] original = new char[r + 1];
      for (int j = 0; j < r + 1; j++) {
        if (Character.isUpperCase(s.charAt(j))) {
          upper[s.charAt(j) - 'A']++;
        } else {
          lower[s.charAt(j) - 'a']++;
        }
        original[j] = s.charAt(j);
      }

      boolean flag = true;
      for (int j = 0; j < upper.length; j++) {
        if (upper[j] == 0 && lower[j] == 0) continue;
        if (!(upper[j] != 0 && lower[j] != 0)) {
          flag = false;
          break;
        }
      }

      if (flag && r + 1 > length) {
        length = r + 1;
        ans = String.valueOf(original);
      }

      String window = String.valueOf(original);
      for (int j = r + 1; j < s.length(); j++) {
        String temp = window.substring(1) + s.charAt(j);
        window = temp;

        if (Character.isUpperCase(s.charAt(j))) {
          upper[s.charAt(j) - 'A']++;
        } else {
          lower[s.charAt(j) - 'a']++;
        }

        if (Character.isUpperCase(s.charAt(j - r - 1))) {
          upper[s.charAt(j - r - 1) - 'A']--;
        } else {
          lower[s.charAt(j - r - 1) - 'a']--;
        }

        flag = true;
        for (int k = 0; k < upper.length; k++) {
          if (upper[k] == 0 && lower[k] == 0) continue;
          if (!(upper[k] != 0 && lower[k] != 0)) {
            flag = false;
            break;
          }
        }

        if (flag && r + 1 > length) {
          length = r + 1;
          ans = window;
        }
      }
    }
    return ans;
  }
}
