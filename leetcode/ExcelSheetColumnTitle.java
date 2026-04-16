public class ExcelSheetColumnTitle {
  public String convertToTitle(int columnNumber) {
    String temp = "";
    String ans = "";
    while (columnNumber > 0) {
      columnNumber--;
      temp += (char) (columnNumber % 26 + 'A');
      columnNumber /= 26;
      System.out.println(columnNumber);
    }
    for (int i = temp.length() - 1; i >= 0; i--) {
      ans += temp.charAt(i);
    }
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(new ExcelSheetColumnTitle().convertToTitle(701));
  }
}
