package cs6301.g38;

import java.util.Arrays;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description This class is used to implement Merge Sort.
 * 
 */
public class MergeSort {

	private static int threshold = 19;

	/**
	 * 
	 * @description Version 4 Using Insertion Sort and avoid copying into tmp array
	 * @param mergeArray
	 *            - Array to be sorted
	 */
	public static <T extends Comparable<? super T>> void mergeSortAvoidTmpCopy(T[] mergeArray) {
		T[] temp = Arrays.copyOf(mergeArray, mergeArray.length);
		mergeSort4(mergeArray, temp, 0, mergeArray.length - 1);
	}

	/**
	 * @param mergeArray
	 *            - array to be sorted
	 * @param temp
	 *            - temp array
	 * @param start
	 *            - start index
	 * @param end
	 *            - end index
	 */
	private static <T extends Comparable<? super T>> void mergeSort4(T[] mergeArray, T[] temp, int start, int end) {
		if (end - start < threshold) {
			insertionSort(mergeArray);
		} else {
			int mid = (start + end) / 2;
			mergeSort4(mergeArray, temp, start, mid);
			mergeSort4(mergeArray, temp, mid + 1, end);
			merge4(mergeArray, temp, start, mid, end);

		}
	}

	/**
	 * merge - avoid copying to tmp array
	 * 
	 * @param A
	 *            - input Array
	 * @param mergeArray
	 *            - tmp array
	 * @param start
	 *            - start index
	 * @param mid
	 *            - mid index
	 * @param end
	 *            - end index
	 */
	private static <T extends Comparable<? super T>> void merge4(T[] mergeArray, T[] tmp, int start, int mid, int end) {
		int i = start;
		int j = mid + 1;

		for (int k = start; k < end; k++) {
			if ((j > end) || (i <= mid && mergeArray[i].compareTo(mergeArray[j]) <= 0)) {
				tmp[k] = mergeArray[i++];
			} else {
				tmp[k] = mergeArray[j++];
			}
		}

	}

	/**
	 * method performing Insertion Sort
	 * 
	 * @param mergeArray
	 *            - arr to be sorted
	 */
	public static <T extends Comparable<? super T>> void insertionSort(T[] mergeArray) {
		int size = mergeArray.length;
		for (int i = 1; i < size; i++) {
			T key = mergeArray[i];
			int j = i - 1;
			while (j >= 0 && mergeArray[j].compareTo(key) > 0) {
				mergeArray[j + 1] = mergeArray[j];
				j = j - 1;
			}
			mergeArray[j + 1] = key;
		}
	}

}
