package cs6301.g38;

import java.util.Random;
import java.util.Scanner;
/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 *	@description  This class will reorder an int array A[] by moving negative elements to the front,
   followed by its positive elementsby retaining the relative order of positive numbers as well as negative numbers.
 *
 */
public class ReArrangeNegativePositive {
	
	
	/**Method used to rearrange the negative numbers in the front, followed by its positive numbers retaining their orders in the given array.
	 * @param arr - Input Array 
	 */
	public static void rearrangeMinusPlus(int[] arr) { 
		
		rearrangeMinusPlus(arr,0,arr.length-1);
		
		
	}

	/** Recursive Helper function for ReArrangeNegativePositive. Using divide and conquer strategy.
	 * @param arr - Input array
	 * @param start - starting index of the array.
	 * @param end - ending index of the array.
	 */
	private static void rearrangeMinusPlus(int[] arr, int start , int end) {
		 if(start < end)
		    {
		        
		        int mid = start + (end - start) / 2; // To avoid overflow.
		 
		        
		        rearrangeMinusPlus(arr, start, mid);
		        rearrangeMinusPlus(arr, mid + 1, end);
		 
		    
		        mergeOfRearrangeMinusPlus(arr, start, mid, end);
		    }
	}
	
	
	/** Method used to reverse the given array recursively.
	 * @param arr - Input array.
	 * @param start - starting index of the array 
	 * @param end - ending index of the array
	 */
	private static void reverseArray(int arr[], int start, int end)
	{
	    if (start < end)
	    {
	       
	        int temp = arr[start]; // swapping arr[l] and  arr[r])
	        arr[start] = arr[end];
	        arr[end] = temp;
	        reverseArray(arr, ++start, --end);
	    }
	}
	
	
	
	
/*arr[] = [Ln Lp Rn Rp]; we need to convert to [Ln Rn Lp Rp];
 * 
 * Reverse [Lp] to [Lp'] and [Rn] to [Rn'], which we will get [Ln Lp' Rn' Rp]
 * 
 * Now reverse [Lp'Rn'], which will leads to [Ln Rn Lp Rp]
 * 
 * 
 */

	/** Method used for merge operations. 
	 * Given arr[] = [Ln Lp Rn Rp], we need to convert to [Ln Rn Lp Rp];
	 * Reverse [Lp] to [Lp'] and [Rn] to [Rn'], which leads to arr][]= [Ln Lp' Rn' Rp]
	 * Now reverse [Lp'Rn'], which will leads to arr[]=[Ln Rn Lp Rp]
	 * 
	 * @param arr - Input array.
	 * @param left - starting index of the left sub-array.
	 * @param mid - middle element of the array.
	 * @param right - ending index of the right sub-array.
	 */
	private static void mergeOfRearrangeMinusPlus(int[] arr, int left, int mid,
			int right) {
		 int i = left; // Initial index of 1st subarray L[]
		    int j = mid + 1; // Initial index of 2nd subarray R[]
		 
		    while (i <= mid && arr[i] < 0)// arr[i..mid] is positive
		        i++;
		 
		    
		 
		    while (j <= right && arr[j] < 0)  // arr[j..right] is positive
		        j++;
		 
		    
		 
		    
		    reverseArray(arr, i, mid); // reverse positive part of left sub-array (arr[i..mid])
		 
		    
		    reverseArray(arr, mid + 1, j - 1); // reverse negative part of right sub-array (arr[mid+1..j-1])
		 
		   
		    reverseArray(arr, i, j - 1);  // reverse arr[i..j-1]
	}
	
	public static void main (String args[])
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size in millions ");
		int arrSize = in.nextInt();
		in.close();
		arrSize = arrSize * 1;
		int[] arr = new int[arrSize];
		
		Random r = new Random();
		// Populating the array with random elements
		for (int i = 0; i < arrSize; i++) {
			arr[i] = r.nextInt(arrSize + 1 +arrSize) - arrSize;;
		}
		
		for(int i : arr)
		System.out.print(i + " ");
		System.out.println();
		
	ReArrangeNegativePositive.rearrangeMinusPlus(arr);
	
	for(int i : arr)
		System.out.print(i+ " ");
	}
	
	
}
