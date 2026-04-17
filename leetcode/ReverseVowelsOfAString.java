public class ReverseVowelsOfAString {
  public String reverseVowels(String s) {
    char[] words = s.toCharArray();
    int start = 0, end = s.length() - 1;
    String vowels = "aeiouAEIOU";
    while (start < end) {
      while (start < end && vowels.indexOf(words[start]) == -1) start++;
      while (start < end && vowels.indexOf(words[end]) == -1) end--;
      char temp = words[start];
      words[start] = words[end];
      words[end] = temp;
      start++;
      end--;
    }
    String ans = new String(words);
    return ans;
  }
}
