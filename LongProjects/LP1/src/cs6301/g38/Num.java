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
public class Num implements Comparable<Num> {

	private static long defaultBase = 10; // This can be changed to what you want it to
									// be.
	private long base; // Change as needed

	public long getBase() {
		return base;
	}

	public void setBase(long base) {
		this.base = base;
		toBase();
	}

	private LinkedList<Long> num = new LinkedList<>();

	/* Start of Level 1 */
	public Num(String s) {
		base = defaultBase;
		String[] temp = s.split("");
		for (String i : temp) {
			num.addFirst(Long.parseLong(i));
		}
	}

	public Num(long x) {
		base = defaultBase;
		String s = Long.toString(x);
		String[] temp = s.split("");
		for (String i : temp) {
			num.addFirst(Long.parseLong(i));
		}
	}

	public Num() {
		base = defaultBase;
	}

	public void toBase() {
		LinkedList<Long> quotient = new LinkedList<Long>();
		LinkedList<Long> reminder = new LinkedList<Long>();
		while (!num.isEmpty()) {
			quotient.clear();
			int i = num.size() - 1;
			long temp = num.get(i--);
			while (i >= 0) {
				quotient.addFirst(temp / base);
				temp = temp % base;
				if (temp < base && i >= 0)
					temp = temp * 10 + num.get(i--);
			}
			quotient.addFirst(temp / base);
			num.clear();
			while (!quotient.isEmpty() && quotient.getLast() == 0)
				quotient.removeLast();
			if (!quotient.isEmpty())
				for (Long x : quotient) {
					num.add(x);
				}
			reminder.add(temp % base);
		}
		for (Long i : reminder) {
			num.add(i);
		}
	}

	public void toDecimal() {
		long p = 0;
		Num temp;
		Num decimal = new Num(0);
		decimal.setBase(10);

		for (Long i : num) {
			temp = new Num(i * (long) Math.pow(base, p++));
			temp.setBase(10);
			decimal = add(decimal, temp);
		}
		num.clear();
		for (Long i : decimal.num)
			num.add(i);
	}

	public static Num add(Num a, Num b) {

		long carry = 0;
		long sum = 0;
		ListIterator<Long> it1 = a.num.listIterator();
		ListIterator<Long> it2 = b.num.listIterator();
		Num z = new Num();
		z.setBase(a.base);
		while (it1.hasNext() || it2.hasNext() || carry > 0) {
			sum = next(it1) + next(it2) + carry;
			z.num.add(sum % a.base);
			carry = sum / a.base;

		}
		if (carry > 0)
			z.num.addFirst(carry);

		return z;
	}

	/**
	 * Method : helper function to check whether list has next element
	 * 
	 * @param it1
	 *            - List iterator
	 * @return - returns the next element of the list, if present else, returns
	 *         null
	 */
	private static long next(ListIterator<Long> it1) {
		return it1.hasNext() ? it1.next() : 0;
	
	}

	public static Num subtract(Num a, Num b) {
		long borrow = 0;
		long difference = 0;
		ListIterator<Long> it1 = a.num.listIterator();
		ListIterator<Long> it2 = b.num.listIterator();
		Num z = new Num();
		z.base=a.base;
		while (it1.hasNext() || it2.hasNext()) {
			difference = next(it1) - next(it2) - borrow;
			if (difference < 0) {
				borrow = 1;
				difference += a.base;
			} else
			{
				borrow = 0;
			}
			z.num.add(difference);

		}
		if (borrow > 0)
			z.num.addFirst(borrow);

		removeLeadingZeros(z.num);

		return z;
	}

	private static void removeLeadingZeros(LinkedList<Long> num) {

		ListIterator<Long> it = num.listIterator(num.size());
		int c = num.size();
		while (it.hasPrevious() && c != 1) {
			c--;
			if (it.previous() != 0) {
				break;
			} else {
				it.remove();
			}
		}
	}

	// Implement Karatsuba algorithm for excellence credit
	public static Num product(Num a, Num b) {

		Num al = new Num();
		Num ah = new Num();
		Num bl = new Num();
		Num bh = new Num();

		if (b.num.size() > a.num.size()) {
			Num temp = a;
			a = b;
			b = temp;
		}
		int k = b.num.size() / 2; // assuming b is smaller

		ah.setBase(a.getBase());
		al.setBase(a.getBase());
		bl.setBase(b.getBase());
		bh.setBase(b.getBase());

		for (int i = 0; i < k; i++) {
			al.num.add(a.num.get(i));
			bl.num.add(b.num.get(i));
		}

		for (int j = k; j < b.num.size(); j++) {
			bh.num.add(b.num.get(j));
		}

		for (int j = k; j < a.num.size(); j++) {
			ah.num.add(a.num.get(j));
		}

		if (k == 0) {
			return new Num();
		}

		if (k == 1) {
			return multiply(a, b);
		}

		Num prod1 = product(ah, bh);
		Num prod2 = product(al, bl);
		Num prod3 = product(add(al, ah), add(bl, bh));

		return add(add(shift(prod1, 2 * k), shift(subtract(subtract(prod3, prod1), prod2), k)), prod2);
	}

	private static Num multiply(Num a, Num b) {

		Num res = new Num();
		long prod;
		long carry = 0L;
		ListIterator<Long> it1 = a.num.listIterator();
		while (it1.hasNext()) {
			prod = next(it1) * b.num.getFirst();
			prod += carry;
			carry = prod / a.getBase();
			res.num.add(prod % (a.getBase()));
		}
		// for multiplying with msb
		prod = a.num.getLast() * b.num.getFirst();
		prod += carry;
		res.num.add(prod % (a.getBase()));
		if (prod / a.getBase() > 0) {
			res.num.add(prod / (a.getBase()));
		}

		return res;
	}

	private static Num shift(Num prod, int bits) {

		for (int i = 0; i < bits; i++) {
			prod.num.addFirst(0L);
		}

		return prod;
	}

	// Use divide and conquer
	public static Num power(Num a, long n) {

		if (n == 0) {
			return new Num(1);
		} else if (n == 1) {
			return a;
		} else {
			Num temp = power(product(a, a), n / 2);
			if (n % 2 == 0) {
				return product(temp, temp);
			} else {
				return product(product(temp, temp), a);
			}
		}
	}
	/* End of Level 1 */

	/* Start of Level 2 */
	public static Num divide(Num a, Num b) {
		return null;
	}

	public static Num mod(Num a, Num b) {
	
		return subtract(a,product(divide(a,b),b)); 
	}

	// Use divide and conquer
	public static Num power(Num a, Num n) {
		return null;
	}

	public static Num squareRoot(Num a) {
		return null;
	}
	/* End of Level 2 */

	// Utility functions
	// compare "this" to "other": return +1 if this is greater, 0 if equal, -1
	// otherwise
	public int compareTo(Num other) {
		return 0;
	}

	// Output using the format "base: elements of list ..."
	// For example, if base=100, and the number stored corresponds to 10965,
	// then the output is "100: 65 9 1"
	public void printList() {
		System.out.print(base + ": ");
		for (Long i : num) {
			System.out.print(i + " ");
		}
	}

	// Return number to a string in base 10
	public String toString() {
		long p = 0;
		Num temp;
		Num decimal = new Num(0);
		decimal.setBase(10);

		for (Long i : num) {
			temp = new Num(i * (long) Math.pow(base, p++)); // Need to use Num.pow() 
			temp.setBase(10);
			decimal = add(decimal, temp);
		}
		StringBuilder strBuild = new StringBuilder();
		
		for (Long i : decimal.num)
			strBuild.insert(0, i);
		
		return strBuild.toString();
	}

	public long base() {
		return base;
	}
}