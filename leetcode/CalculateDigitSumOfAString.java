/**
 * CalculateDigitSumOfAString
 */
public class CalculateDigitSumOfAString {
    public String digitSum(String s, int k) {
        StringBuilder ans = new StringBuilder(s);
        while (ans.length() > k) {
            StringBuilder ansTemp = new StringBuilder();
            int count = 0, sum = 0;
            for (int i = 0; i < ans.length(); i++) {
                if(count == k) {
                    ansTemp.append(sum);
                    sum = 0;
                    count = 0;
                }
                sum += ans.charAt(i) - '0';
                count++;
            }
            ansTemp.append(sum);
            ans.setLength(0);
            ans.append(ansTemp.toString());
        }
        return ans.toString();
    }
}