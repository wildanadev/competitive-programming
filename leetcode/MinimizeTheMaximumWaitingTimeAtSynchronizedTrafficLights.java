public class MinimizeTheMaximumWaitingTimeAtSynchronizedTrafficLights {
  public int minPenalty(int period, int[] lights, int[] arrivalTime) {
    int ans = 0;
    int maxLight = Integer.MIN_VALUE;
    for (int i : lights) maxLight = Math.max(maxLight, i);
    for (int i = 0; i < arrivalTime.length; i++) {
      int r = arrivalTime[i] % period;
      if (r >= maxLight) ans = Math.max(ans, period - r);
    }
    return ans;
  }
}
