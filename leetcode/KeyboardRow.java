import java.util.ArrayList;
import java.util.List;

public class KeyboardRow {
  public String[] findWords(String[] words) {
    // using array with 26 size to grouping every single american keyboard
    int[] row = new int[26];
    for (char e : "qwertyuiop".toCharArray()) row[e - 'a'] = 1;
    for (char e : "asdfghjkl".toCharArray()) row[e - 'a'] = 2;
    for (char e : "zxcvbnm".toCharArray()) row[e - 'a'] = 3;

    // Simulate the process
    List<String> ans = new ArrayList<>();
    for (String e : words) {
      boolean isValid = true;
      char[] chars = e.toLowerCase().toCharArray();
      for (int i = 0; i < chars.length; i++) {
        if (row[chars[i] - 'a'] != row[chars[0] - 'a']) {
          isValid = false;
          break;
        }
      }
      if (isValid) ans.add(e);
    }

    return ans.toArray(new String[ans.size()]);
  }
}
