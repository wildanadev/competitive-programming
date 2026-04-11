public class BestTimeToBuyAndSellStock {
  public int maxProfit(int[] prices) {
    int ans = 0;
    int buy = 100_000;
    for (int i = 0; i < prices.length; i++) {
      if (buy > prices[i]) buy = prices[i];
      ans = Math.max(ans, prices[i] - buy);
    }
    return ans;
  }
}
