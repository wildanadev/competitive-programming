import java.util.ArrayDeque;
import java.util.Deque;

public class RemoveAllAdjacentDuplicatesInString {
  public String removeDuplicates(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char i : s.toCharArray()) {
      if (stack.isEmpty()) stack.push(i);
      else {
        if (i == stack.peek()) {
          stack.pop();
        } else stack.push(i);
      }
    }
    StringBuilder sb = new StringBuilder();
    while (!stack.isEmpty()) sb.append(stack.pop());
    return sb.reverse().toString();
  }
}
