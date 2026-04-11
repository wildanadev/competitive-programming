import java.util.HashSet;

public class ContainsDuplicateII {
  public boolean containsNearbyDuplicate(int[] nums, int k) {
    HashSet<Integer> seen = new HashSet<Integer>();
    for (int i = 0; i < nums.length; i++) {
      if (i > k) seen.remove(nums[i - k - 1]);
      if (seen.contains(nums[i])) return true;
      seen.add(nums[i]);
    }
    return false;
  }
}
