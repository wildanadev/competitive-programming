public class FinalPricesWithASpecialDiscountInAShop {
  public int[] finalPrices(int[] prices) {
    int[] ans = new int[prices.length];
    int i = 0;
    while (i < prices.length) {
      int j = i + 1;
      while (j < prices.length) {
        if (prices[i] >= prices[j]) break;
        j++;
      }
      ans[i] = (j == prices.length) ? prices[i] : prices[i] - prices[j];
      i++;
    }
    return ans;
  }
}
