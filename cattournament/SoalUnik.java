import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SoalUnik {

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

  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int N = fr.nextInt();
    long ans = 0;
    long[] br = new long[N];
    for (int i = 0; i < N; i++) {
      br[i] = fr.nextInt();
      ans += br[i];
    }
    Arrays.sort(br);
    if (ans != 0) {
      if (br[0] != 0) {
        System.out.println(0);
      } else {
        System.out.println(Integer.MAX_VALUE);
      }
    } else {
      System.out.println(Integer.MAX_VALUE);
    }
  }
}
