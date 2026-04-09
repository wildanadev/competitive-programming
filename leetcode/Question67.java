public class Question67 {
  public String addBinary(String a, String b) {
    String ans = "";
    String finalAns = "";
    int c = 0;
    int sisa = 0;
    while (b.length() > c && a.length() > c) {
      int t1 = b.charAt(b.length() - 1 - c) - '0';
      int t2 = a.charAt(a.length() - 1 - c) - '0';
      int sum = t1 + t2 + sisa;
      sisa = 0;
      switch (sum) {
        case 1:
          ans += "1";
          break;

        case 2:
          ans += "0";
          sisa = 1;
          break;

        case 3:
          ans += "0";
          sisa = 1;
          break;

        default:
          ans += "0";
          break;
      }
      c++;
    }
    if (sisa == 1) {
      if (a.length() > c) {
        while (a.length() > c) {
          int t2 = a.charAt(a.length() - 1 - c) - '0';
          int sum = t2 + sisa;
          sisa = 0;
          switch (sum) {
            case 1:
              ans += "1";
              break;

            case 2:
              ans += "0";
              sisa = 1;
              break;

            case 3:
              ans += "0";
              sisa = 1;
              break;

            default:
              ans += "0";
              break;
          }
          c++;
        }
      } else if (b.length() > c) {
        while (b.length() > c) {
          int t2 = b.charAt(b.length() - 1 - c) - '0';
          int sum = t2 + sisa;
          sisa = 0;
          switch (sum) {
            case 1:
              ans += "1";
              break;

            case 2:
              ans += "0";
              sisa = 1;
              break;

            case 3:
              ans += "0";
              sisa = 1;
              break;

            default:
              ans += "0";
              break;
          }
          c++;
        }
      } else {
        ans += "1";
      }
    }
    if (sisa == 1) ans += "1";
    for (int i = ans.length() - 1; i >= 0; i--) {
      finalAns += ans.charAt(i);
    }
    return finalAns;
  }

  public static void main(String[] args) {
    System.out.println(new Question67().addBinary("1", "111"));
  }
}
