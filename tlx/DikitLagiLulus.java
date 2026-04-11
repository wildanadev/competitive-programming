import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DikitLagiLulus {

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
        if (s.hasMoreTokens()) {
          str = s.nextToken("\n");
        } else {
          str = b.readLine();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return str;
    }
  }

  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int N = fr.nextInt();
    int X = fr.nextInt();
    int[] arr = new int[N + 1];
    int ans = 1;
    int temp = arr[0];
    for (int i = 1; i <= N; i++) {
      arr[i] = fr.nextInt();
    }
    for (int i = 1; i <= N; i++) {
      if (i == X) continue;
      if (temp > arr[i]) {
        ans = 0;
        break;
      }
      temp = arr[i];
    }
    System.out.println(ans);
  }
}
