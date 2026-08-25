import java.util.HashSet;

public class SmallestMissingMultipleOfK {
  public int missingMultiple(int[] nums, int k) {
    HashSet<Integer> lookup = new HashSet<Integer>();
    int smallest = 0;
    for (int i : nums) lookup.add(i);
    for (int i = k; ; i += k)
      if (i % k == 0 && !lookup.contains(i)) {
        smallest = i;
        break;
      }
    return smallest;
  }
}
