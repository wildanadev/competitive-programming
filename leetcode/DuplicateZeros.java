public class DuplicateZeros {
	public void duplicateZeros(int[] arr) {
		int[] copy = arr.clone();
		int indx = 0;
		for (int i = 0; i < arr.length; i++) {
			if(indx == arr.length) break;
			if (copy[i] == 0) {
				arr[indx++] = 0;
				if(indx == arr.length) break;
				arr[indx++] = 0;
			} else
				arr[indx++] = copy[i];
		}
	}
}
