import java.util.HashSet;

public class FairCandySwap {
  public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
    int sumA = 0, sumB = 0;
    HashSet<Integer> setB = new HashSet<>();
    for (int a : aliceSizes) sumA += a;
    for (int b : bobSizes) {
      sumB += b;
      setB.add(b);
    }
    int diff = (sumB - sumA) / 2;
    for (int a : aliceSizes) {
      if (setB.contains(a + diff)) {
        return new int[] {a, a + diff};
      }
    }
    return new int[] {};
  }
}
