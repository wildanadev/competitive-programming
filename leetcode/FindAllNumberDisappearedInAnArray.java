import java.util.ArrayList;
import java.util.List;

public class FindAllNumberDisappearedInAnArray {
  public List<Integer> findDisappearedNumbers(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
      int idx = Math.abs(nums[i]) - 1;
      if (nums[idx] > 0) nums[idx] = -nums[idx];
    }
    List<Integer> ans = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > 0) ans.add(i + 1);
    }
    return ans;
  }
}
