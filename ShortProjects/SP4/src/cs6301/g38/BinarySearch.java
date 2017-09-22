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
 * @Description Version of binary search that retruns the largest element that is less than or equal to x
 */

public class BinarySearch {

	
	 public static<T extends Comparable<? super T>> int recursiveBinarySearch(T[] A, int p, int r, T x) {
			// Compare middle element of A[p..r] to x to decide which half of the array to search
		 	if(p <= r) {
			    int q = (p+r)/2;
			    int cmp = A[q].compareTo(x);
			    if(cmp < 0) {  // A[q] is too small, x is not in left half
			    	return recursiveBinarySearch(A, q+1, r, x);
			    }
			    else if (cmp == 0) {// x found
			    	if(q-1 < 0){ // x first element
			    		return -1;
			    	}
			    	else{ // return the index of the largest element <= x
			       		return q-1;
			    	}
			    } 
			    else { // A[q] > x, so x is not in the right half
			    	return recursiveBinarySearch(A, p, q-1, x);
			    }
			} 
			else { // empty array, return false
			    return -1;
			}
	 }
	 public static<T extends Comparable<? super T>> int recursiveBinarySearch(T[] A, T x) {
		 return recursiveBinarySearch(A, 0, A.length-1, x);
	 }
	 
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size... ");
		int size = in.nextInt();
		
		int[] arr = new int[size];
		Integer[] iarr = new Integer[size];
		Random rand = new Random();
		for(int i=0; i<size; i++) {
		    arr[i] = rand.nextInt(10*size);
		}
		Arrays.sort(arr);
		
		for(int i=0; i<size; i++) {
		    iarr[i] = new Integer(arr[i]);
		}
		
		System.out.println("Array to be searched ");
		for(Integer i : iarr){
			System.out.println(i);
		}
		
		
		System.out.println("Enter the number to be searched... ");
	    Integer x = new Integer(in.nextInt());
	    int index = recursiveBinarySearch(iarr, x);
	    System.out.println("Index of greater element less than or equal to x: "+ index);
		in.close();

	}

}
