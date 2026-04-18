import java.util.Arrays;

public class AssignCookies {
  public int findContentChildren(int[] g, int[] s) {
    int sg = 0;
    int ss = 0;
    Arrays.sort(g);
    Arrays.sort(s);
    while (sg < g.length && ss < s.length) {
      if (s[ss] >= g[sg]) {
        sg++;
      }
      ss++;
    }
    return sg;
  }
}
