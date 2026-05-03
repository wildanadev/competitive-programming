import java.util.Arrays;

public class SumOfPrimesBetweenNumberAndItsReverse {
  boolean[] sieve(int n) {
    boolean[] prime = new boolean[n + 1];
    Arrays.fill(prime, true);
    if (n >= 0) prime[0] = false;
    if (n >= 1) prime[1] = false;
    for (int i = 2; i * i <= n; i++) {
      if (prime[i]) {
        for (int j = i * i; j <= n; j += i) prime[j] = false;
      }
    }
    return prime;
  }

  int reverse(int n) {
    int rev = 0;
    while (n != 0) {
      rev = (rev * 10) + (n % 10);
      n /= 10;
    }
    return rev;
  }

  public int sumOfPrimesInRange(int n) {
    int ans = 0;
    int rev = reverse(n);
    int min = Math.min(n, rev), max = Math.max(n, rev);
    boolean[] isPrime = sieve(max);
    for (int i = min; i <= max; i++) {
      if (isPrime[i]) ans += i;
    }
    return ans;
  }
}
