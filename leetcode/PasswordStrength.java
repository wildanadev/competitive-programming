import java.util.HashSet;

public class PasswordStrength {
  public int passwordStrength(String password) {
    int ans = 0;
    HashSet<Character> set = new HashSet<>();
    for (char ch : password.toCharArray()) set.add(ch);
    for (char ch : set) {
      ans +=
          Character.isDigit(ch)
              ? 3
              : "!@#$".indexOf(ch) >= 0 ? 5 : Character.isUpperCase(ch) ? 2 : 1;
    }
    return ans;
  }
}
