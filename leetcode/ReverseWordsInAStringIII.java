public class ReverseWordsInAStringIII {
  public String reverseWords(String s) {
    StringBuilder ans = new StringBuilder();
    for (String i : s.split(" ")) ans.append(new StringBuilder(i).reverse().toString()).append(" ");
    return ans.toString().trim();
  }
}
