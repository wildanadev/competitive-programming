import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ValidElementsinArray {
  public List<Integer> findValidElements(int[] nums) {
    List<Integer> ans = new ArrayList<>();
    TreeMap<Integer, Integer> value = new TreeMap<>();
    int mLeft = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > mLeft) {
        mLeft = nums[i];
        value.put(i, nums[i]);
      }
    }

    int mRight = Integer.MIN_VALUE;
    for (int i = nums.length - 1; i >= 0; i--) {
      if (nums[i] > mRight) {
        mRight = nums[i];
        value.put(i, nums[i]);
      }
    }

    for (int i : value.keySet()) {
      ans.add(value.get(i));
    }
    return ans;
  }
}
