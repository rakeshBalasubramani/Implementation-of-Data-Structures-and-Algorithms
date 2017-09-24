package cs6301.g38;

import java.util.Random;
import java.util.Scanner;

public class ReArrangeNegativePositive {
	
	
	public static void rearrangeMinusPlus(int[] arr) { 
		
		rearrangeMinusPlus(arr,0,arr.length-1);
		
		
	}

	private static void rearrangeMinusPlus(int[] arr, int left , int right) {
		 if(left < right)
		    {
		        
		        int mid = left + (right - left) / 2; // To avoid overflow.
		 
		        
		        rearrangeMinusPlus(arr, left, mid);
		        rearrangeMinusPlus(arr, mid + 1, right);
		 
		    
		        mergeOfrearrangeMinusPlus(arr, left, mid, right);
		    }
	}
	
	/* Function to reverse an array. An array can be
	reversed in O(n) time and O(1) space. */
	static void reverse(int arr[], int l, int r)
	{
	    if (l < r)
	    {
	       
	        int temp = arr[l]; // swapping arr[l] and  arr[r])
	        arr[l] = arr[r];
	        arr[r] = temp;
	        reverse(arr, ++l, --r);
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

	private static void mergeOfrearrangeMinusPlus(int[] arr, int left, int mid,
			int right) {
		 int i = left; // Initial index of 1st subarray L[]
		    int j = mid + 1; // Initial index of 2nd subarray R[]
		 
		    while (i <= mid && arr[i] < 0)
		        i++;
		 
		    // arr[i..mid] is positive
		 
		    while (j <= right && arr[j] < 0)
		        j++;
		 
		    // arr[j..right] is positive
		 
		    // reverse positive part of left sub-array (arr[i..mid])
		    reverse(arr, i, mid);
		 
		    // reverse negative part of right sub-array (arr[mid+1..j-1])
		    reverse(arr, mid + 1, j - 1);
		 
		    // reverse arr[i..j-1]
		    reverse(arr, i, j - 1);
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
