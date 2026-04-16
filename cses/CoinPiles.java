import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CoinPiles {

  static class FastReader {
    BufferedReader b;
    StringTokenizer s;

    public FastReader() {
      b = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (s == null || !s.hasMoreElements()) {
        try {
          s = new StringTokenizer(b.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return s.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }

    long nextLong() {
      return Long.parseLong(next());
    }

    double nextDouble() {
      return Double.parseDouble(next());
    }

    String nextLine() {
      String str = "";
      try {
        str = b.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return str;
    }
  }

  static FastReader fr = new FastReader();

  static void solve() {
    long a = fr.nextLong();
    long b = fr.nextLong();
    if ((a + b) % 3 == 0 && Math.max(a, b) <= 2 * Math.min(a, b)) {
      System.out.println("YES");
    } else {
      System.out.println("NO");
    }
  }

  public static void main(String[] args) {
    int t = fr.nextInt();
    while (t-- > 0) {
      solve();
    }
  }
}
