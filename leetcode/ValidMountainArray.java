public class ValidMountainArray {
  public boolean validMountainArray(int[] A) {
    int n = A.length, i = 0, j = n - 1;
    while (i + 1 < n && A[i] < A[i + 1]) i++;
    while (j > 0 && A[j] < A[j - 1]) j--;
    return i != 0 && j != n - 1 && i == j;
  }
}
