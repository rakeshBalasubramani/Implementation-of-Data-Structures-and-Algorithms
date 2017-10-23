package cs6301.g38;


/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Desc Class to find the longest streak of consecutive integers
 */

import java.util.Scanner;
import java.util.TreeSet;

public class LongestStreak {

	/**
	 * @param A - input array
	 * @return - length of lonegst streak of consecutive integers
	 */
	public static int longestStreak(int[] A) {
		 // RT = O(nlogn).
	     // Ex: A = {1,7,9,4,1,7,4,8,7,1}.  longestStreak(A) return 3,
	     //    corresponding to the streak {7,8,9} of consecutive integers
	     //    that occur somewhere in A.
		
		TreeSet<Integer> elementsSet = new TreeSet<Integer>();
        int length = 0;
 
        // add the array eleemnts to Treeset
        for (int i=0; i<A.length; i++)
            elementsSet.add(A[i]);
 
        //traverse through the array elements
        for (int i=0; i<A.length; i++)
        {
            
            if (!elementsSet.contains(A[i]-1)) // check if current element is the start of the streak
            {
            	int j = A[i];
                while (elementsSet.contains(j)){ // check for consecutive integers 
                    j++;
                }
               //update the longest length so far
                if (length < j-A[i]){
                    length = j-A[i];
                }
            }
        }
        return length;
        
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
		
		int result = longestStreak(arr);		
		System.out.println("Longest Streak: " + result);
		in.close();
		

	}

}
