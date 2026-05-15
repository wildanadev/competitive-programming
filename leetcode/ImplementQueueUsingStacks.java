import java.util.ArrayDeque;

class MyQueue {
  ArrayDeque<Integer> stack;
  ArrayDeque<Integer> stack1;

  public MyQueue() {
    stack = new ArrayDeque<>();
    stack1 = new ArrayDeque<>();
  }

  public void push(int x) {
    stack.push(x);
  }

  public int pop() {
    transfer();
    return stack1.pop();
  }

  public int peek() {
    transfer();
    return stack1.peek();
  }

  public boolean empty() {
    return stack.isEmpty() && stack1.isEmpty();
  }

  private void transfer() {
    if (stack1.isEmpty()) while (!stack.isEmpty()) stack1.push(stack.pop());
  }
}
