public class MaximumNestingDepthOfTheParentheses {
  public int maxDepth(String s) {
    int ans = 0;
    int counting = 0;
    for (char i : s.toCharArray()) {
      if (i == '(') counting++;
      if (i == ')') counting--;
      ans = Math.max(ans, counting);
    }
    return ans;
  }
}
