public class AddStrings {
  public String addStrings(String num1, String num2) {
    StringBuilder sb = new StringBuilder();
    int indx1 = num1.length() - 1, indx2 = num2.length() - 1;
    int carry = 0;
    while (indx1 >= 0 || indx2 >= 0 || carry != 0) {
      int digit1 = indx1 >= 0 ? num1.charAt(indx1) - '0' : 0;
      int digit2 = indx2 >= 0 ? num2.charAt(indx2) - '0' : 0;
      int total = digit1 + digit2 + carry;
      carry = total / 10;
      sb.append(total % 10);
      indx1--;
      indx2--;
    }
    return sb.reverse().toString();
  }
}
