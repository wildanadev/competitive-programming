public class FindTheKBeautyOfANumber {
  public int divisorSubstrings(int num, int k) {
    int ans = 0;
    char[] subs = String.valueOf(num).toCharArray();
    for (int i = 0; i < subs.length - k + 1; i++) {
      StringBuilder res = new StringBuilder();
      res.append(subs[i]);
      for (int j = i + 1; j < i + k; j++) {
        res.append(subs[j]);
      }
      int track = Integer.parseInt(res.toString());
      if (track != 0 && num % track == 0) ans++;
    }
    return ans;
  }
}
