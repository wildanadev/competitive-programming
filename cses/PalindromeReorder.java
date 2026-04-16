import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class PalindromeReorder {

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
    String n = fr.nextLine();
    HashMap<Character, Integer> pr = new HashMap<>();
    char[] palin = n.toCharArray();
    Arrays.sort(palin);
    StringBuilder ans = new StringBuilder();
    StringBuilder f = new StringBuilder();
    StringBuilder m = new StringBuilder();
    int countOdd = 0;
    for (char c : palin) {
      pr.put(c, pr.getOrDefault(c, 0) + 1);
    }
    for (char i : pr.keySet()) {
      if ((pr.get(i) & 1) == 1) countOdd++;
    }
    if (countOdd > 1) {
      System.out.println("NO SOLUTION");
    } else {
      for (char key : pr.keySet()) {
        int count = pr.get(key);
        if ((count & 1) == 1) {
          for (int j = 0; j < count; j++) {
            m.append(key);
          }
        } else {
          for (int j = 0; j < count / 2; j++) {
            f.append(key);
          }
        }
      }
      ans.append(f);
      ans.append(m);
      ans.append(new StringBuilder(f).reverse());
      System.out.println(ans);
    }
  }
}
