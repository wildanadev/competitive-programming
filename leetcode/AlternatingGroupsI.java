public class AlternatingGroupsI {
  public int numberOfAlternatingGroups(int[] colors) {
    int ans = 0;
    for (int i = 0; i < colors.length; i++) {
      int cur = colors[i];
      int next = colors[(i + 1) == colors.length ? 0 : i + 1];
      int prev = colors[(i - 1) < 0 ? colors.length - 1 : i - 1];
      if (cur != next && cur != prev) ans++;
    }
    return ans;
  }
}
