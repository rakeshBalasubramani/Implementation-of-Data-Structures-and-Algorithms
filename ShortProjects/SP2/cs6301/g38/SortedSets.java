package cs6301.g38;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * @author Avinash Venkatesh - axv165330 <br>
 * 		   HariPriyaa - hum160030 <br>
 * 		   Rakesh Balasubramani - rxb162130 <br>
 * 		   Raj Kumar Panneer Selvam - rxp162130 
 *
 * @description This class implements functions for union,intersection and difference for linkedlists implementing sorted sets
 */
public class SortedSets {

	
	/**
	 * Method: Implements set intersect
	 * @param l1 - first linkedlist 
	 * @param l2 - second linkedlist
	 * @param outList - empty list which will be used to hold the intersected elements 
	 */
	public static <T extends Comparable<? super T>> void intersect(List<T> l1, List<T> l2, List<T> outList) {
		// set the iterator for both lists
		ListIterator<T> it1 = l1.listIterator();
		ListIterator<T> it2 = l2.listIterator();
		
		// pointing to first element of both lists
		T x1 =  hasNext(it1);
		T x2 =  hasNext(it2);
		
		
		// now traverse till the end of both the lists
		// find the common elements and append to the outList
		
		while(x1 != null  && x2 != null){
			// since the elements are sorted, if the element is small, then advance the iterator
			if((x1).compareTo(x2) < 0){
				x1 = hasNext(it1);
			}
			else if((x1).compareTo(x2) > 0){
				x2 = hasNext(it2);
			}
			
			else{
				outList.add( x1); // adding the common element to the outList and increment both iterator
				x1 =  hasNext(it1);
				x2 =  hasNext(it2);
			}
			
		}
	}

	
	/**
	 * Method: Implements Set Union
	 * @param l1 - first linkedlist
	 * @param l2 - second linkedlist
	 * @param outList - output list which will be used to hold the union elements of both first and second linkedlists
	 */
	public static <T extends Comparable<? super T>> void union(List<T> l1, List<T> l2, List<T> outList) {
		
		// set the iterator for both lists
		ListIterator<T> it1 = l1.listIterator();
		ListIterator<T> it2 = l2.listIterator();
		
		// pointing to first element of both lists
		T x1 =  hasNext(it1);
		T x2 =  hasNext(it2);
		

		// now traverse till the end of both the lists
		// Insert all the elements to outList in sorted order
		while(x1 != null && x2 != null){
			
			if((x1).compareTo(x2) < 0){
				outList.add( x1); 
				x1 = hasNext(it1);
			}
			else if((x1).compareTo(x2) > 0){
				outList.add( x2);
				x2 = hasNext(it2);
			}
			
			else{
				outList.add( x1); // adding the common element to the outList and advance the iterator
				x1 =  hasNext(it1);
				x2 =  hasNext(it2);
			}
		}
		
		while(x1 != null){ //if first list still has elements, add them to output list
			outList.add( x1); 
			x1 = hasNext(it1);			
		}
		
		while(x2 != null){ // if second list has any elements, add them to output list
			outList.add( x2); 
			x2 = hasNext(it2);			
		}
	
	}

	/**
	 * Method: implements set difference
	 * @param l1 - first linkedlist
	 * @param l2 - second linkedlist
	 * @param outList - output list used to hold the first-second - set difference elements
	 */
	public static <T extends Comparable<? super T>> void difference(List<T> l1, List<T> l2, List<T> outList) {
		// set the iterator for both lists
		ListIterator<T> it1 = l1.listIterator();
		ListIterator<T> it2 = l2.listIterator();
		
		// pointing to first element of both lists
		T x1 =  hasNext(it1);
		T x2 =  hasNext(it2);
		

		while(x1 != null  && x2 != null){
			// since the elements are sorted, if the first list element is small, then add them to out since it will not occur in second
			if((x1).compareTo(x2) < 0){
				outList.add(x1); 
				x1 = hasNext(it1);
			}
			else if((x1).compareTo(x2) > 0){ // else, advance the second iterator
				x2 = hasNext(it2);
			}
			
			else{ // if they are equal, simply advance the iterator
				x1 =  hasNext(it1);
				x2 =  hasNext(it2);
			}
			
		}
		while(x1 != null){
			outList.add( x1); 
			x1 = hasNext(it1);			
		}		
	}

	/**
	 * Method : helper function to check whether list has next element 
	 * @param it - List iterator
	 * @return - retruns the next element of the list, if present else, returns null
	 */
	private static <T> T hasNext(ListIterator<T> it) {
		return it.hasNext() ? it.next() : null;
	}

	
	/** 
	 * Driver code 
	 * 
	 */
	public static void main(String[] args) {
		// creating two linked lists
		List<Integer> lst1 = new LinkedList<Integer>();
		List<Integer> lst2 = new LinkedList<Integer>();

		// populating two list in sorted order
		for (int i = 0; i < 20; i++) {
			lst1.add(new Integer(i));
		}
		
		for (int i = 0; i < 10; i++) {
			lst2.add(new Integer(i + 4));
		}
		
		

		System.out.println("List1 ");
		System.out.println(lst1);
		System.out.println("List2 ");
		System.out.println(lst2);

		List<Integer> outListIntersect = new LinkedList<Integer>();
		List<Integer> outListUnion = new LinkedList<Integer>();
		List<Integer> outListDifference = new LinkedList<Integer>();

		// Intersect operation
		intersect(lst1, lst2, outListIntersect);
		System.out.println("Intersect:  "+ outListIntersect);

		// Union operation
		union(lst1, lst2, outListUnion);
		System.out.println("Union:  " + outListUnion);
		
		// Difference operation
		difference(lst1, lst2, outListDifference);
		System.out.println("Difference:  " + outListDifference);
		
		
		List<String> str1list= new ArrayList<String>();
		List<String> str2list = new ArrayList<String>();
		
		
		str1list.add("Angel");
		str1list.add("Banana");
		str1list.add("Kumar");
		str1list.add("Raj");
		str1list.add("zebra");
		
		str2list.add("Raj");
	 	str2list.add("Rakesh");
	 	str2list.add("zebra");
	 	
	 	System.out.println("List1 ");
		System.out.println(str1list);
		System.out.println("List2 ");
		System.out.println(str2list);

		List<String> outListIntersectstr = new LinkedList<String>();
		List<String> outListUnionstr = new LinkedList<String>();
		List<String> outListDifferencestr = new LinkedList<String>();

		// Intersect operation
		intersect(str1list, str2list, outListIntersectstr);
		System.out.println("Intersect:  "+ outListIntersectstr);

		// Union operation
		union(str1list, str2list, outListUnionstr);
		System.out.println("Union:  " + outListUnionstr);
		
		// Difference operation
		difference(str1list, str2list, outListDifferencestr);
		System.out.println("Difference:  " + outListDifferencestr);
	 	
		
	}

	
}
