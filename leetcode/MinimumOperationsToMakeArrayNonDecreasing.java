public class MinimumOperationsToMakeArrayNonDecreasing {
  public long minOperations(int[] nums) {
    long ans = 0;
    long[] numsLong = new long[nums.length];
    for (int i = 0; i < nums.length; i++) numsLong[i] = nums[i];
    for (int i = 1; i < numsLong.length; i++) {
      if (numsLong[i] < numsLong[i - 1]) {
        ans += numsLong[i - 1] - numsLong[i];
        for (int j = i + 1; j < numsLong.length; j++) {
          numsLong[j] += numsLong[i - 1] - numsLong[i];
        }
        numsLong[i] = numsLong[i - 1];
      }
    }
    return ans;
  }
}
