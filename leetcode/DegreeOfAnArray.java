import java.util.HashMap;
import java.util.Map;

public class DegreeOfAnArray {
  public int findShortestSubArray(int[] nums) {
    Map<Integer, int[]> info = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      final int idx = i;
      info.computeIfAbsent(nums[i], k -> new int[] {idx, idx, 0});
      int[] data = info.get(nums[i]);
      data[1] = i;
      data[2]++;
    }

    int degree = 0;
    for (int[] data : info.values()) degree = Math.max(degree, data[2]);

    int ans = nums.length;
    for (int[] data : info.values())
      if (data[2] == degree) ans = Math.min(ans, data[1] - data[0] + 1);
    return ans;
  }
}
