import java.util.Arrays;

public class RelativeRanks {
  public String[] findRelativeRanks(int[] score) {
    int[][] ranks = new int[score.length][2];
    for (int i = 0; i < score.length; i++) {
      ranks[i][0] = score[i];
      ranks[i][1] = i;
    }
    Arrays.sort(ranks, (a, b) -> b[0] - a[0]);
    String[] ans = new String[score.length];
    for (int i = 0; i < score.length; i++) {
      int idx = ranks[i][1];
      if (i == 0) ans[idx] = "Gold Medal";
      else if (i == 1) ans[idx] = "Silver Medal";
      else if (i == 2) ans[idx] = "Bronze Medal";
      else ans[idx] = String.valueOf(i + 1);
    }
    return ans;
  }
}
