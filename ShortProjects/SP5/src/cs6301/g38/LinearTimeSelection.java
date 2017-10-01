package cs6301.g38;

import java.util.Arrays;
/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Implementation of O(n) Selection algorithm using partition.
 * 
 */
public class LinearTimeSelection {
	
	/**Method used to find K largest elements in the given array.
	 * @param arr - Given array
	 * @param k - number of largest element needed from the given array.
	 * @return - K largest elements from the given array
	 */
	public static <T extends Comparable<? super T>> T[] select(T arr[], int k) {
		int n = arr.length;

		if (k <= 0) {

			return null;
		}

		T[] result = Arrays.copyOf(arr, k);

		if (k > n) {
			return arr;
		}

		select(arr, 0, n, k);
		int j = 0;
		for (int i = n - 1; j < k; i--, j++) {
			result[j] = arr[i];
		}

		return result;

	}

	/** Helper method for select algorithm.
	 * @param arr - Given array.
	 * @param i - left index to be used for selection.
	 * @param n - right index to be used for selection.
	 * @param k - number of largest elements needed.
	 * @return - K largest elements from the given array.
	 */
	private static <T extends Comparable<? super T>> T select(T[] arr, int i,
			int n, int k) {

		int r = i + n - 1;

		int threshold = 20;
		if (n < threshold) {
			insertionSort(arr, i, r);
			return arr[i + n - k];
		}

		else {

			int q = partition(arr, i, r);
			int left = q - i;
			int right = r - q;

			if (right >= k) {
				return select(arr, q + 1, right, k);
			} else if (right + 1 == k) {
				return arr[q];
			} else {
				return select(arr, i, left, k - (right + 1));
			}
		}
	}

	/**Method used to partition the given array based on random element (pivot) from the given array. 
	 * @param arr - Given array
	 * @param p - left index of the array to be used for partition.
	 * @param r - right index of the array to be used for partition.
	 * @return - Partitioned array.
	 */
	private static <T extends Comparable<? super T>> int partition(T[] arr,
			int p, int r) {		
		
		return QuickSort.partition(arr, p, r);

	}
	
	/**Insertion sort algorithm.
	 * @param arr - Given array
	 * @param k - Starting index of the array used for sorting.
	 * @param right - ending index of the array used for sorting.
	 */
	private static <T extends Comparable<? super T>> void insertionSort(
			T[] arr, int k, int right) {

		for (int i = k; i <= right; i++) {
			T key = arr[i];
			int j = i - 1;
			// Compare arr element with key and place the elements in correct
			// position
			while (j >= 0 && arr[j].compareTo(key) > 0) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = key;
		}
	}

}
