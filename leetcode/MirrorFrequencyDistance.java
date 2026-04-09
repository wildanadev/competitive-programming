import java.util.HashMap;
import java.util.HashSet;

public class MirrorFrequencyDistance {
  public int mirrorFrequency(String s) {
    int ans = 0;
    HashMap<Character, Character> mMap = new HashMap<>();
    HashMap<Character, Character> mMap1 = new HashMap<>();
    HashMap<Character, Integer> mMapFreq = new HashMap<>();
    HashSet<Character> mMap2 = new HashSet<>();
    int limitAlfa = 1 + ((int) 'z' - (int) 'a');
    char t1 = 'a';
    char t2 = 'z';
    char t3 = '0';
    char t4 = '9';
    for (int i = 0; i < limitAlfa / 2; i++) {
      char temp1 = t1++;
      char temp2 = t2--;
      mMap.put(temp1, temp2);
      mMap1.put(temp2, temp1);
    }
    for (int i = 0; i < 5; i++) {
      char temp1 = t3++;
      char temp2 = t4--;
      mMap.put(temp1, temp2);
      mMap1.put(temp2, temp1);
    }

    for (char charx : s.toCharArray()) {
      mMapFreq.put(charx, mMapFreq.getOrDefault(charx, 0) + 1);
    }

    for (char charx : s.toCharArray()) {
      if (!mMap2.contains(charx)) {
        char temp1 = mMap.getOrDefault(charx, '?');
        char temp2 = mMap1.getOrDefault(charx, '?');
        char tempFinal12 = temp1 != '?' ? temp1 : temp2;
        mMap2.add(charx);
        mMap2.add(tempFinal12);
        ans += Math.abs(mMapFreq.getOrDefault(charx, 0) - mMapFreq.getOrDefault(tempFinal12, 0));
      }
    }
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(new MirrorFrequencyDistance().mirrorFrequency("byby"));
  }
}
