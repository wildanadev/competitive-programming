public class MinimumDeletionsToMakeArraySharp {
  public int minDeletions(int[] nums) {
    int ans = 0;
    for (int i = 0; i < nums.length - 1; i++) {
      if ((nums[i] ^ nums[i + 1]) == 0) ans++;
    }
    return ans;
  }
}
