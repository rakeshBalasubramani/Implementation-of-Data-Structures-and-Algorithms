// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g38;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 */
public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    long base;  // Change as needed
    public long getBase() {
		return base;
	}

	public void setBase(long base) {
		this.base = base;
	}

	LinkedList<String> num = new LinkedList<>();

	LinkedList<Long> num1 = new LinkedList<Long>();
	LinkedList<Long> outList = new LinkedList<Long>();
	   	
    /* Start of Level 1 */
    Num(String s) {
    	String [] temp = s.split("");
    	for(String i:temp) {
    		num.addFirst(i);
    	}
    }

    Num(long x) {
    	
    	String str = Long.toString(x);
    	String[] temp = str.split("");
    	for(String i:temp) {
    		num.addFirst(i);
    	}
    }
    public Num() {
		// TODO Auto-generated constructor stub
	}

	public void toBase() {
    	LinkedList<String> quotient = new LinkedList<String>();
    	LinkedList<String> reminder = new LinkedList<String>();
    	while(!num.isEmpty()){
    		quotient.clear();
    		int i=num.size()-1;
    		long temp = Long.parseLong(num.get(i--));
    		while(i>=0) {
    	    	  quotient.addFirst(Long.toString(temp/base));
    	    	  temp = temp%base;
    	    	  if(temp<base&&i>=0)
    	    		  temp=temp*10+Long.parseLong(num.get(i--));
    	    }
    		quotient.addFirst(Long.toString(temp/base));
    		num.clear();
    		while(!quotient.isEmpty()&&quotient.getLast().equals(new String("0")))
    			quotient.removeLast();
    		if(!quotient.isEmpty())
    		for(String x :quotient) {
    			num.add(x);
    		}
    		reminder.add(Long.toString(temp%base));
    	}
    	for(String i:reminder) {
    		num.add(i);
    	}
    	
    	for(String i:num) {
     		num1.add(Long.parseLong(i));
    	}
    }
    public void toDecimal() {
    	int p=0;
    	for(String i:num) {
     		Long.parseLong(i);
    	}
    }
   
    
    static Num add(Num a, Num b) {
	   	
	   	long carry = 0;
	   	long sum = 0;
	   	ListIterator<Long> it1 = a.num1.listIterator();
	   	ListIterator<Long> it2 = b.num1.listIterator();
	   	Num z = new Num();
	   	while(it1.hasNext() || it2.hasNext() || carry>0){
	   		sum = next(it1) + next(it2) + carry ;
	   		z.outList.add(sum % a.base);
	   		carry = sum/a.base;
	   		
	   	}
	   if (carry > 0)
		   z.outList.add(carry);
	   
		return z;
   }

     
	/**
	 * Method : helper function to check whether list has next element 
	 * @param it1 - List iterator
	 * @return - retruns the next element of the list, if present else, returns null
	 */
	private static long next(ListIterator<Long> it1) {
		return it1.hasNext() ?  it1.next() : 0;
	}
	

    static Num subtract(Num a, Num b) {
    	long borrow = 0;
	   	long difference = 0;
	   	ListIterator<Long> it1 = a.num1.listIterator();
	   	ListIterator<Long> it2 = b.num1.listIterator();
	   	Num z = new Num();
	   	while(it1.hasNext() || it2.hasNext() ){
	   		difference = next(it1) - next(it2) - borrow ;
	   		if(difference < 0){
	   			borrow = 1;
	   			difference += a.base;
	   		}
	   		else borrow=0;
	   		z.outList.add(difference);
	   		
	   		//borrow = difference / a.base;
	   		System.out.println("diff "+ difference+ " borrow "+ borrow);
	   		
	   	}
	   if (borrow > 0)
		   z.outList.add(borrow);
	   
		return z;
    }

    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
	return null;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
	return null;
    }
    /* End of Level 1 */

    /* Start of Level 2 */
    static Num divide(Num a, Num b) {
	return null;
    }

    static Num mod(Num a, Num b) {
	return null;
    }

    // Use divide and conquer
    static Num power(Num a, Num n) {
	return null;
    }

    static Num squareRoot(Num a) {
	return null;
    }
    /* End of Level 2 */


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
	return 0;
    }
    
    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    void printList() {
    	for(String i:num) {
    		System.out.print(i+" ");
    	}
    }
    
    // Return number to a string in base 10
    public String toString() {
	return null;
    }

    public long base() { return base; }
}