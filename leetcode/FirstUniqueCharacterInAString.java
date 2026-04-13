public class FirstUniqueCharacterInAString {
  public int firstUniqChar(String s) {
    int[] un = new int[26];
    for (char i : s.toCharArray()) {
      un[i - 'a']++;
    }
    for (int i = 0; i < s.length(); i++) {
      if (un[s.charAt(i) - 'a'] == 1) return i;
    }
    return -1;
  }
}
