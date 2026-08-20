public class DistributeElementsIntoTwoArraysI {
  public int[] resultArray(int[] nums) {
    int[] ans = new int[nums.length];
    int[] arr1 = new int[nums.length];
    int[] arr2 = new int[nums.length];
    int currIndx = 0;
    arr1[0] = nums[0];
    arr2[0] = nums[1];
    for (int i = 2, j = 0, k = 0; i < nums.length; i++) {
      if (arr1[j] > arr2[k]) arr1[++j] = nums[i];
      else arr2[++k] = nums[i];
    }
    for (int i : arr1) {
      if (i == 0) break;
      else ans[currIndx++] = i;
    }
    for (int i : arr2) {
      if (i == 0) break;
      else ans[currIndx++] = i;
    }
    return ans;
  }
}
