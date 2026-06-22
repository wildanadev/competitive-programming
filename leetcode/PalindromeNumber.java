public class PalindromeNumber {
  public boolean isPalindrome(int x) {
    if (x < 0) return false;
    int rev = 0, xcopy = x;
    while (x > 0) {
      rev = (rev * 10) + (x % 10);
      x /= 10;
    }
    return rev == xcopy;
  }
}
