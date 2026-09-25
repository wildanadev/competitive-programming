public class BackspaceStringCompare {
  public boolean backspaceCompare(String s, String t) {
    StringBuilder sTemp = new StringBuilder();
    StringBuilder tTemp = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '#') {
        if (sTemp.length() > 0) sTemp.deleteCharAt(sTemp.length() - 1);
      } else sTemp.append(s.charAt(i));
    }
    for (int i = 0; i < t.length(); i++) {
      if (t.charAt(i) == '#') {
        if (tTemp.length() > 0) tTemp.deleteCharAt(tTemp.length() - 1);
      } else tTemp.append(t.charAt(i));
    }
    return sTemp.toString().equals(tTemp.toString());
  }
}
