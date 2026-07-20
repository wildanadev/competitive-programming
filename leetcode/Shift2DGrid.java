import java.util.ArrayList;
import java.util.List;

public class Shift2DGrid {
  public List<List<Integer>> shiftGrid(int[][] grid, int k) {
    List<List<Integer>> ans = new ArrayList<>();
    int row = grid.length;
    int col = grid[0].length;

    // fill with empty array list
    for (int i = 0; i < row; i++) ans.add(new ArrayList());

    // find the size and start begin of grid (0,0) after shift with counter variable to make sure
    // the element is the right position
    int dimension = row * col;
    k %= (row * col);
    int begin = dimension - k;
    int cnt = 0;

    // simulate the processs
    for (int i = begin; i < begin + dimension; i++) {
      int rowTemp =
          (i / col) % row; // calculate the row index cell the idea is the begin value / col % row
      int colTemp = (i % col); // calculate the col index cell the idea is the begin % col
      ans.get(cnt / col).add(grid[rowTemp][colTemp]);
      cnt++;
    }
    return ans;
  }
}
