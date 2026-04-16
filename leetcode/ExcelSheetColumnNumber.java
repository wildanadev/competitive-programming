public class ExcelSheetColumnNumber {
  public int titleToNumber(String columnTitle) {
    int ans = 0;
    int c = 0;
    for (int i = columnTitle.length() - 1; i >= 0; i--) {
      ans += (columnTitle.charAt(i) - 'A' + 1) * (int) Math.pow(26, c++);
    }
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(new ExcelSheetColumnNumber().titleToNumber("AA"));
  }
}
