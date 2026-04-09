public class Question28 {
  public int strStr(String haystack, String needle) {
    return haystack.indexOf(needle);
  }

  public static void main(String[] args) {
    System.out.println(new Question28().strStr("leetcode", "leeto"));
  }
}
