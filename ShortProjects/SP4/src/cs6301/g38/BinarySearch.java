package cs6301.g38;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Question 4 - Version of binary search that returns index of the largest
 *              element that is less than or equal to x
 */

public class BinarySearch {

	/**
	 * @param A - Sorted Array
	 * @param p - start index of the array
	 * @param r - end index of the array
	 * @param x - element to be searched
	 * @return - index of largest element <=x
	 */
	public static <T extends Comparable<? super T>> int recursiveBinarySearch(T[] A, int p, int r, T x) {
		// Compare middle element of A[p..r] to x to decide which half of the
		// array to search
		if (p <= r) {
			int q = (p + r) / 2;
			int cmp = A[q].compareTo(x);
			if (cmp < 0) { // A[q] < x, search x in the right half
				
				if (q!=A.length-1 && A[q + 1].compareTo(x) > 0) { // x is not present and A[q] < x < A[q+1], then return the index of A[q]
					return q;
				} else if (q!=A.length-1 && A[q + 1].compareTo(x) == 0) { // if A[q+1] == x , then return the index of A[q+1]
					return q + 1;
				} else {
						if(q+1==A.length) //end of array , x > last element of array, return index of last element 
						{
							return q;
						}
					return recursiveBinarySearch(A, q + 1, r, x); // if x is present inthe right half, we search right half recursively
				}
			
			} else if (cmp == 0) {// x found
				// return the index of the largest element == x
				return q;

			} else { // A[q] > x, search x in left half
				
				if (q!=0 && A[q - 1].compareTo(x) <= 0) { // if A[q-1] < x, return the index of q-1
					return q-1;
				} else {
				return recursiveBinarySearch(A, p, q - 1, x);
				}
			}
		} else { // empty array, return false
			return -1;
		}
	}

	/**
	 * @param A - Sorted Array
	 * @param x - element to be searched 
	 * @return - index of largest element <=x 
	 */
	public static <T extends Comparable<? super T>> int recursiveBinarySearch(T[] A, T x) {
		return recursiveBinarySearch(A, 0, A.length - 1, x);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size... ");
		int size = in.nextInt();

		int[] arr = new int[size];
		Integer[] iarr = new Integer[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextInt(10 * size); // randomly fill the array elements
		}
		Arrays.sort(arr); // sort the array

		for (int i = 0; i < size; i++) {
			iarr[i] = new Integer(arr[i]);
		}

		System.out.println("Array to be searched ");
		for (Integer i : iarr) {
			System.out.println(i);
		}

		System.out.println("Enter the number to be searched... ");
		Integer x = new Integer(in.nextInt());
		int index = recursiveBinarySearch(iarr, x);
		System.out.println("Index of greater element less than or equal to x: "	+ index);
		in.close();

	}

}
