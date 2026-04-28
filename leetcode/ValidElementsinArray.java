import java.util.ArrayList;
import java.util.List;

public class ValidElementsinArray {
  public List<Integer> findValidElements(int[] nums) {
    int n = nums.length;
    boolean[] valid = new boolean[n];

    // Prefix maximum
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      if (nums[i] > max) {
        valid[i] = true;
        max = nums[i];
      }
    }

    // Suffix maximum
    max = Integer.MIN_VALUE;
    for (int i = n - 1; i >= 0; i--) {
      if (nums[i] > max) {
        valid[i] = true;
        max = nums[i];
      }
    }

    List<Integer> ans = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (valid[i]) ans.add(nums[i]);
    }

    return ans;
  }
}
