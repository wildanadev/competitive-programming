import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.PriorityQueue;

public class SortVowelsByFrequency {
  public class CI {
    char[] letter;
    int indx;

    public CI(char[] letter, int indx) {
      this.letter = letter;
      this.indx = indx;
    }
  }

  public String sortVowels(String s) {
    HashMap<Character, Integer> freq = new HashMap<>();
    PriorityQueue<CI> pq =
        new PriorityQueue<>(
            (a, b) ->
                (b.letter.length == a.letter.length)
                    ? a.indx - b.indx
                    : b.letter.length - a.letter.length);
    ArrayDeque<Character> ad = new ArrayDeque<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == 'a'
          || s.charAt(i) == 'e'
          || s.charAt(i) == 'i'
          || s.charAt(i) == 'o'
          || s.charAt(i) == 'u') {
        freq.put(s.charAt(i), freq.getOrDefault(s.charAt(i), 0) + 1);
      }
    }
    for (char i : freq.keySet()) {
      char[] temp = new char[freq.get(i)];
      for (int j = 0; j < freq.get(i); j++) {
        temp[j] = i;
      }
      pq.offer(new CI(temp, s.indexOf(i)));
    }
    while (!pq.isEmpty()) {
      CI i = pq.poll();
      for (char csi : i.letter) {
        ad.offer(csi);
      }
    }
    for (char i : s.toCharArray()) {
      if (i == 'a' || i == 'e' || i == 'i' || i == 'o' || i == 'u') {
        sb.append(ad.poll());
      } else {
        sb.append(i);
      }
    }
    return sb.toString();
  }
}
