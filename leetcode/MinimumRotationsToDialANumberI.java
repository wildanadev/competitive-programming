public class MinimumRotationsToDialANumberI {
  public int minRotations(String s) {
    int ans = 0;
    char pointer = '0';
    for (char i : s.toCharArray()) {
      int diff = Math.abs((pointer - '0') - (i - '0'));
      ans += Math.min(diff, 10 - diff);
      pointer = i;
    }
    return ans;
  }
}
