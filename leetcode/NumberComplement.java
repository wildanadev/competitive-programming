public class NumberComplement {
  public int findComplement(int num) {
    String bit = Integer.toBinaryString(num);
    int counter = 0;
    int ans = 0;
    for (int i = bit.length() - 1; i >= 0; i--) {
      if (bit.charAt(i) == '0') ans += 1 << counter;
      counter++;
    }
    return ans;
  }
}
