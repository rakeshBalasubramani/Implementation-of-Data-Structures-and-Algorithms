package cs6301.g38;

import java.util.Random;
import java.util.Scanner;

public class SelectionComparison {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size in millions");

		int size = in.nextInt();
int million= 1000000;
size = size * million;
		System.out.println("Enter the value of k :");
		int k = in.nextInt();
		Integer arr[] = new Integer[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++)
			arr[i] = (rand.nextInt(i + 1));

//		System.out.println("Input array :");
//		for (int i : arr) {
//			System.out.print(i + " ");
//		}
		System.out.println();

		Timer time = new Timer();
		time.start();
		Integer r[] = PriorityQueueSelection.maxHeapSelectAlgo(arr, k);
		time.end();
		System.out.println("----------------------------------");
		System.out.println("Selection using Max heap");
		System.out.println(time);

//		for (int i : r) {
//			System.out.print(i + " ");
//		}
System.out.println();
		time.start();
		r = PriorityQueueSelection.minHeapSelectAlgo(arr, k);
		time.end();
		System.out.println("----------------------------------");

		System.out.println("Selection using Min heap");
		System.out.println(time);

//		for (int i : r) {
//			System.out.print(i + " ");
//		}
System.out.println();
		time.start();
		r = LinearTimeSelection.select(arr, k);
		time.end();
		System.out.println("----------------------------------");

		System.out.println("Linear time selection");
		System.out.println(time);

//		for (int i : r) {
//			System.out.print(i + " ");
//		}

		in.close();
	}

}
