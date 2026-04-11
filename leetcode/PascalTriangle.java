import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
  static long rC(int n, int r) {
    return r == 0 ? 1 : rC(n, r - 1) * (n - r + 1) / r;
  }

  public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> ans = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j <= i; j++) {
        row.add((int) rC(i, j));
      }
      ans.add(row);
    }
    return ans;
  }
}
