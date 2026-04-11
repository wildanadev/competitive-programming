public class PlusOne {
  public int[] plusOne(int[] digits) {
    boolean f = true;
    for (int i : digits) {
      if (i != 9) {
        f = false;
        break;
      }
    }
    if (f) {
      int[] ans = new int[digits.length + 1];
      ans[0] = 1;
      return ans;
    } else {
      for (int i = digits.length - 1; i >= 0; i--) {
        if (digits[i] + 1 == 10) {
          digits[i] = 0;
        } else {
          digits[i] += 1;
          break;
        }
      }
      return digits;
    }
  }
}
