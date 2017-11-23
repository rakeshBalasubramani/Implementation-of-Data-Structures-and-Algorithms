package cs6301.g38;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Sort the generic and primitive int array elements using Merge Sort Algorithm.
 */

public class MergeSort {

	/**
	 * Merge Sort implementation for int array
	 * 
	 * @param arr
	 *            - input array to be sorted
	 * @param tmp
	 *            - array to store values during merge operation
	 */
	public static void mergeSort(int[] arr, int[] tmp) {

		mergeSort(arr, tmp, 0, arr.length - 1);
	}

	/**
	 * Recursive function for sorting int array
	 * 
	 * @param arr
	 *            -array holding the elements during each recursion
	 * @param tmp
	 *            -array to store values during merge operation
	 * @param start
	 *            -Start index of the array
	 * @param end
	 *            - End index of the array
	 */
	private static void mergeSort(int[] arr, int[] tmp, int start, int end) {

		if (start < end) {
			int mid = (start + end) / 2;

			mergeSort(arr, tmp, start, mid);// Sort first half of the array
			mergeSort(arr, tmp, mid + 1, end);// Sort second half of the array
			merge(arr, tmp, start, mid, end);// Merge the sorted arrays
		}

	}

	/**
	 * Function to merge sorted int arrays
	 * 
	 * @param arr
	 *            -array to hold sorted elements
	 * @param tmp
	 *            -array to store values during merge operation
	 * @param start
	 *            -Start index of the array
	 * @param mid
	 *            -Middle index of the array
	 * @param end
	 *            -End index of the array
	 */
	private static void merge(int[] arr, int[] tmp, int start, int mid, int end) {

		int lptr = start;
		int rptr = mid + 1;
		int arrPtr = start;
		int lsize = mid;

		int numElements = end - start + 1;

		while (lptr <= lsize && rptr <= end) { // Check if both left and right
												// array has elements
			if (arr[lptr] < arr[rptr]) { // store left array element if it is
											// less than right array element
				tmp[arrPtr++] = arr[lptr++];
			} else { // store right array element if it is less than the element
						// in left array
				tmp[arrPtr++] = arr[rptr++];
			}
		}

		while (lptr <= lsize) { // Only if left array has elements
			tmp[arrPtr++] = arr[lptr++];
		}
		while (rptr <= end) { // Only if right array has elements
			tmp[arrPtr++] = arr[rptr++];
		}

		for (int i = 0; i < numElements; i++, end--) {
			arr[end] = tmp[end]; // copy the tmp array to the arr
		}

	}

	/**
	 * Merge Sort implementation for Generic array
	 * 
	 * @param arr
	 *            - input array to be sorted
	 * @param tmp
	 *            - array to store values during merge operation
	 */

	public static <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {
		mergeSort(arr, tmp, 0, arr.length - 1);
	}

	/**
	 * Recursive function for sorting the generic array
	 * 
	 * @param arr
	 *            -array holding the elements during each recursion
	 * @param tmp
	 *            -array to store values during merge operation
	 * @param start
	 *            -Start index of the array
	 * @param end
	 *            - End index of the array
	 */

	private static <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;

			mergeSort(arr, tmp, start, mid);
			mergeSort(arr, tmp, mid + 1, end);
			merge(arr, tmp, start, mid, end);
		}
	}

	/**
	 * Function to merge generic sorted arrays
	 * 
	 * @param arr
	 *            -array to hold sorted elements
	 * @param tmp
	 *            -array to store values during merge operation
	 * @param start
	 *            -Start index of the array
	 * @param mid
	 *            -Middle index of the array
	 * @param end
	 *            -End index of the array
	 */

	private static <T extends Comparable<? super T>> void merge(T[] arr, T[] tmp, int start, int mid, int end) {

		int lptr = start;
		int rptr = mid + 1;
		int arrPtr = start;
		int lsize = mid;

		int numElements = end - start + 1;

		while ((lptr <= lsize) && (rptr <= end)) {
			if (arr[lptr].compareTo(tmp[rptr]) == -1) {
				tmp[arrPtr++] = arr[lptr++];
			} else {
				tmp[arrPtr++] = arr[rptr++];
			}
		}

		while (lptr <= lsize) {
			tmp[arrPtr++] = arr[lptr++];
		}
		while (rptr <= end) {
			tmp[arrPtr++] = arr[rptr++];
		}

		for (int i = 0; i < numElements; i++, end--) {
			arr[end] = tmp[end];
		}
	}

}
