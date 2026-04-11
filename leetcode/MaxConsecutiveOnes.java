public class MaxConsecutiveOnes {
  public int findMaxConsecutiveOnes(int[] nums) {
    int max = 0, count = 0;
    for (int i : nums) {
      count = i == 1 ? count + 1 : 0;
      if (count > max) max = count;
    }
    return max;
  }
}
