import java.util.PriorityQueue;

public class KthLargestElementInAStream {
  class KthLargest {
    final int k;
    final PriorityQueue<Integer> nums;

    public KthLargest(int k, int[] nums) {
      this.k = k;
      this.nums = new PriorityQueue<Integer>();
      for (int i : nums) add(i);
    }

    public int add(int val) {
      if (nums.size() < k) {
        nums.offer(val);
      } else if (nums.peek() < val) {
        nums.poll();
        nums.offer(val);
      }
      return nums.peek();
    }
  }
}
