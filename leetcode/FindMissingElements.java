import java.util.ArrayList;
import java.util.List;

public class FindMissingElements {
  public List<Integer> findMissingElements(int[] nums) {
    List<Integer> ans = new ArrayList<>();
    boolean[] seen = new boolean[101];
    int min = nums[0], max = nums[0];
    for (int i : nums) {
      min = Math.min(min, i);
      max = Math.max(max, i);
      seen[i] = true;
    }
    for (int i = min; i <= max; i++) if (!seen[i]) ans.add(i);
    return ans;
  }
}
