public class CountLargestGroup {
  public int countLargestGroup(int n) {
    int max = 0;
    int ans = 0;
    int[] digits = new int[37];
    for (int i = 1; i <= n; i++) {
      int sumD = sumOfDigits(i);
      digits[sumD]++;
      max = Math.max(max, digits[sumD]);
    }
    for (int i : digits) if (i == max) ans++;
    return ans;
  }

  private int sumOfDigits(int number) {
    int sum = 0;
    while (number > 0) {
      sum += (number % 10);
      number /= 10;
    }
    return sum;
  }
}
