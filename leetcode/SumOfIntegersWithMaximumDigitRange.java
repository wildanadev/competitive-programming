import java.util.ArrayList;

public class SumOfIntegersWithMaximumDigitRange {
  public int maxDigitRange(int[] nums) {
    int ans = 0;
    int maxDigitRange = 0;
    ArrayList<int[]> al = new ArrayList<>();
    for (int i : nums) {
      int t = i;
      int s = Integer.MAX_VALUE;
      int l = Integer.MIN_VALUE;
      while (t != 0) {
        int digit = t % 10;
        s = Math.min(s, digit);
        l = Math.max(l, digit);
        t /= 10;
      }
      int diff = l - s;
      maxDigitRange = Math.max(maxDigitRange, diff);
      al.add(new int[] {diff, i});
    }
    for (int i[] : al) if (i[0] == maxDigitRange) ans += i[1];
    return ans;
  }
}
