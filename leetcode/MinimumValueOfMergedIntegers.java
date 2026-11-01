public class MinimumValueOfMergedIntegers {
	public int minMergedInteger(int[] nums) {
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length; i += 2)
			ans = Math.min(ans, Integer.parseInt(String.valueOf(nums[i]) + String.valueOf(nums[i + 1])));
		return ans;
	}
}
