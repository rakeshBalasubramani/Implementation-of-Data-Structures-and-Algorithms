package cs6301.g38;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class UniqueElements {

	
		
		private static<T extends Comparable<? super T>> T[] exactlyOnce(T[] A) {
			List<T> B = new ArrayList<T>();

			TreeMap<T, Integer> elementMap = new TreeMap<T, Integer>();
			
			for(int i =0 ; i<A.length ; i++){
				if(!elementMap.containsKey(A[i])){
					elementMap.put(A[i], 1);
				}
				else{
					elementMap.put(A[i], elementMap.get(A[i])+1);
				}				
			}
			System.out.println("TreeMap");
			System.out.println(elementMap);
			
			
			for(int i=0; i<A.length; i++){
				T x = A[i];
				if(elementMap.get(x) == 1){
					B.add(x);
				}
			}

			System.out.println("B " + B);
		
			/*Object[] result =  new Object[B.size()];
			T[] res;
			B.toArray(result);
			 if (A instanceof Object) {
				 res  = (T[]) result;
				
			}
			T[] arr = (T[]) new Object[result.length];
			
			
			return (T[]) result  ;
			*/
			return null;
			
			 
			 // RT = O(nlogn).
		     // Ex: A = {6,3,4,5,3,5}.  exactlyOnce(A) returns {6,4}
	   }

	
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size:");
		int size = in.nextInt();
		Integer[] arr = new Integer[size];
		
		System.out.println("Enter the array elements:");
		for(int i=0; i<size; i++){
			arr[i] = new Integer(in.nextInt());
		}
		
		
		Integer[] result = (Integer[]) exactlyOnce(arr);
		
		System.out.println("New Array " + result);
		in.close();
		

	}

}
