public class FindTheHighestAltitude {
  public int largestAltitude(int[] gain) {
    int alt = 0;
    int ans = 0;
    for (int i : gain) {
      alt += i;
      ans = Math.max(alt, ans);
    }
    return ans;
  }
}
