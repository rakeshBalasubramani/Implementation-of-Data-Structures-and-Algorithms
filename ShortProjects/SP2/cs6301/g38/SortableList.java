package cs6301.g38;

import java.util.NoSuchElementException;

/**
 * @author Avinash Venkatesh - axv165330 <br>
 * 		   HariPriyaa - hum160030 <br>
 * 		   Rakesh Balasubramani - rxb162130 <br>
 * 		   Rajkumar Panneer Selvam - rxp162130 
 *
 * @description This class contains functionality to sort using MergeSort Algorithm for linked list.
 */
public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {
	
	
   /** Method: Merges this list with the given list.
 * @param otherList - List to be merged with this list.
 */
private void merge(SortableList<T> otherList) {  // Merge this list with other list
    	
    	 
    	  Entry<T> temp = head;
    	  Entry<T>  left = head.next;
    	  Entry<T>   right = otherList.head.next;
        while((left != null) && (right !=null)){
            if(left.element.compareTo(right.element)<=0  ){
                temp.next = left;
                temp = left;
                left = left.next;
            }
            else{
                temp.next = right;
                temp = right; 
                right = right.next;
            }
        }
        if(right == null)
            temp.next = left;
        else
            temp.next = right;
       
    }
    
    /**
     * Implemets MergeSort Algorithm
     */
    private void mergeSort() { 
    	
    	  Entry<T> headToList = head.next ;
        // Base case : if head is null
        if (headToList== null || headToList.next == null)
        {
            return ;
        }
 
        // get the middle of the list
        Entry<T>  middle = getMiddleEntry(headToList);
        SortableList<T>  right = createRightList(middle.next);
 
        // set the next of middle node to null
        middle.next = null;
        tail  = middle;
 
        // Apply mergeSort on left list
         sort(this);
 
        // Apply mergeSort on right list
       sort(right);
 
        // Merge the left and right lists
        this.merge(right);
      
    	
    }
    
    
    /**Method : creates a new Right List for the merge sort.
     * @param rightHead - head for the new RightList.
     * @return - new Right list.
     */
    private SortableList<T> createRightList(
			Entry<T> rightHead ) {
		
    	SortableList<T> rightList = new SortableList<T>();
    	
    	rightList.head.next = rightHead;
    	
    	rightList.tail = tail;
    	
    	
		return rightList;
	}

	/**Method: Function to find the middle element in the linked list.
	 * @param headToList - head of the linked list.
	 * @return - middle element of the given linked list.
	 */
	private Entry<T> getMiddleEntry(
			Entry<T> headToList) {
    	
		
		 //Base case
        if (headToList == null)
            return headToList;
        Entry<T>  fastptr = headToList.next;
        Entry<T>  slowptr = headToList;
         
        // Move fastptr by two and slow ptr by one
        // Finally slowptr will point to middle node
        while (fastptr != null)
        {
            fastptr = fastptr.next;
            if(fastptr!=null)
            {
                slowptr = slowptr.next;
                fastptr=fastptr.next;
            }
        }
        return slowptr;
	}

	/**
	 * Method: sort the given list using Merge Sort.
	 * @param list - input list to be sorted. 
	 */
	public static<T extends Comparable<? super T>> void sort(SortableList<T> list) {
	list.mergeSort();
    }

	
	 public static void main(String[] args) throws NoSuchElementException {
	        int n = 10;
	        if(args.length > 0) {
	            n = Integer.parseInt(args[0]);
	        }

	        SortableList<Integer> lst = new SortableList<>();
	        for(int i=n; i>=0; i--) {
	            lst.add(new Integer(i));
	        }
	        System.out.println(" Interger inputs :");
	        lst.printList();
	        SortableList.sort(lst);
	        System.out.println( "After sorting the integer list :");
	        lst.printList();
	        SortableList<String> strList = new SortableList<String>();
	        strList.add("zebra");
	        strList.add("Lion");
	        strList.add("Tiger");
	        strList.add("Animal");
	        strList.add("Monkey");
	        
	        System.out.println("String inputs :");
	        strList.printList();
	        
	        
	       SortableList.sort(strList);
	       
	       System.out.println("After sorting the string list :");
	       
	     
	       strList.printList();

		
	
		
	    }
	}

