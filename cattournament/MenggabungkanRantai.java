import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MenggabungkanRantai {

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
    String A = fr.nextLine();
    String B = fr.nextLine();
    char[] S = A.toCharArray();
    char[] T = B.toCharArray();
    Arrays.sort(S);
    Arrays.sort(T);
    StringBuilder ans = new StringBuilder();
    StringBuilder ans1 = new StringBuilder();
    for (int i = 0; i < N; i++) {
      ans.append(S[i]);
      ans.append(T[i]);
      ans1.append(T[i]);
      ans1.append(S[i]);
    }
    System.out.println(ans.compareTo(ans1) > 0 ? ans1 : ans);
  }
}
