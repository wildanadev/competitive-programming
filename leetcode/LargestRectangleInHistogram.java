import java.util.ArrayDeque;

public class LargestRectangleInHistogram {
  public int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int ans = 0;
    ArrayDeque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i <= n; i++) {
      int currHeight = (i == n) ? 0 : heights[i];
      while (!stack.isEmpty() && heights[stack.peek()] > currHeight) {
        int height = heights[stack.pop()];
        int width = stack.isEmpty() ? i : i - stack.peek() - 1;
        ans = Math.max(ans, height * width);
      }
      stack.push(i);
    }
    return ans;
  }
}
