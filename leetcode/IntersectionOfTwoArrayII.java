import java.util.ArrayList;
import java.util.HashMap;

public class IntersectionOfTwoArrayII {
  public int[] intersect(int[] nums1, int[] nums2) {
    HashMap<Integer, Integer> dict1 = new HashMap<>();
    ArrayList<Integer> result = new ArrayList<>();
    for (int i : nums1) {
      dict1.put(i, dict1.getOrDefault(i, 0) + 1);
    }
    for (int i : nums2) {
      if (dict1.containsKey(i) && dict1.get(i) > 0) {
        result.add(i);
        dict1.put(i, dict1.get(i) - 1);
      }
    }
    int[] ans = new int[result.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = result.get(i);
    }
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(
        new IntersectionOfTwoArrayII().intersect(new int[] {4, 9, 5}, new int[] {9, 4, 9, 8, 4}));
  }
}
