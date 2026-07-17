import java.util.ArrayDeque;
import java.util.HashMap;

public class NextGreaterElementI {
  public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
    for (int i : nums2) {
      while (!stack.isEmpty() && stack.peek() < i) map.put(stack.pop(), i);
      stack.push(i);
    }
    for (int i = 0; i < nums1.length; i++) nums1[i] = map.getOrDefault(nums1[i], -1);
    return nums1;
  }
}
