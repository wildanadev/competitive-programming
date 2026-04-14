import java.util.HashSet;

public class IntersectionOfTwoArray {
  public int[] intersection(int[] nums1, int[] nums2) {
    HashSet<Integer> ans = new HashSet<>();
    HashSet<Integer> ans1 = new HashSet<>();
    for (int i : nums1) ans.add(i);
    for (int i : nums2) {
      if (ans.contains(i)) {
        ans1.add(i);
      }
    }
    int[] result = new int[ans1.size()];
    int count = 0;
    for (int i : ans1) {
      result[count++] = i;
    }
    return result;
  }
}
