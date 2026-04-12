import java.util.Arrays;

public class FindTheDegreeOfEachVertex {
  public int[] findDegrees(int[][] matrix) {
    int[] ans = new int[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      int temp = 0;
      for (int j = 0; j < matrix[i].length; j++) {
        if (i == j) continue;
        if (matrix[i][j] == 1) temp++;
      }
      ans[i] = temp;
    }
    System.out.println(Arrays.toString(ans));
    return ans;
  }
}
