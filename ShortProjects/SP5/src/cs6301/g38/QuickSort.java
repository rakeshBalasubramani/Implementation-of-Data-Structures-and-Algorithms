package cs6301.g38;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description  SP5- Question1 - Class to compare the two versions of partitions of Quick Sort
 * 
 * */
public class QuickSort {

	/**
	 * Quick Sort implementing partition 1
	 * @param arr - arr to be sorted
	 */
	public static <T extends Comparable<? super T>> void quickSort1(T[] arr){
		quickSort1(arr, 0, arr.length-1);
		//printArray(arr);
	}

	/**
	 * method that implements quick sort recursively
	 * @param arr - array to be sorted
	 * @param p - start index 
	 * @param r - end index
	 */
	private static<T extends Comparable<? super T>> void quickSort1(T[] arr, int p, int r) {
		if (p < r){
			int q = partition1(arr, p, r);
			quickSort1(arr, p, q - 1); 
			quickSort1(arr, q + 1, r); 
		}

	}

	
	/**
	 * partition 1
	 * @param A - array to be sorted
	 * @param p - start index
	 * @param r - end index
	 * @return - parition index
	 */
	private static<T extends Comparable<? super T>> int partition1(T[] A, int p, int r){
		Random ran = new Random();
		Integer i = ran.nextInt(r-p) + p; // select i randomly between A[p...r]
		exchange(A,i,r);
		T x = A[r]; //pivot
		i=p-1;

		for(int j=p; j<=r-1; j++){
			if(A[j].compareTo(x) == -1){
				i+=1;
				exchange(A, i, j);
			}
		}
		exchange(A,i+1,r); //bring pivot back to middle
		return i+1;
	}
	

	/**
	 * Quick Sort version implementing partition 2
	 * 
	 * @param arr - array to be sorted
	 */
	public static<T extends Comparable<? super T>> void quickSort2 (T[] arr){
		quickSort2(arr, 0, arr.length-1);
		//printArray(arr);
	}

	/**
	 * method that implements quick sort recursively
	 * @param arr - array to be sorted
	 * @param p - start index 
	 * @param r - end index
	 */	
	private static<T extends Comparable<? super T>> void quickSort2(T[] arr, int p, int r) {
		if (p < r){
			int q = partition2(arr, p, r);
			quickSort2(arr, p, q);
			quickSort2(arr, q+1 , r);
		}
	}
	
	/**
	 * partition 1
	 * @param A - array to be sorted
	 * @param p - start index
	 * @param r - end index
	 * @return - parition index
	 */
	private static <T extends Comparable<? super T>>int partition2(T[] A, int p, int r){

		Random ran1 = new Random();
		int ranElement = ran1.nextInt(r-p) + p;
		//System.out.println("ran elt "+ ranElement);
		T x = A[ranElement];
		int i = p-1;
		int j = r+1;

		while(true){

			do{
				i++;
			}while(A[i].compareTo(x) == -1);
			do{
				j--;
			}while(A[j].compareTo(x) == 1);

			if(i>=j){
				return j;
			}

			exchange(A, i, j);
			//i++;
			//j--;

		}
	}

	/**
	 * method to exchnage (swap) two elements
	 * @param A - array
	 * @param i - index of the first element to be exchanged
	 * @param j - index of the second element to be exchanged
	 */
	private static<T extends Comparable<? super T>> void exchange(T[] A, int i, int j){
		T tmp;
		tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;

	}
	
//	public static<T extends Comparable<? super T>> void printArray(T[] arr){
//		for(int i = 0; i<arr.length; i++){
//			System.out.println(arr[i]);
//		}
//	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size ");
		int arrSize = in.nextInt();

		Timer t = new Timer();

		//populating an array with randomly generted distinct elements 
		//first randomly generate elements in hash set to avoid duplicates and then convert into an array
		Random r = new Random();
		Set<Integer> uniqueNumbers = new HashSet<>();
		while (uniqueNumbers.size()< arrSize){
			uniqueNumbers.add(r.nextInt(arrSize*2));
		}

		Integer[] arr = uniqueNumbers.toArray(new Integer[uniqueNumbers.size()]);
		Integer[] arr1 = uniqueNumbers.toArray(new Integer[uniqueNumbers.size()]);

//		System.out.println("Array to be sorted ");
//		printArray(arr);

		System.out.println("Quick Sort - Arrays with distinct elements ");
		
		System.out.println("Partition 1");
		t.start(); // start timer
		quickSort1(arr);
		System.out.println(t.end()); // end timer
		System.out.println();
		
		System.out.println("Partition 2");
		t.start(); // start timer
		quickSort2(arr1);
		System.out.println(t.end()); // end timer
		System.out.println();

		// Quick Sort on randomly shuffled elements

		//System.out.println("Now shuffling the array");
		List<Integer> list = Arrays.asList(arr);
		Collections.shuffle(list);
		list.toArray(arr);
		list.toArray(arr1);
		System.out.println("Quick Sort on randomly shuffled elements ");
		
		System.out.println("Partition 1");
		t.start(); // start timer
		quickSort1(arr);
		//printArray(arr);
		System.out.println(t.end()); // end timer
		System.out.println();
		
		System.out.println("Partition 2");
		t.start(); // start timer
		quickSort2(arr1);
		//printArray(arr1);
		System.out.println(t.end()); // end timer
		System.out.println();


		
		// Quick Sort on arrays having elements in descending order

		//System.out.println("Array with Descending order ");
		Arrays.sort(arr, Collections.reverseOrder());
		Arrays.sort(arr1, Collections.reverseOrder());
		//printArray(arr);
		System.out.println("Quick Sort on arrays with elements in descending order ");
		
		System.out.println("Partition 1");
		t.start(); // start timer
		quickSort1(arr);
		//printArray(arr);
		System.out.println(t.end()); // end timer
		System.out.println();
		
		System.out.println("Partition 2");
		t.start(); // start timer
		quickSort2(arr1);
		//printArray(arr1);
		System.out.println(t.end()); // end timer
		System.out.println();

		in.close();

	}

}
