import java.util.HashMap;

public class ConvertANumberToHexadecimal {
  public String toHex(int num) {
    String ans = "";
    String finalAns = "";
    HashMap<Byte, Character> dict = new HashMap<>();
    char t = 'a';
    for (byte i = 0; i < 16; i++) {
      if (i < 10) dict.put(i, (char) (i + '0'));
      else dict.put(i, t++);
    }
    if (num == 0) return "0";
    if (num > 0) {
      while (num > 0) {
        ans += dict.get((byte) (num % 16));
        num /= 16;
      }
    } else {
      int[] bit = new int[32];
      int c = bit.length - 1;
      int tempNum = Math.abs(num);
      while (tempNum > 0) {
        bit[c--] = tempNum % 2;
        tempNum /= 2;
      }
      for (int i = 0; i < bit.length; i++) {
        if (bit[i] == 0) bit[i] = 1;
        else bit[i] = 0;
      }
      int simpan = 1;
      for (int i = bit.length - 1; i >= 0; i--) {
        int res = simpan + bit[i];
        if (res == 2) {
          simpan = 1;
          bit[i] = 0;
        } else if (res == 1) {
          simpan = 0;
          bit[i] = 1;
        } else {
          simpan = 0;
          bit[i] = 0;
        }
      }
      if (simpan == 1) {
        bit[0] = 1;
      }
      int cMp = 0;
      int tempCmp = 0;
      for (int i = bit.length - 1; i >= 0; i--) {
        tempCmp += (bit[i] * 1 << cMp++);
        if (cMp == 4) {
          ans += dict.get((byte) (tempCmp));
          cMp = 0;
          tempCmp = 0;
        }
      }
    }
    for (int i = ans.length() - 1; i >= 0; i--) {
      finalAns += ans.charAt(i);
    }
    return finalAns;
  }

  public static void main(String[] args) {
    System.out.println(new ConvertANumberToHexadecimal().toHex(-2147483648));
  }
}
