public class ReshapeTheMatrix {
  public int[][] matrixReshape(int[][] mat, int r, int c) {
    int m = mat.length;
    int n = mat[0].length;
    int total = m * n;
    if ((r * c) != total) return mat;
    int[][] ans = new int[r][c];
    for (int i = 0; i < total; i++) ans[i / c][i % c] = mat[i / n][i % n];
    return ans;
  }
}
