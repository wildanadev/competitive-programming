public class GuessNumberHigherOrLower extends GuessGame {
  public int guessNumber(int n) {
    int l = 1, r = n;
    while (l <= r) {
      int m = l + (r - l) / 2;
      int result = guess(m);
      if (result == 0) return m;
      else if (result == 1) {
        l = m + 1;
      } else r = m - 1;
    }
    return -1;
  }
}

class GuessGame {
  /**
   * Forward declaration of guess API.
   *
   * @param num your guess
   * @return -1 if num is higher than the picked number 1 if num is lower than the picked number
   *     otherwise return 0 int guess(int num);
   */
  public int guess(int num) {
    int pickedNumber = 6;
    if (pickedNumber == num) return 0;
    else if (num > pickedNumber) return -1;
    else return 1;
  }
}
