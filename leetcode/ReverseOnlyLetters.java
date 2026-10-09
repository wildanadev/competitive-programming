public class ReverseOnlyLetters {
  public String reverseOnlyLetters(String s) {
    char[] letters = s.toCharArray();
    int l = 0, r = s.length() - 1;
    while (l < r) {
      while (l < r & !Character.isLetter(letters[l])) l++;
      while (l < r & !Character.isLetter(letters[r])) r--;
      char temp = letters[l];
      letters[l] = letters[r];
      letters[r] = temp;
      l++;
      r--;
    }
    return new String(letters);
  }
}
