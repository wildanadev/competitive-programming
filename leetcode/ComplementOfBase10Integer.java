public class ComplementOfBase10Integer {
  public int bitwiseComplement(int n) {
    String binary = Integer.toBinaryString(n);
    int i = binary.length() - 1;
    int ans = 0;
    for (char c : binary.toCharArray()) {
      ans += (c == '0') ? 1 << i : 0;
      i--;
    }
    return ans;
  }
}
