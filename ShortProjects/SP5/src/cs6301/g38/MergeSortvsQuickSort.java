package cs6301.g38;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MergeSortvsQuickSort {
	private static int size = 1000000;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size in millions");
		int arrSize = in.nextInt();
		arrSize = arrSize * size;
		Integer[] mergeArray = new Integer[arrSize];
		Integer[] quickArray = new Integer[arrSize];
		Timer mergeTimer = new Timer();
		Timer quickTimer = new Timer();
		List<Integer> temp = IntStream.range(0, arrSize).boxed().collect(Collectors.toList()); // Generate arrSize unique
																								// numbers.
		Collections.shuffle(temp);
		int p = 0;
		for (int x : temp) {
			mergeArray[p] = x;
			quickArray[p++] = x;
		}
		mergeTimer.start(); // start timer
		MergeSort.mergeSortAvoidTmpCopy(mergeArray);
		mergeTimer.end();
		quickTimer.start(); // start timer
		QuickSort.dualPivotQuickSort(quickArray);
		quickTimer.end();
		System.out.println("Merge Sort For Distinct values : " + mergeTimer); // end timer
		System.out.println("Quick Sort For Distinct values : " + quickTimer); // end timer
		for (int i = 0; i < arrSize; i++) {
			mergeArray[i] = (int) (Math.random() * arrSize);
			quickArray[i] = (int) (Math.random() * arrSize);
		}
		mergeTimer.start(); // start timer
		MergeSort.mergeSortAvoidTmpCopy(mergeArray);
		mergeTimer.end();
		quickTimer.start(); // start timer
		QuickSort.dualPivotQuickSort(quickArray);
		quickTimer.end();
		System.out.println("Merge Sort For Duplicate values : " + mergeTimer); // end timer
		System.out.println("Quick Sort For Duplicate values : " + quickTimer); // end timer
		in.close();

	}
}
