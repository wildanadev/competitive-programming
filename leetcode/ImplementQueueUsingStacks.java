import java.util.ArrayDeque;

class MyQueue {
  ArrayDeque<Integer> stack;
  ArrayDeque<Integer> stack1;

  public MyQueue() {
    stack = new ArrayDeque<>();
    stack1 = new ArrayDeque<>();
  }

  public void push(int x) {
    if (stack.isEmpty()) stack.push(x);
    else {
      while (!stack.isEmpty()) stack1.push(stack.pop());
      stack.push(x);
      while (!stack1.isEmpty()) stack.push(stack1.pop());
    }
  }

  public int pop() {
    return stack.pop();
  }

  public int peek() {
    return stack.peek();
  }

  public boolean empty() {
    return stack.isEmpty();
  }
}
