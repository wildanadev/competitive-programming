import java.util.ArrayDeque;

public class ImplementStackUsingQueues {
  ArrayDeque<Integer> queue;

  public ImplementStackUsingQueues() {
    queue = new ArrayDeque<>();
  }

  public void push(int x) {
    queue.offer(x);
    for (int i = 1; i < queue.size(); i++) {
      queue.offer(queue.removeFirst());
    }
  }

  public int pop() {
    return queue.poll();
  }

  public int top() {
    return queue.peek();
  }

  public boolean empty() {
    return queue.isEmpty();
  }
}
