package cs6301.g38;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class HeapComparison {

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
		Integer r[] = PriorityQueueSelection.minHeapSelectAlgo(arr, k);
		time.end();
		System.out.println("----------------------------------");
		System.out.println("Selection using Java Priority Queue");
		System.out.println(time);

		for (int i : r) {
			System.out.print(i + " ");
		}
System.out.println();
		time.start();
	   	Comparator<Integer> comp = new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					// TODO Auto-generated method stub
					return -1*Integer.compare(o1, o2);
				}
	    		
			};
	    	
PriorityQueueSelection.binaryHeapSelectAlgo(arr, k, comp);	    	
	    	
	    	time.end();
		System.out.println("----------------------------------");

		System.out.println("Selection using Min heap");
		System.out.println(time);

		for (int i : r) {
			System.out.print(i + " ");
		}


//		for (int i : r) {
//			System.out.print(i + " ");
//		}

		in.close();
	}

}
