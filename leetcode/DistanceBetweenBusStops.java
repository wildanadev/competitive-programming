public class DistanceBetweenBusStops {
	public int distanceBetweenBusStops(int[] distance, int start, int destination) {
		int n = distance.length;
		int clockWise = 0, counterClockWise = 0;
		int cc = start, rcc = start;
		while ((cc % n) != destination || (rcc % n) != destination) {
			if((cc % n) != destination) clockWise += distance[cc++ % n];
			if ((rcc % n) != destination) {
				rcc = (rcc - 1 + n ) % n;
				counterClockWise += distance[rcc];
			}
		}
		return Math.min(clockWise, counterClockWise);
	}
}