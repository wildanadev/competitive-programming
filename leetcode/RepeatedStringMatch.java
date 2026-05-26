public class RepeatedStringMatch {
  public int repeatedStringMatch(String a, String b) {
    int count = (int) Math.ceil((double) b.length() / a.length());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < count; i++) sb.append(a);
    if (sb.toString().contains(b)) return count;
    sb.append(a);
    if (sb.toString().contains(b)) return count + 1;
    return -1;
  }
}
