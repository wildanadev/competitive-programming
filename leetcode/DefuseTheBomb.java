public class DefuseTheBomb {
  public int[] decrypt(int[] code, int k) {
    int[] ans = new int[code.length];
    if (k == 0) return ans;
    int l = k > 0 ? 1 : code.length + k, r = k > 0 ? k : code.length - 1;

    int wSum = 0;
    for (int i = l; i <= r; i++) {
      wSum += code[i % code.length];
    }

    for (int i = 0; i < code.length; i++) {
      ans[i] = wSum;
      wSum += code[(r + 1) % code.length] - code[l % code.length];
      l++;
      r++;
    }

    return ans;
  }
}
