public class RomanToInteger {
  public int romanDict(char i) {
    int ans = 0;
    switch (i) {
      case 'I':
        ans = 1;
        break;
      case 'V':
        ans = 5;
        break;
      case 'X':
        ans = 10;
        break;
      case 'L':
        ans = 50;
        break;
      case 'C':
        ans = 100;
        break;
      case 'D':
        ans = 500;
        break;
      case 'M':
        ans = 1000;
        break;
      default:
        ans = 0;
        break;
    }
    return ans;
  }

  public int romanToInt(String s) {
    int ans = 0;
    char before = '-';
    for (int i = 0; i < s.length(); i++) {
      char t = s.charAt(i);
      if (i == 0) {
        before = t;
        ans += romanDict(t);
      } else {
        if (t == 'V' || t == 'X') {
          if (before == 'I') ans -= 2;
        } else if (t == 'L' || t == 'C') {
          if (before == 'X') ans -= 20;
        } else if (t == 'D' || t == 'M') {
          if (before == 'C') ans -= 200;
        }
        ans += romanDict(t);
        before = t;
      }
    }
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(new RomanToInteger().romanToInt("MCMXCIV"));
  }
}
