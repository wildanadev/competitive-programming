import java.util.HashSet;

/** ContainsDuplicate */
public class ContainsDuplicate {
  public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> t = new HashSet<>();
    for (int i : nums) {
      if (t.contains(i)) return true;
      t.add(i);
    }
    return false;
  }

  public static void main(String[] args) {
    System.out.println(new ContainsDuplicate().containsDuplicate(new int[] {1, 2, 3, 4}));
  }
}
