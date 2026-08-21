public class RangeAdditionII {
  public int maxCount(int m, int n, int[][] ops) {
    int ai = m;
    int bi = n;
    for (int i[] : ops) {
      ai = Math.min(ai, i[0]);
      bi = Math.min(bi, i[1]);
    }
    return ai * bi;
  }
}
