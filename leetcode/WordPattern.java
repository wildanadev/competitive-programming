import java.util.HashMap;

public class WordPattern {
  public boolean wordPattern(String pattern, String s) {
    HashMap<Character, String> mapping = new HashMap<>();
    String[] sMap = s.split(" ");
    if (pattern.length() != sMap.length) return false;
    for (int i = 0; i < pattern.length(); i++) {
      char t = pattern.charAt(i);
      String word = sMap[i];
      if (mapping.containsKey(t) || mapping.containsValue(word)) {
        if (!mapping.containsKey(t)) return false;
        if (!mapping.get(t).equals(sMap[i])) return false;
      } else {
        mapping.put(t, word);
      }
    }
    return true;
  }
}
