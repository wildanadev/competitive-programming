/**
 * ReformatPhoneNumber
 */
public class ReformatPhoneNumber {
   public String reformatNumber(String number) {
        StringBuilder ans = new StringBuilder();
        for (char i : number.toCharArray())
            if (i != ' ' && i != '-')
                ans.append(i);
        if (ans.length() < 3)
            return ans.toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ans.length();) {
            if (ans.length() - i > 4) {
                result.append(ans.substring(i, i + 3));
                i += 3;
                result.append('-');
            } else {
                if (ans.length() - i == 3) {
                    result.append(ans.substring(i, i + 3));
                    i += 3;
                } else {
                    result.append(ans.substring(i, i + 2));
                    i += 2;
                    if (i != ans.length())
                        result.append('-');
                }
            }
        }
        return result.toString();
    }
}