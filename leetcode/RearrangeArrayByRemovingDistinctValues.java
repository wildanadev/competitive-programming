public class RearrangeArrayByRemovingDistinctValues {
  public int[] rearrangeArray(int[] nums) {
    int[] ans = new int[nums.length];
    int[] freq = new int[101];
    int maxFreq = 0;
    int idx = 0;
    for (int i : nums) {
      freq[i]++;
      if (freq[i] > maxFreq) maxFreq = freq[i];
    }
    for (int i = 0; i < maxFreq; i++) {
      for (int j = 1; j <= 100; j++) {
        if (freq[j] > 0) {
          ans[idx++] = j;
          freq[j]--;
        }
      }
    }
    return ans;
  }
}
