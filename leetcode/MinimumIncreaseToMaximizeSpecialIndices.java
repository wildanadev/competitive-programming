import java.util.TreeMap;

public class MinimumIncreaseToMaximizeSpecialIndices {
  public long minIncrease(int[] nums) {
    long ans = 0;
    if (nums.length <= 2) {
      return ans;
    }
    TreeMap<int[], Integer> mmap =
        new TreeMap<>(
            (a, b) -> {
              if (a[0] != b[0]) return b[0] - a[0];
              else return a[1] - b[1];
            });
    for (int i = 1; i < nums.length - 1; i++) {
      int opr = 0;
      int t = nums[i];
      while (!(nums[i] > nums[i - 1] && nums[i] > nums[i + 1])) {
        nums[i] = nums[i] + 1;
        opr++;
      }
      mmap.put(new int[] {nums[i], opr}, opr);
      nums[i] = t;
    }
    for (int[] i : mmap.keySet()) {
      ans = mmap.get(i);
      break;
    }
    System.out.println(mmap);
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(
        new MinimumIncreaseToMaximizeSpecialIndices().minIncrease(new int[] {2, 1, 1, 3}));
    System.out.println("Hello World!");
  }
}
