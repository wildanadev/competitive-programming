import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MostCommonWord {
  public String mostCommonWord(String paragraph, String[] banned) {
    String[] result = paragraph.toLowerCase().split("[\\p{Punct}\\s]+");
    HashMap<String, Integer> commonWord = new HashMap<String, Integer>();
    HashSet<String> bannedWord = new HashSet<String>();
    String ans = "";
    int freq = 0;
    for (String i : banned) bannedWord.add(i);
    for (String i : result)
      if (!(bannedWord.contains(i))) commonWord.put(i, commonWord.getOrDefault(i, 0) + 1);
    for (Map.Entry<String, Integer> entry : commonWord.entrySet()) {
      if (ans.isEmpty()) {
        ans = entry.getKey();
        freq = entry.getValue();
      }
      if (entry.getValue() > freq) {
        freq = entry.getValue();
        ans = entry.getKey();
      }
    }
    return ans;
  }
}
