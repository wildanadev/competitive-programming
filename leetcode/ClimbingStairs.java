public class ClimbingStairs {
  public int fibo(int n) {
    if (n <= 1) return n;
    int curr = 0;
    int prev1 = 1, prev2 = 0;
    for (int i = 2; i <= n; i++) {
      curr = prev2 + prev1;
      prev2 = prev1;
      prev1 = curr;
    }
    return curr;
  }

  public int climbStairs(int n) {
    return fibo(n + 1);
  }
}
