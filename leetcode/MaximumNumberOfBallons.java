public class MaximumNumberOfBallons {
	public int maxNumberOfBalloons(String text) {
		int ans = 0;
		char[] freq = new char[26];
		for (char i : text.toCharArray())
			freq[i - 'a']++;
		ans = freq['b' - 'a'];
		ans = Math.min(ans, freq['a' - 'a']);
		ans = Math.min(ans, freq['l' - 'a'] / 2);
		ans = Math.min(ans, freq['o' - 'a'] / 2);
		ans = Math.min(ans, freq['n' - 'a']);
		return ans;
	}
}
