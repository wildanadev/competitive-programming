public class RemoveDuplicatesFromSortedArray {
  public int removeDuplicates(int[] nums) {
    int min = -101;
    int count = 0;
    for (int i : nums) {
      if (i != min) {
        nums[count++] = i;
        min = i;
      }
    }
    return count;
  }
}
