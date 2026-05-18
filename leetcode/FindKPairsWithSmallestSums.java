import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class FindKPairsWithSmallestSums {
  public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<List<Integer>> ans = new ArrayList<>();
    PriorityQueue<int[]> pq =
        new PriorityQueue<>((a, b) -> (nums1[a[0]] + nums2[a[1]]) - (nums1[b[0]] + nums2[b[1]]));
    for (int i = 0; i < Math.min(nums1.length, k); i++) pq.offer(new int[] {i, 0});
    while (k-- > 0) {
      int[] cur = pq.poll();
      int i = cur[0];
      int j = cur[1];
      ans.add(List.of(nums1[i], nums2[j]));
      if (j + 1 < nums2.length) {
        pq.offer(new int[] {i, j + 1});
      }
    }
    return ans;
  }
}
