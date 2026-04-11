import java.util.HashSet;

class ContainsDuplicate {
  public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> t = new HashSet<>();
    for (int i : nums) {
      if (t.contains(i)) return true;
      t.add(i);
    }
    return false;
  }
}
