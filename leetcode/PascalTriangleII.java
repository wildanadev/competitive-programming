import java.util.ArrayList;
import java.util.List;

public class PascalTriangleII {
  public static long rC(int n, int r) {
    return r == 0 ? 1 : rC(n, r - 1) * (n - r + 1) / r;
  }

  public List<Integer> getRow(int rowIndex) {
    List<Integer> ans = new ArrayList<Integer>();
    for (int i = 0; i <= rowIndex; i++) {
      ans.add((int) rC(rowIndex, i));
    }
    return ans;
  }
}
