public class RemoveOuterMostParentheses {
  public String removeOuterParentheses(String s) {
    StringBuilder sb = new StringBuilder();
    int open = 0;
    for (char i : s.toCharArray()) {
      if (i == '(' && open++ > 0) sb.append(i);
      if (i == ')' && open-- > 1) sb.append(i);
    }
    return sb.toString();
  }
}
