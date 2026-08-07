import java.util.Arrays;

public class HeightChecker {
  public int heightChecker(int[] heights) {
    int[] expected = Arrays.copyOf(heights, heights.length);
    Arrays.sort(expected);
    int ans = 0;
    for (int i = 0; i < expected.length; i++) if (heights[i] != expected[i]) ans++;
    return ans;
  }
}
