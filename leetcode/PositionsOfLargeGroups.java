import java.util.ArrayList;
import java.util.List;

public class PositionsOfLargeGroups {
  public List<List<Integer>> largeGroupPositions(String s) {
    List<List<Integer>> ans = new ArrayList<>();
    int start, end;
    for (start = 0, end = 0; end < s.length(); end++) {
      if (s.charAt(start) != s.charAt(end)) {
        if (end - start >= 3) ans.add(List.of(start, end - 1));
        start = end;
      }
    }
    if (end - start >= 3) ans.add(List.of(start, end - 1));
    return ans;
  }
}
