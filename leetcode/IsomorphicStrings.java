import java.util.HashMap;

public class IsomorphicStrings {
  public boolean isIsomorphic(String s, String t) {
    HashMap<Character, Character> iso = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      if (iso.containsKey(s.charAt(i))) {
        if (iso.get(s.charAt(i)) != t.charAt(i)) return false;
      } else {
        if (iso.containsValue(t.charAt(i))) return false;
        iso.put(s.charAt(i), t.charAt(i));
      }
    }
    return true;
  }

  public static void main(String[] args) {}
}
