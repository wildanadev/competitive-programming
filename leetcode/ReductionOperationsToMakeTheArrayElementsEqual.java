import java.util.Arrays;
import java.util.HashMap;

public class ReductionOperationsToMakeTheArrayElementsEqual {
  public int reductionOperations(int[] nums) {
    int ans = 0, cntAns = 0;
    if (nums.length == 1) return 0;
    Arrays.sort(nums);
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i : nums) map.put(i, map.getOrDefault(i, 0) + 1);
    System.out.println(map);
    for (int i : map.keySet()) {
      if (i != nums[0]) {
        System.out.println(i);
        cntAns += map.get(i);
        ans += cntAns;
      }
    }
    return ans;
  }
}
