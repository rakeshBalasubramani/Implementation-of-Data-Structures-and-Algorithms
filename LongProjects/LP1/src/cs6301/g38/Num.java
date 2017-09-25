package cs6301.g38;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description This class is used to store and perform arithmetic operations on
 *              arbitrarily large integers
 */
public class Num implements Comparable<Num> {

	private static long defaultBase = 10; // This can be changed to what you
											// want it to
	// be.
	private long base; // Change as needed

	private boolean negativeSignBit = false; // false if positive , else
												// negative.

	/**
	 * Function to check if a number is positive or negative.
	 * 
	 * @return - True if negative else positive.
	 */
	public boolean isNegative() {
		return negativeSignBit;
	}

	/**
	 * Function to set the sign bit of a number.
	 * 
	 * @param signBit
	 *            - Parameter used to set the negativeSignBit of a number.
	 */
	public void setNegativeSignBit(boolean signBit) {
		this.negativeSignBit = signBit;
	}

	/**
	 * Function that returns the current base of a number.
	 * 
	 * @return - The current base in which the number is stored.
	 */
	public long getBase() {
		return base;
	}

	/**
	 * Function to set the base of a number.
	 * 
	 * @param base
	 *            - Parameter used to set the base of a number.
	 */
	public void setBase(long base) {
		this.base = base;
		toBase();
	}

	/**
	 * The list in which the number is stored. For example the number 1234 in base
	 * 10 is stored as 4->3->2->1.
	 */
	private LinkedList<Long> num = new LinkedList<>();

	/**
	 * Constructor to set the base and num list.
	 * 
	 * @param s
	 *            - Parameter used to set the num list.
	 */
	public Num(String s) {
		base = defaultBase;
		parseInputString(s);
	}

	/**
	 * Constructor to set the base and store the number.
	 * 
	 * @param s
	 *            - Parameter used to set the num list.
	 * @param b
	 *            - Parameter used to store the number's base.
	 */
	public Num(String s, long b) {
		parseInputString(s);
		setBase(b);
	}

	/**
	 * Function to set the negativeSignBit and to remove the leading zeros from the
	 * given string.
	 * 
	 * @param s
	 *            - The given number.
	 */
	private void parseInputString(String s) {
		if (s.charAt(0) == '-') {
			negativeSignBit = true;
			s = s.substring(1);
		} else {
			negativeSignBit = false;
		}
		boolean isLeadingZero = true;
		String[] temp = s.split("");
		String zeroString = "0";
		int zeroLength = 0;
		for (String i : temp) {
			if (isLeadingZero && i.equals(zeroString) && temp.length != 1) {
				zeroLength++;
				continue;
			}
			isLeadingZero = false;
			num.addFirst(Long.parseLong(i));
		}

		if (num.size() == 0 && zeroLength == temp.length) {
			num.add(0L);
		}
	}

	/**
	 * Constructor to get and store the number.
	 * 
	 * @param x
	 *            - The number that needs to be stored.
	 */
	public Num(long x) {
		base = defaultBase;
		String s = Long.toString(x);
		parseInputString(s);
	}

	/**
	 * Constructor
	 */
	private Num() {
		base = defaultBase;
	}

	/**
	 * Function to convert the base of a number from base 10 to the given base.
	 */
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

	/**
	 * Function to convert the number in base n to decimal.
	 */
	public void toDecimal() {
		if (base == 10) {
			return;
		}
		long p = 0;
		Num temp;
		Num decimal = new Num();
		decimal.setBase(10);
		Num tBase = new Num(base);
		for (Long i : num) {
			temp = product(new Num(i), Num.power(tBase, p++));
			temp.setBase(10);
			decimal = add(decimal, temp);
		}
		num.clear();
		for (Long i : decimal.num) {
			num.add(i);
		}
		base = 10;
	}

	/**
	 * The helper function to perform addition of two Num objects.
	 * 
	 * @param a
	 *            - Operand one.
	 * @param b
	 *            - Operand two.
	 * @return - The sum of operand one and operand two.
	 */
	public static Num add(Num a, Num b) {
		Num result = new Num();
		result.base = a.base;
		if (a.isNegative() == b.isNegative()) {
			result.addition(a, b);
			result.setNegativeSignBit(a.negativeSignBit);
		} else {
			if (a.compareTo(b) == 1) {
				result.subtraction(a, b);
				result.setNegativeSignBit(a.negativeSignBit);
			} else if (a.compareTo(b) == -1) {
				result.subtraction(b, a);
				result.setNegativeSignBit(b.negativeSignBit);
			}
		}

		removeLeadingZeros(result.num);// For product case;
		return result;
	}

	/**
	 * Function to perform the addition of two Num objects
	 * 
	 * @param a
	 *            - Operand one.
	 * @param b
	 *            - Operand two.
	 */
	public void addition(Num a, Num b) {
		long carry = 0;
		long sum = 0;
		ListIterator<Long> it1 = a.num.listIterator();
		ListIterator<Long> it2 = b.num.listIterator();
		while (it1.hasNext() || it2.hasNext() || carry > 0) {
			sum = next(it1) + next(it2) + carry;
			num.add(sum % a.base);
			carry = sum / a.base;
		}
	}

	/**
	 * Method : helper function to check whether list has next element
	 * 
	 * @param it1
	 *            - List iterator
	 * @return - returns the next element of the list, if present else, returns 0
	 */
	private static long next(ListIterator<Long> it1) {
		return it1.hasNext() ? it1.next() : 0;
	}

	/**
	 * The helper function to perform subtraction of two Num objects.
	 * 
	 * @param a
	 *            - Operand one.
	 * @param b
	 *            - Operand two.
	 * @return - Difference of the two numbers a-b.
	 */
	public static Num subtract(Num a, Num b) {
		Num result = new Num();
		result.base = a.base;
		if ((a.isNegative() == b.isNegative())) {
			result.subtraction(a, b);
			if (a.compareTo(b) >= 0) {
				result.setNegativeSignBit(a.negativeSignBit);
			} else {
				result.setNegativeSignBit(!b.negativeSignBit);
			}
		} else if (b.negativeSignBit) {
			result.addition(a, b);
			result.negativeSignBit = false;
		} else {
			result.addition(a, b);
			result.negativeSignBit = true;
		}
		return result;
	}

	/**
	 * Function to perform the subtraction of two Num objects.
	 * 
	 * @param a
	 *            - Operand one.
	 * @param b
	 *            - Operand two.
	 */
	private void subtraction(Num a, Num b) {
		if (a.compareTo(b) < 0) {
			Num temp = a;
			a = b;
			b = temp;
		}
		long borrow = 0;
		long difference = 0;
		ListIterator<Long> it1 = a.num.listIterator();
		ListIterator<Long> it2 = b.num.listIterator();
		base = a.base;
		while (it1.hasNext() || it2.hasNext()) {
			difference = next(it1) - next(it2) - borrow;
			if (difference < 0) {
				borrow = 1;
				difference += a.base;
			} else {
				borrow = 0;
			}
			num.add(difference);

		}
		if (borrow > 0)
			num.addFirst(borrow);
		removeLeadingZeros(num);
	}

	/**
	 * Function to remove the leading zeros.
	 * 
	 * @param num
	 *            - The list in which the number is stored.
	 */
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

	/**
	 * The helper function to perform the product of two Num objects.
	 * 
	 * @param a
	 *            - Operand one.
	 * @param b
	 *            - Operand two.
	 * @return - The product of two numbers operand one and operand two.
	 */
	public static Num product(Num a, Num b) {

		Num al = new Num();
		Num ah = new Num();
		Num bl = new Num();
		Num bh = new Num();

		if (b.num.size() > a.num.size()) {
			boolean setb = a.negativeSignBit;
			boolean seta = b.negativeSignBit;

			Num temp = a;
			a = b;
			b = temp;

			a.setNegativeSignBit(seta);
			b.setNegativeSignBit(setb);

		}

		if (b.num.size() == 0) {
			return new Num();
		}

		if (b.num.size() == 1) {
			return multiply(a, b);
		}

		int k = (b.num.size() / 2);

		ah.base = a.getBase();
		al.base = a.getBase();
		bl.base = b.getBase();
		bh.base = b.getBase();

		if (a.negativeSignBit) {
			al.setNegativeSignBit(true);
			ah.setNegativeSignBit(true);
		}

		if (b.negativeSignBit) {
			bl.setNegativeSignBit(true);
			bh.setNegativeSignBit(true);
		}

		Iterator<Long> iteratora = a.num.iterator();
		Iterator<Long> iteratorb = b.num.iterator();
		int count = 0;

		while (iteratora.hasNext() && iteratorb.hasNext() && count < k) {
			al.num.add(iteratora.next());
			bl.num.add(iteratorb.next());
			count++;

		}
		while (iteratora.hasNext()) {
			ah.num.add(iteratora.next());
		}
		while (iteratorb.hasNext()) {
			bh.num.add(iteratorb.next());
		}
		Num prod1 = product(ah, bh);
		Num prod2 = product(al, bl);
		Num prod3 = product(add(al, ah), add(bl, bh));
		Num sub1 = subtract(prod3, prod1);
		Num sub2 = subtract(sub1, prod2);
		Num shift1 = shift(prod1, 2 * k);
		Num shift2 = shift(sub2, k);
		Num add1 = add(shift1, shift2);
		Num add2 = add(add1, prod2);
		if (a.negativeSignBit != b.negativeSignBit) {
			add2.setNegativeSignBit(true);
		}
		return add2;
	}

	/**
	 * Function to perform product of two numbers.
	 * 
	 * @param a
	 *            - Operand one.
	 * @param b
	 *            - Operand two.
	 * @return - Product of a and b.
	 */
	private static Num multiply(Num a, Num b) {

		Num res = new Num();
		res.setBase(a.base);
		long prod;
		long carry = 0L;
		ListIterator<Long> it1 = a.num.listIterator();
		while (it1.hasNext()) {
			prod = next(it1) * b.num.getFirst();
			prod += carry;
			carry = prod / a.getBase();
			res.num.add(prod % (a.getBase()));
		}

		if (carry != 0) {
			res.num.add(carry);
		}

		if (a.negativeSignBit != b.negativeSignBit) {
			res.setNegativeSignBit(true);
		}
		return res;
	}

	/**
	 * Performs the shift operation.
	 * 
	 * @param prod
	 *            - The given number object on which the shift operation is
	 *            performed.
	 * @param bits
	 *            - The number of bits needs to be shifted.
	 * @return - The number after the shift operation has been completed.
	 */
	private static Num shift(Num prod, int bits) {

		for (int i = 0; i < bits; i++) {
			prod.num.addFirst(0L);
		}

		return prod;
	}

	/**
	 * Function to perform exponent calculation.
	 * 
	 * @param a
	 *            - Base
	 * @param n
	 *            - Exponent.
	 * @return - The result of a to the power n here n is of long data type.
	 */
	public static Num power(Num a, long n) {

		if (n == 0) {
			return new Num(1);
		} else if (n == 1) {
			return a;
		} else {
			Num temp = power(a, n / 2);
			if (n % 2 == 0) {
				return product(temp, temp);
			} else {
				return product(product(temp, temp), a);
			}
		}
	}

	/**
	 * Function to perform right shift or division by the base operation.
	 * 
	 * @param x
	 *            - The number on which the operation is performed.
	 * @return - The result of the operation
	 */
	private static Num rightShift(Num x) {
		Num result = Num.product(x, new Num(5));
		result.num.removeFirst();
		return result;
	}

	/**
	 * Function to perform the division of two numbers.
	 * 
	 * @param a
	 *            - Dividend
	 * @param b
	 *            - Divisor
	 * @return - The quotient when a is divided by b. (b!=0).
	 */
	public static Num divide(Num a, Num b) {
		Num tempa = new Num();
		Num tempb = new Num();
		for (Long i : a.num) {
			tempa.num.add(i);
		}
		for (Long i : b.num) {
			tempb.num.add(i);
		}
		tempa.base = a.base;
		tempb.base = b.base;
		tempa.toDecimal();
		tempb.toDecimal();
		Num first = new Num(0);
		Num last = new Num();
		for (Long i : tempa.num) {
			last.num.add(i);
		}
		last = add(last, new Num(1));
		Num med = rightShift(add(first, last));
		while (!((product(med, tempb).compareTo(tempa) <= 0)
				&& (tempa.compareTo(product(add(med, new Num(1)), tempb)) < 0))) {
			if (first.compareTo(last) >= 0) {
				return new Num(0);
			} else {
				if (product(med, tempb).compareTo(tempa) < 0) {
					first = med;
					med = rightShift(add(first, last));

				} else {
					last = med;
					med = rightShift(add(first, last));
				}
			}
		}
		med.setBase(a.base);
		if (a.negativeSignBit != b.negativeSignBit) {
			med.negativeSignBit = true;
		}
		return med;
	}

	/**
	 * Function to perform the modulo operation.
	 * 
	 * @param a
	 *            - Dividend
	 * @param b
	 *            - Divisor
	 * @return - The remainder when a is divided by b. (b>0).
	 */
	public static Num mod(Num a, Num b) {

		if (b.negativeSignBit) {
			throw new ArithmeticException("b should be positive");
		}
		if (a.negativeSignBit) {
			Num prod = product(a, b);
			prod.printList();
			return subtract(b, subtract(a, product(divide(a, b), b)));
		} else {
			return subtract(a, product(divide(a, b), b));
		}

	}

	/**
	 * Function to perform exponent calculation when both base and exponent are Num
	 * objects.
	 * 
	 * @param a
	 *            - Base.
	 * @param n
	 *            - Exponent.
	 * @return - The result of a to the power of n.
	 */
	public static Num power(Num a, Num n) {
		if (n.compareTo(new Num(1)) == 0) {
			return a;
		} else if (n.compareTo(new Num(0)) == 0) {
			return new Num(1);
		}

		if (n.num.size() == 1) {
			return power(a, n.num.get(0));
		}
		Num tempn = new Num();
		tempn.base = n.base;
		for (Long i : n.num) {
			tempn.num.add(i);
		}
		long s = tempn.shift();
		return product(power(power(a, tempn), n.base), power(a, s));
	}

	/**
	 * Function to get the items of the exponent one by one.
	 * 
	 * @return - The number after the first digit is removed.
	 */
	private long shift() {
		return num.removeFirst();
	}

	/**
	 * Function to the perform the square root operation.
	 * 
	 * @param a
	 *            - The given number.
	 * @return - The closest possible square root of the given number.
	 */
	public static Num squareRoot(Num a) {
		Num tempa = new Num();
		for (Long i : a.num) {
			tempa.num.add(i);
		}
		tempa.base = a.base;
		tempa.toDecimal();
		Num first = new Num(0);
		Num last = new Num();
		for (Long i : tempa.num) {
			last.num.add(i);
		}
		last = add(last, new Num(1));
		Num med = rightShift(add(first, last));
		while (!(power(med, 2).compareTo(tempa) <= 0 && tempa.compareTo(power(add(med, new Num(1)), 2)) < 0)) {
			if (power(med, 2).compareTo(tempa) < 0) {
				first = med;
				med = rightShift(add(first, last));

			} else {
				last = med;
				med = rightShift(add(first, last));
			}
		}
		med.setBase(a.base);
		return med;

	}

	/**
	 * Function to compare two num objects
	 * 
	 * @param a
	 *            - The other number with which it has to be compared.
	 * @return - 0 if equal, 1 if the other number is lesser else -1..
	 */
	public int compareTo(Num other) {
		if (this.num.size() > other.num.size()) {
			return 1;
		} else if (this.num.size() == other.num.size()) {
			ListIterator<Long> iterator1 = num.listIterator(num.size());
			ListIterator<Long> iterator2 = other.num.listIterator(other.num.size());
			long temp, temp1;
			while (iterator1.hasPrevious()) {
				temp = iterator1.previous();
				temp1 = iterator2.previous();
				if (temp > temp1) {
					return 1;
				} else if (temp < temp1) {
					return -1;
				}
			}
			return 0;
		}
		return -1;
	}

	/**
	 * Function to print the number as it stored in the list.
	 */
	public void printList() {
		System.out.print(base + ": ");
		for (Long i : num) {
			System.out.print(i + " ");
		}
		if (isNegative()) {
			System.out.print("Negative number");
		}
		System.out.println();
	}

	/**
	 * Function to print the number in base 10.
	 */
	public String toString() {
		Num temp = new Num();
		temp.base = base;
		for (Long i : num) {
			temp.num.add(i);
		}
		temp.toDecimal();
		temp.setNegativeSignBit(negativeSignBit);
		StringBuilder strBuild = new StringBuilder();
		if (num.size() == 0) {
			strBuild.insert(0, 0);
		}
		for (Long i : temp.num)
			strBuild.insert(0, i);
		if (temp.negativeSignBit && temp.compareTo(new Num(0))!=0) {
			strBuild.insert(0, "-");
		}
		return strBuild.toString();
	}

}