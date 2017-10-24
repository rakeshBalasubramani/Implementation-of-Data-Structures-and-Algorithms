package cs6301.g38;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Desc Class used to count the number of pairs in array that sums to X
 */

import java.util.Arrays;
import java.util.Scanner;

public class PairsSum {

	/**
	 * @param arr - input array
	 * @param X - sum value
	 * @return - count of pairs in array that sums to X
	 */
	public static int howMany(int[] arr, int X){
   
		//initialize the start and end index of the array
	    int startIndex = 0;
	    int endIndex = arr.length -1;
	    int end = arr.length -1;	   
	    int pairsCount =0;
	    int sum=0; 
	    
	    //sort the array
	    Arrays.sort(arr);
	   
	    //loop through till the start index is less than end index
		while(startIndex < endIndex){  
		
			sum = arr[startIndex] + arr[endIndex];
			
			if( sum == X){  // check for the sum, if sum is equal to X
				pairsCount++;
				// condition checks for the duplicates at the end  
			    if(((arr[endIndex-1]+arr[startIndex]) == X) && (startIndex != endIndex-1)){
			         endIndex = endIndex-1;
			    }else{  //else increment only the start index
			        startIndex = startIndex+1;
			        endIndex = end;
			    }
			}else if(sum < X){ // if sum < X, since array is sorted, increment start index
				startIndex=startIndex+1;
			}else{ // if sum> x, decrement end index
				endIndex=endIndex-1;
			}
		}
	        
        return pairsCount;
	}
   
  	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size:");
		int size = in.nextInt();
		int[] arr = new int[size];
		
		System.out.println("Enter the array elements:");
		for(int i=0; i<size; i++){
			arr[i] = in.nextInt();
		}
		System.out.println("Enter the Integer X");
		int x = in.nextInt();
		
		int result = howMany(arr, x);
		
		System.out.println("Number of pairs in arr sum to X: " + result);
		in.close();
		
		
	}

}
