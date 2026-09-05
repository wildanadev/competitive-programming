import java.util.ArrayList;
import java.util.List;

public class SelfDividingNumbers {
  public List<Integer> selfDividingNumbers(int left, int right) {
    List<Integer> ans = new ArrayList<Integer>();
    for (int i = left; i <= right; i++) {
      if (isSelfDividingNumber(i)) ans.add(i);
    }
    return ans;
  }

  public boolean isSelfDividingNumber(int number) {
    int temp = number;
    while (temp > 0) {
      int curr = temp % 10;
      if (curr == 0) return false;
      if (!(number % curr == 0)) return false;
      temp /= 10;
    }
    return true;
  }
}
