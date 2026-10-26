public class RelativeSortArray {
  public int[] relativeSortArray(int[] arr1, int[] arr2) {
    int[] ans = new int[arr1.length];
    int[] freq = new int[1001];
    int j = 0;
    for (int i : arr1) {
      freq[i]++;
    }
    for (int i : arr2) {
      while (freq[i] > 0) {
        ans[j++] = i;
        freq[i]--;
      }
    }
    for (int i = 0; i <= 1000; i++) {
      while (freq[i] > 0) {
        ans[j++] = i;
        freq[i]--;
      }
    }
    return ans;
  }
}
