import java.util.ArrayList;
import java.util.Collections;

/** MaxConsecutiveOnes */
public class MaxConsecutiveOnes {
  public static int findMaxConsecutiveOnes(int[] nums) {
    ArrayList<Integer> ans = new ArrayList<Integer>((int) 1e5);
    int count = 0
    for (int i : nums) {
      if (i == 1) {
        count++;
      } else {
        if (count > 0) {
          ans.add(count);
          count = 0;
        }
      }
    }
    if (count > 0) ans.add(count);
    return Collections.max(ans);
  }

  public static void main(String[] args) {
    System.out.println(findMaxConsecutiveOnes(new int[] {1, 1, 0, 1, 1, 1}));
  }
}
