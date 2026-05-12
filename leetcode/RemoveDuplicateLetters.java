import java.util.ArrayDeque;

public class RemoveDuplicateLetters {
  public String removeDuplicateLetters(String s) {
    int[] lastIndex = new int[26];
    for (int i = 0; i < s.length(); i++) lastIndex[s.charAt(i) - 'a'] = i;
    boolean[] visit = new boolean[26];
    ArrayDeque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
      int curr = s.charAt(i) - 'a';
      if (visit[curr]) continue;
      while (!stack.isEmpty() && stack.peek() > curr && i < lastIndex[stack.peek()]) {
        visit[stack.pop()] = false;
      }
      stack.push(curr);
      visit[curr] = true;
    }
    StringBuilder sb = new StringBuilder();
    while (!stack.isEmpty()) sb.append((char) (stack.pop() + 'a'));
    return sb.reverse().toString();
  }

  public static void main(String[] args) {
    System.out.println(new RemoveDuplicateLetters().removeDuplicateLetters("cbacdcbc"));
  }
}
