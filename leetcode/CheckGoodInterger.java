public class CheckGoodInterger {
  public boolean checkGoodInteger(int n) {
    long digitSum = 0, squareSum = 0;
    while (n != 0) {
      System.out.println(n % 10);
      digitSum += (n % 10);
      squareSum += Math.pow(n % 10, 2);
      n /= 10;
    }
    return squareSum - digitSum >= 50;
  }

  public static void main(String[] args) {
    System.out.println(new CheckGoodInterger().checkGoodInteger(19));
  }
}
