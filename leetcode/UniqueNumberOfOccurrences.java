import java.util.HashMap;
import java.util.HashSet;

public class UniqueNumberOfOccurrences {
  public boolean uniqueOccurrences(int[] arr) {
    HashMap<Integer, Integer> map = new HashMap<>();
    HashSet<Integer> uniqueOccurrences = new HashSet<>();
    for (int i : arr) map.put(i, map.getOrDefault(i, 0) + 1);
    for (int i : map.values()) {
      if (uniqueOccurrences.contains(i)) return false;
      uniqueOccurrences.add(i);
    }
    return true;
  }
}
