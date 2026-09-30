import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UncommonWordsFromTwoSentences {
  public String[] uncommonFromSentences(String s1, String s2) {
    List<String> ans = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();

    for (String i : s1.split(" ")) map.put(i, map.getOrDefault(i, 0) + 1);

    for (String i : s2.split(" ")) map.put(i, map.getOrDefault(i, 0) + 1);

    for (String value : map.keySet()) if (map.get(value) == 1) ans.add(value);

    return ans.toArray(new String[0]);
  }
}
