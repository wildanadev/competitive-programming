import java.util.HashSet;

public class JewelsAndStones {
  public int numJewelsInStones(String jewels, String stones) {
    int ans = 0;
    HashSet<Character> jewelSet = new HashSet<Character>();
    for (char i : jewels.toCharArray()) jewelSet.add(i);
    for (char i : stones.toCharArray()) if (jewelSet.contains(i)) ans++;
    return ans;
  }
}
