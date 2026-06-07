import java.util.ArrayList;
import java.util.List;

public class ValidBinaryStringWithCostLimit {
  public List<String> generateValidStrings(int n, int k) {
    List<String> res = new ArrayList<>();
    solve(0, n, k, 0, "", res);
    return res;
  }

  public void solve(int i, int n, int k, int cost, String s, List<String> ans) {
    if (cost > k) return;
    if (i == n) {
      ans.add(s);
      return;
    }
    solve(i + 1, n, k, cost, s + "0", ans);
    if (i == 0 || s.charAt(s.length() - 1) != '1') solve(i + 1, n, k, cost + i, s + "1", ans);
  }
}
