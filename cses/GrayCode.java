import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GrayCode {

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
    int n = fr.nextInt();
    for (int i = 0; i < (1 << n); i++) {
      String org = Integer.toString(i ^ (i >> 1), 2);
      for (int j = 0; j < n - org.length(); j++) System.out.print('0');
      System.out.println(org);
    }
  }
}
