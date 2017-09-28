package cs6301.g38;

import java.util.Random;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description This class is used to implement Quick Sort.
 * 
 */
public class QuickSort {

	/**
	 * Function to implement quick sort with dual pivot.
	 * 
	 * @param quickArray
	 *            - The input array that needs to be sorted.
	 */
	public static <T extends Comparable<? super T>> void dualPivotQuickSort(T[] quickArray) {
		dualPivotQuickSort(quickArray, 0, quickArray.length - 1);
	}

	/**
	 * Recursive function to implement quick sort with dual pivot.
	 * 
	 * @param quickArray
	 *            - Input array.
	 * @param p
	 *            - start.
	 * @param r
	 *            - end.
	 */
	private static <T extends Comparable<? super T>> void dualPivotQuickSort(T[] quickArray, int p, int r) {

		int i, j, k;
		T x1, x2;

		if (r <= p) {
			return;
		}

		i = generateRandomNumber(p, r);
		j = generateRandomNumber(p, r);

		swap(quickArray, i, p);
		swap(quickArray, j, r);

		x1 = quickArray[p];
		x2 = quickArray[r];

		if (x1.compareTo(x2) > 0) {
			swap(quickArray, p, r);
			x1 = quickArray[p];
			x2 = quickArray[r];
		}

		i = p + 1;
		j = r - 1;
		k = p + 1;

		while (i <= j) {

			if (quickArray[i].compareTo(x1) < 0) {
				swap(quickArray, i, k);
				i++;
				k++;
			} else if (quickArray[i].compareTo(x2) > 0) {
				swap(quickArray, i, j);
				j--;
			} else {
				i++;
			}
		}

		swap(quickArray, p, --k);
		swap(quickArray, r, ++j);

		dualPivotQuickSort(quickArray, p, k - 1);
		dualPivotQuickSort(quickArray, j + 1, r);

		if (x1.compareTo(x2) != 0) {
			dualPivotQuickSort(quickArray, k + 1, i - 1);
		}
	}

	/**
	 * Fucntion to implement quick sort.
	 * 
	 * @param quickArray
	 *            - Input array.
	 */
	public static <T extends Comparable<? super T>> void quickSort(T[] quickArray) {
		quickSort(quickArray, 0, quickArray.length - 1);
	}

	/**
	 * Recursive function to implement quick sort.
	 * 
	 * @param quickArray
	 *            - Input array.
	 * @param p
	 *            - start.
	 * @param r
	 *            - end.
	 */
	private static <T extends Comparable<? super T>> void quickSort(T[] quickArray, int p, int r) {
		int q;
		if (p < r) {
			q = partition(quickArray, p, r);
			quickSort(quickArray, p, q - 1);
			quickSort(quickArray, q + 1, r);
		}
	}

	/**
	 * Function to partition the input array based on the required condition.
	 * 
	 * @param quickArray
	 *            - Input array.
	 * @param p
	 *            - start.
	 * @param r
	 *            - end.
	 * @return - //Aviansh fill this as i have no idea what it is.
	 */
	private static <T extends Comparable<? super T>> int partition(T[] quickArray, int p, int r) {

		int i;
		T x;
		i = generateRandomNumber(p, r);
		swap(quickArray, i, r);
		x = quickArray[r];
		i = p - 1;
		for (int j = p; j < r; j++) {

			if (quickArray[j].compareTo(x) < 1) {
				i++;
				swap(quickArray, i, j);
			}

		}

		swap(quickArray, i + 1, r);

		return i + 1;
	}

	/**
	 * Function to swap elements.
	 * 
	 * @param quickArray
	 *            - Input array
	 * @param i
	 *            - Index to be swapped with quickArray[j]
	 * @param j
	 *            - Index to be swapped with quickArray[i]
	 */
	private static <T extends Comparable<? super T>> void swap(T[] quickArray, int i, int j) {

		T temp;

		temp = quickArray[i];
		quickArray[i] = quickArray[j];
		quickArray[j] = temp;

	}

	/**
	 * Fucntion to generate a random number between p and r.
	 * 
	 * @param p
	 *            - start.
	 * @param r
	 *            - end.
	 * @return - Random number between p and r.
	 */
	private static int generateRandomNumber(int p, int r) {
		Random random = new Random();
		int res = random.nextInt(r - p) + p;
		return res;
	}
}