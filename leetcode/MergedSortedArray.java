import java.util.Arrays;

public class MergedSortedArray {
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    if (n == 0) return;
    int count = 0;
    for (int i = m; i < nums1.length; i++) {
      nums1[i] = nums2[count++];
    }
    Arrays.sort(nums1);
  }
}
