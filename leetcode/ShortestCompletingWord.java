import java.util.HashMap;

public class ShortestCompletingWord {
  public String shortestCompletingWord(String licensePlate, String[] words) {
    HashMap<Character, Integer> completingWord = new HashMap<Character, Integer>();
    for (char i : licensePlate.toLowerCase().toCharArray())
      if (Character.isLetter(i)) completingWord.put(i, completingWord.getOrDefault(i, 0) + 1);
    String ans = "";
    for (String word : words)
      if (isCompletingWord(word, completingWord) && (ans.isEmpty() || word.length() < ans.length()))
        ans = word;
    return ans;
  }

  private boolean isCompletingWord(String value, HashMap<Character, Integer> completingWord) {
    HashMap<Character, Integer> completingWordTemp = new HashMap<Character, Integer>();
    for (char i : value.toCharArray())
      if (completingWord.containsKey(i))
        completingWordTemp.put(i, completingWordTemp.getOrDefault(i, 0) + 1);
    if (completingWord.size() != completingWordTemp.size()) return false;
    for (Character key : completingWord.keySet())
      if (!(completingWordTemp.containsKey(key)
          && completingWordTemp.get(key) >= completingWord.get(key))) return false;
    return true;
  }
}
