public class MaximumLengthSubstringWithTwoOccurrences {
  public int maximumLengthSubstring(String s) {
    int i = 0, j = 0;
    int ans = 0;
    int[] occurrences = new int[26];
    while (j < s.length()) {
      occurrences[s.charAt(j) - 'a']++;
      while (occurrences[s.charAt(j) - 'a'] > 2) occurrences[s.charAt(i++) - 'a']--;
      ans = Math.max(ans, j - i + 1);
      j++;
    }
    return ans;
  }
}
