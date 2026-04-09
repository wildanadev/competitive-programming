public class LongestCommonPrefix {
  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";

    String pref = strs[0];
    int prefLen = pref.length();

    for (int i = 1; i < strs.length; i++) {
      String s = strs[i];
      while (prefLen > s.length() || !pref.equals(s.substring(0, prefLen))) {
        prefLen--;
        if (prefLen == 0) {
          return "";
        }
        pref = pref.substring(0, prefLen);
      }
    }
    return pref;
  }

  public static void main(String[] args) {
    // String[] ar = new String[] {"dog", "racecar", "car"};
    // Arrays.sort(ar);
    // int count = 0;
    // for (int i = 0; i < ar[0].length(); i++) {
    //   char x = ar[0].charAt(i);
    //   System.out.println(x);
    //   for (int j = 1; j < ar.length; j++) {
    //     char t = ar[j].charAt(i);
    //     System.out.println(t);
    //     if (x != t) {
    //       System.out.println(count);
    //       return;
    //     } else {
    //       count++;
    //     }
    //   }
    // }
    System.out.println(
        new LongestCommonPrefix().longestCommonPrefix(new String[] {"flower", "flow", "a"}));
  }
}
