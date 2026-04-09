import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class MajorityElement {
  public int majorityElement1(int[] nums) {
    HashMap<Integer, Integer> hash = new HashMap<>();
    int res = 0;
    int majority = 0;
    for (int n : nums) {
      hash.put(n, 1 + hash.getOrDefault(n, 0));
      if (hash.get(n) > majority) {
        res = n;
        majority = hash.get(n);
      }
    }
    return res;
  }

  public int majorityElement(int[] nums) {
    TreeMap<Integer, Integer> majoritys = new TreeMap<>((a, b) -> b - a);
    HashSet<Integer> uniqueMajoritys = new HashSet<>();
    int key = 0;
    for (int i : nums) {
      uniqueMajoritys.add(i);
    }
    for (int i : uniqueMajoritys) {
      int count = 0;
      for (int j : nums) {
        if (i == j) count++;
      }
      majoritys.put(count, i);
    }
    for (int i : majoritys.keySet()) {
      key = i;
      break;
    }
    return majoritys.get(key);
  }

  public static void main(String[] args) {
    System.out.println(new MajorityElement().majorityElement1(new int[] {2, 2, 1, 1, 1, 2, 2}));
  }
}
