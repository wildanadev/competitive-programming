public class NearestAvailableDrone {
  public int nearestDrone(int[][] drones, int[] target) {
    int ans = -1;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < drones.length; i++) {
      int xi = drones[i][0];
      int yi = drones[i][1];
      int range = drones[i][2];
      int result = Math.abs(xi - target[0]) + Math.abs(yi - target[1]);
      if (result <= range && result < min) {
        ans = i;
        min = result;
      }
    }
    return ans;
  }
}
