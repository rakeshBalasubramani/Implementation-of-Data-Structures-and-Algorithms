package cs6301.g38;

import java.util.Scanner;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Driver code used to check the performance of QuickSort using
 *              single and dual pivot partitions for arrays which has distinct
 *              and duplicate values.
 * 
 */

public class Sort {

	private static int size = 1000000;

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size in millions");
		int arrSize = in.nextInt();
		arrSize = arrSize * size;

		int[] qSort=new int[arrSize];
		Integer[] qSortSinglePivot = new Integer[arrSize];
		Integer[] qSortDualPivot = new Integer[arrSize];
		Timer qSortSP = new Timer();
		Timer qSortDP = new Timer();

		// Populating arrays with distinct elements
		for (int i = 0,p=arrSize-1; i <arrSize ; i++,p--) {
			qSortSinglePivot[p] = new Integer(i);
			qSortDualPivot[p] = new Integer(i);
		}

		System.out.println("\nQuick Sort with single pivot partition for distinct values:");
		qSortSP.start(); // start timer
		QuickSort.quickSort(qSortSinglePivot);
		System.out.println(qSortSP.end());

		System.out.println("\nQuick Sort with dual pivot partition for distinct values:");
		qSortDP.start(); // start timer
		QuickSort.dualPivotQuickSort(qSortDualPivot);
		System.out.println(qSortDP.end());

		// Populating elements with duplicate values
		for (int i = 0; i < arrSize; i++) {
			qSort[i]=(int) (Math.random() * arrSize);
			qSortSinglePivot[i] = new Integer(qSort[i]);
			qSortDualPivot[i] = new Integer(qSort[i]);
		}

		System.out.println("\nQuick Sort with single pivot partition for duplicate values:");
		qSortSP.start(); // start timer
		QuickSort.quickSort(qSortSinglePivot);
		System.out.println(qSortSP.end());

		System.out.println("\nQuick Sort with dual pivot partition for duplicate values:");
		qSortDP.start(); // start timer
		QuickSort.dualPivotQuickSort(qSortDualPivot);
		System.out.println(qSortDP.end());

		in.close();

	}

}
