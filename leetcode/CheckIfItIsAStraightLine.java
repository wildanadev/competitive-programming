public class CheckIfItIsAStraightLine {
	public boolean checkStraightLine(int[][] coordinates) {
		if (coordinates.length == 2)
			return true;
		double gradien = (coordinates[1][0] - coordinates[0][0]) != 0
		                 ? ((double) (coordinates[1][1] - coordinates[0][1]) / (coordinates[1][0] - coordinates[0][0]))
		                 : Double.POSITIVE_INFINITY;
		for (int i = 1; i < coordinates.length - 1; i++) {
			double gradienTemp = (coordinates[i + 1][0] - coordinates[i][0]) != 0
			                     ? ((double) (coordinates[i + 1][1] - coordinates[i][1])
			                        / (coordinates[i + 1][0] - coordinates[i][0]))
			                     : Double.POSITIVE_INFINITY;
			if (gradien != gradienTemp)
				return false;
		}
		return true;
	}
}