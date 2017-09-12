
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g38;

import java.util.LinkedList;

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
    }
    public void toDecimal() {
    	int p=0;
    	for(String i:num) {
    		Long.parseLong(i);
    	}
    }
    static Num add(Num a, Num b) {
	return null;
    }

    static Num subtract(Num a, Num b) {
	return null;
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
