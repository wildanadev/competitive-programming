import java.util.PriorityQueue;

public class ConstructTargetArrayWithMultipleSums {
  public boolean isPossible(int[] target) {
    if (target.length == 1) return target[0] == 1;
    PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> Long.compare(b, a));
    long sum = 0;
    for (long t : target) {
      pq.add(t);
      sum += t;
    }
    while (pq.peek() != 1) {
      long curr = pq.poll();
      if (sum - curr == 1) return true;
      long x = curr % (sum - curr);
      sum = sum - curr + x;
      if (x == 0 || x == curr) return false;
      else pq.add(x);
    }
    return true;
  }
}
