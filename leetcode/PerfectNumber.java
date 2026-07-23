public class PerfectNumber {
  public boolean checkPerfectNumber(int num) {
    if (num < 2) return false;
    int perfectNum = 0;
    for (int i = 2; i <= Math.sqrt(num); i++) {
      if (num % i == 0) {
        perfectNum += i + num / i;
      }
    }
    return perfectNum + 1 == num;
  }
}
