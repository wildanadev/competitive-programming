import java.util.HashSet;

public class SmallestMissingIntegerGreaterThanSequentialPrefiSum {
  public int missingInteger(int[] nums) {
    int prefix = nums[0];
    boolean isSequential = true;
    HashSet<Integer> col = new HashSet<Integer>();
    col.add(nums[0]);
    for (int i = 1; i < nums.length; i++) {
      col.add(nums[i]);
      if (isSequential && nums[i] - 1 == nums[i - 1]) prefix += nums[i];
      else isSequential = false;
    }
    while (prefix <= 50) {
      if (!col.contains(prefix)) break;
      prefix++;
    }
    return prefix;
  }
}
