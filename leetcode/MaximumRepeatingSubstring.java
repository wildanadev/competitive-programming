public class MaximumRepeatingSubstring {
  public int maxRepeating(String sequence, String word) {
    int ans = 0;
    StringBuilder sb = new StringBuilder();
    while (sequence.contains(sb.append(word).toString())) ans++;
    return ans;
  }
}
