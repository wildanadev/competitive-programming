import java.util.Arrays;

public class RansomNote {
  public boolean canConstruct(String ransomNote, String magazine) {
    char[] x = ransomNote.toCharArray();
    char[] y = magazine.toCharArray();
    if (x.length > y.length) return false;
    int c = 0;
    Arrays.sort(x);
    Arrays.sort(y);
    for (char i : y) {
      if (c == x.length) break;
      if (i == x[c]) c++;
    }
    return c == x.length;
  }
}
