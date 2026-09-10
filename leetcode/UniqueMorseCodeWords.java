import java.util.HashSet;

public class UniqueMorseCodeWords {
  private static final String[] dict = {
    ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--",
    "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."
  };

  public int uniqueMorseRepresentations(String[] words) {
    HashSet<String> uniqueMorse = new HashSet<String>();
    for (String i : words) {
      uniqueMorse.add(convertToMorse(i));
    }
    return uniqueMorse.size();
  }

  private String convertToMorse(String i) {
    StringBuilder sb = new StringBuilder();
    for (char j : i.toCharArray()) sb.append(dict[j - 'a']);
    return sb.toString();
  }
}
