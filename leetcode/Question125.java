public class Question125 {
  public String cleaningPalindrome(String s) {
    String ans = "";
    for (char i : s.toLowerCase().toCharArray()) {
      int ascii = i;
      if ((ascii >= 48 && ascii <= 57) || (ascii >= 97 && ascii <= 122)) {
        ans += (char) ascii;
      }
    }
    return ans;
  }

  public boolean isPalindrome(String s) {
    String ans = cleaningPalindrome(s);
    String rev = "";
    for (int i = ans.length() - 1; i >= 0; i--) {
      rev += ans.charAt(i);
    }
    return ans.equals(rev);
  }

  public static void main(String[] args) {
    System.out.println(new Question125().isPalindrome("raceacar"));
  }
}
