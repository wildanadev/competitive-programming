import java.util.ArrayDeque;

/** ValidParentheses */
public class ValidParentheses {
  public boolean isValid(String s) {
    ArrayDeque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
      if (c == '(' || c == '{' || c == '[') {
        stack.push(c);
      } else {
        if (stack.isEmpty()) return false;
        char top = stack.poll();
        if ((c == ')' && top != '(') || (c == '}' && top != '{') || (c == ']' && top != '[')) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }

  public static void main(String[] args) {
    System.out.println(new ValidParentheses().isValid("("));
  }
}
