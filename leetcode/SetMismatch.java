import java.util.HashMap;
import java.util.Map;

public class SetMismatch {
  public int[] findErrorNums(int[] nums) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int n : nums) count.put(n, count.getOrDefault(n, 0) + 1);
    int duplicate = 0, missing = 0;
    for (int i = 1; i <= nums.length; i++) {
      int freq = count.getOrDefault(i, 0);
      if (freq == 2) duplicate = i;
      if (freq == 0) missing = i;
    }
    return new int[] {duplicate, missing};
  }
}
