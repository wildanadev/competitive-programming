import java.util.PriorityQueue;

public class MaximumNumberOfEatenApples {
  public int eatenApples(int[] apples, int[] days) {
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    int day = 0;
    int count = 0;
    while (day < apples.length || !pq.isEmpty()) {
      if (day < apples.length && apples[day] > 0)
        pq.offer(new int[] {day + days[day], apples[day]});
      while (!pq.isEmpty() && (pq.peek()[0] <= day || pq.peek()[1] == 0)) pq.poll();
      if (!pq.isEmpty()) {
        pq.peek()[1]--;
        count++;
      }
      day++;
    }
    return count;
  }
}
