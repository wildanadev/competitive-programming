public class AddBinary {
  public String addBinary(String a, String b) {
    String temp = "";
    String ans = "";
    int rest = 0;
    int indxa = a.length() - 1;
    int indxb = b.length() - 1;
    while (rest > 0 || indxa >= 0 || indxb >= 0) {
      int ta = (indxa >= 0) ? a.charAt(indxa) - '0' : 0;
      int tb = (indxb >= 0) ? b.charAt(indxb) - '0' : 0;
      int s = ta + tb + rest;
      switch (s) {
        case 1:
          temp += '1';
          rest = 0;
          break;

        case 2:
          temp += '0';
          rest = 1;
          break;

        case 3:
          temp += '1';
          rest = 1;
          break;

        default:
          temp += '0';
          rest = 0;
          break;
      }
      indxa--;
      indxb--;
    }
    for (int i = temp.length() - 1; i >= 0; i--) {
      ans += temp.charAt(i);
    }
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(new AddBinary().addBinary("11", "1"));
  }
}
