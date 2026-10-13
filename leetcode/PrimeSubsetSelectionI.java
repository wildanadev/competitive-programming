import java.util.ArrayList;
import java.util.List;

public class PrimeSubsetSelectionI {
  static int[] sieve(int limit) {
    boolean[] prime = new boolean[limit + 1];
    for (int i = 2; i <= limit; i++) {
      prime[i] = true;
    }
    for (int i = 2; i * i <= limit; i++) {
      if (prime[i]) {
        for (int j = i * i; j <= limit; j += i) prime[j] = false;
      }
    }
    int count = 0;
    for (int i = 2; i <= limit; i++) if (prime[i]) count++;
    int[] ans = new int[count];
    int indx = 0;
    for (int i = 2; i <= limit; i++) {
      if (prime[i]) {
        ans[indx++] = i;
      }
    }
    return ans;
  }

  public List<Integer> maxPrimes(int n, int s) {
    ArrayList<Integer> al = new ArrayList<>();
    int sum = 0;
    int[] prime = sieve(n);
    for (int i : prime) {
      sum += i;
      if (sum > s) break;
      al.add(i);
    }
    return al;
  }
}
