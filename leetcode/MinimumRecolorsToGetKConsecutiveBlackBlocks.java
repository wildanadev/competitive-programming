public class MinimumRecolorsToGetKConsecutiveBlackBlocks {
  public int minimumRecolors(String blocks, int k) {
    int ans = 0;
    for (int i = 0; i < k; i++) if (blocks.charAt(i) == 'W') ans++;
    int window = ans;
    for (int i = k; i < blocks.length(); i++) {
      if (blocks.charAt(i) == 'W') window++;
      if (blocks.charAt(i - k) == 'W') window--;
      ans = Math.min(ans, window);
    }
    return ans;
  }
}
