import java.util.Arrays;

public class ContainsDuplicate2 {
  public boolean containsNearbyDuplicate(int[] nums, int k) {
    int[] nums2 = new int[nums.length + k];
    Arrays.fill(nums2, Integer.MIN_VALUE);
    for (int i = 0; i < nums.length; i++) {
      nums2[i] = nums[i];
    }
    for (int i = 0; i + k < nums2.length; i++) {
      int t = nums2[i];
      for (int j = i + 1; j <= i + k; j++) {
        if (t == nums2[j]) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    System.out.println(
        new ContainsDuplicate2().containsNearbyDuplicate(new int[] {1, 2, 3, 1, 2, 3}, 2));
  }
}
