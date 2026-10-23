import java.util.HashSet;

public class FindTheTownJudge {
  public int findJudge(int n, int[][] trust) {
    if (n == 1) return n;
    HashSet<Integer> everybody = new HashSet<>();
    HashSet<Integer> townJudgeList = new HashSet<>();
    HashSet<Integer> count = new HashSet<>();
    int isTownJudge = -1;
    for (int[] i : trust) {
      everybody.add(i[0]);
      townJudgeList.add(i[1]);
    }
    for (int i : townJudgeList) {
      if (!everybody.contains(i)) {
        isTownJudge = i;
      }
    }
    for (int[] i : trust) {
      if (i[1] == isTownJudge) count.add(i[0]);
    }
    return count.size() == n - 1 ? isTownJudge : -1;
  }
}
