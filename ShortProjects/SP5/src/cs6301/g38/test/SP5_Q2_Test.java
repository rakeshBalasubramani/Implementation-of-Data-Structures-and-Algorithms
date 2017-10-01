package cs6301.g38.test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cs6301.g38.QuickSort;


public class SP5_Q2_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter array size in millions :");

		int size = in.nextInt();
		size =size *1000000;

		Integer arr[] = new Integer[size]; //1000000
		Random rand = new Random();
		for (int i = 0; i < size; i++)
			arr[i] = (rand.nextInt(10));
//
//		System.out.println("Input array :");
//		for (int i : arr) {
//			System.out.print(i + " ");
//		}
	Integer[]	arr1 = Arrays.copyOf(arr, arr.length);
		System.out.println();

		System.out.println("-----------------------------------------------------------");
System.out.println("Array with more duplicate elements");
		Timer time = new Timer();
		time.start();
	 QuickSort.quickSort(arr);
		time.end();
		System.out.println( "Normal Quick Sort \n");
		System.out.println(time);
//		for (int i : arr) {
//			System.out.print(i + " ");
//		}
//		
		
		time.start();
		QuickSort.dualPivotQuickSort(arr1);
		time.end();
		System.out.println();
		System.out.println( "Dual pivot quick sort \n");
		System.out.println(time);
//		for (int i : arr1) {
//			System.out.print(i + " ");
//		}

		System.out.println();
System.out.println("-----------------------------------------------------------");
System.out.println("Unique elements");
		List<Integer> temp = IntStream.range(0,size).boxed().collect(Collectors.toList()); // Generate arrSize unique
		int p = size-1;
		for (int x : temp) {
			arr[p] = x;
			arr1[p--] = x;
		}
		
		time.start();
		 QuickSort.quickSort(arr);
			time.end();
			System.out.println( "Normal Quick Sort \n");
			System.out.println(time);
//			for (int i : arr) {
//				System.out.print(i + " ");
//			}
		
		

			time.start();
			QuickSort.dualPivotQuickSort(arr1);
			time.end();
			System.out.println();
			System.out.println( "Dual pivot quick sort \n");
			System.out.println(time);
//			for (int i : arr1) {
//				System.out.print(i + " ");
//			}

			
		
		in.close();
	}

}
