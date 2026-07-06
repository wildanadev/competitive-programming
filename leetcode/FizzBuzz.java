import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
  public List<String> fizzBuzz(int n) {
    ArrayList<String> al = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
      if (i % 3 == 0 && i % 5 == 0) al.add("FizzBuzz");
      else if (i % 3 == 0) al.add("Fizz");
      else if (i % 5 == 0) al.add("Buzz");
      else al.add(String.valueOf(i));
    }
    return al;
  }
}
