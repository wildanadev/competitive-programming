import java.util.ArrayList;
import java.util.List;

/** SummaryRanges */
public class SummaryRanges {
  public List<String> summaryRanges(int[] nums) {
    List<String> ans = new ArrayList<String>();
    int start = nums[0];
    int end = nums[0];
    for (int i = 1; i < nums.length; i++) {
      System.out.println(i);
      if (nums[i] - 1 != nums[i - 1]) {
        end = nums[i - 1];
        if (start == end) ans.add("" + start);
        else ans.add(start + "->" + end);
        start = nums[i];
      }
    }
    end = nums[nums.length - 1];
    if (start == end) ans.add("" + start);
    else ans.add(start + "->" + end);
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(new SummaryRanges().summaryRanges(new int[] {0, 1, 2, 4, 5, 7}));
  }
}
