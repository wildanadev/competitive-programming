public class SplitAStringInBalancedStrings {
	public int balancedStringSplit(String s) {
		int ans = 0;
		int freq = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == 'R') freq++;
			else freq--;
			if(freq == 0) ans++;
		}
		return ans;
	}
}