import java.util.Arrays;

public class MinimumTotalPriceAfterApplyingDiscounts {
  public double minPrice(int[] prices, int[] discounts) {
    double ans = 0;
    int i = prices.length - 1;
    int j = discounts.length - 1;
    Arrays.sort(prices);
    Arrays.sort(discounts);
    while (i >= 0) {
      ans += ((double) prices[i--] * (100 - (j >= 0 ? discounts[j--] : 0)) / 100);
    }
    return ans;
  }
}
