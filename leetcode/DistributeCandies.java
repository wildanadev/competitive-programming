import java.util.HashSet;

public class DistributeCandies {
  public int distributeCandies(int[] candyType) {
    HashSet<Integer> candy = new HashSet<Integer>();
    for (int i : candyType) {
      candy.add(i);
      if (candyType.length / 2 <= candy.size()) return candyType.length / 2;
    }
    return candy.size();
  }
}
