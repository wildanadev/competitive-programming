public class CountDaysSpentTogether {
  public int countDaysTogether(
      String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
    int ans = 0;
    int[] monthlyDayCounts = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] prefixSum = new int[monthlyDayCounts.length + 1];

    for (int i = 0; i < monthlyDayCounts.length; i++)
      prefixSum[i + 1] = prefixSum[i] + monthlyDayCounts[i];

    int[] dateOfYear = new int[366];
    int arriveMonthAlice = Integer.parseInt(arriveAlice.split("-")[0]) - 1;
    int arriveDayAlice = Integer.parseInt(arriveAlice.split("-")[1]);
    int leaveMonthAlice = Integer.parseInt(leaveAlice.split("-")[0]) - 1;
    int leaveDayAlice = Integer.parseInt(leaveAlice.split("-")[1]);

    int arriveMonthBob = Integer.parseInt(arriveBob.split("-")[0]) - 1;
    int arriveDayBob = Integer.parseInt(arriveBob.split("-")[1]);
    int leaveMonthBob = Integer.parseInt(leaveBob.split("-")[0]) - 1;
    int leaveDayBob = Integer.parseInt(leaveBob.split("-")[1]);

    int startAlice = prefixSum[arriveMonthAlice] + arriveDayAlice;
    int endAlice = prefixSum[leaveMonthAlice] + leaveDayAlice;
    int startBob = prefixSum[arriveMonthBob] + arriveDayBob;
    int endBob = prefixSum[leaveMonthBob] + leaveDayBob;

    for (int i = startAlice; i <= endAlice; i++) dateOfYear[i]++;

    for (int i = startBob; i <= endBob; i++) dateOfYear[i]++;

    for (int i : dateOfYear) if (i == 2) ans++;

    return ans;
  }
}
