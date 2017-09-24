// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
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
 */

//1.divisor (if numerator size is small than deno case.
public class Num implements Comparable<Num> {

	private static long defaultBase = 10; // This can be changed to what you
											// want it to
	// be.
	private long base; // Change as needed

	private boolean negativeSignBit = false; // false if positive , else
												// negative.

	public boolean isNegative() {
		return negativeSignBit;
	}

	public void setNegativeSignBit(boolean signBit) {
		this.negativeSignBit = signBit;
	}

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
		parseInputString(s);
	}

	private void parseInputString(String s) {	
		if(s.charAt(0)=='-') {
			negativeSignBit=true;
			s=s.substring(1);
		}
		else {
			negativeSignBit=false;
		}
		boolean isLeadingZero = true;
		String[] temp = s.split("");
		String zeroString = "0";
		int zeroLength= 0;
		for (String i : temp) {
			if (isLeadingZero && i.equals(zeroString) && temp.length!=1) {
				zeroLength++;
				continue;
			}
			isLeadingZero = false;
			num.addFirst(Long.parseLong(i));
		}
		
		if(num.size()==0 && zeroLength==temp.length)
		{
			num.add(0L);
		}
	}

	public Num(long x) {
		base = defaultBase;
		String s = Long.toString(x);
		parseInputString(s);
	}
	
	private Num() {
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
		if(base == 10)
		{
			return;
		}
		long p = 0;
		Num temp;
		Num decimal = new Num();
		decimal.setBase(10);
		Num tBase = new Num(base);
		for (Long i : num) {
			temp = product(new Num(i),Num.power(tBase, p++));
			temp.setBase(10);
			decimal = add(decimal, temp);
		}
		num.clear();
		for (Long i : decimal.num)
		{
			num.add(i);
		}
		base = 10;
	}
	
	
	public static Num add(Num a,Num b) {
		Num result = new Num();
		result.base=a.base;
		if(a.isNegative()==b.isNegative()) {
			result.addition(a, b);
			result.setNegativeSignBit(a.negativeSignBit);
		}
		else {
			if(a.compareTo(b)==1) {
				result.subtraction(a, b);
				result.setNegativeSignBit(a.negativeSignBit);
			}
			else if(a.compareTo(b)==-1) {
				result.subtraction(b,a);
				result.setNegativeSignBit(b.negativeSignBit);
			}
		}
		
		removeLeadingZeros(result.num);
		return result;
	}
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
	 * @return - returns the next element of the list, if present else, returns
	 *         null
	 */
	private static long next(ListIterator<Long> it1) {
		return it1.hasNext() ? it1.next() : 0;
	}
	public static Num subtract(Num a, Num b) {
		Num result = new Num();
		result.base=a.base;
		if((a.isNegative()==b.isNegative())) {
			result.subtraction(a, b);
			if(a.compareTo(b)>=0) {
				result.setNegativeSignBit(a.negativeSignBit);
			}
			else {
				result.setNegativeSignBit(!b.negativeSignBit);
			}
		}
		else if(b.negativeSignBit) {
			result.addition(a, b);
			result.negativeSignBit=false;
		}
		else {
			result.addition(a, b);
			result.negativeSignBit=true;
		}
		return result;
	}
	private void subtraction(Num a, Num b) {
		if(a.compareTo(b) < 0)
		{
			Num temp = a;
			a=b;
			b= temp;
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

	// Implement Karatsuba algorithm
	public static Num product(Num a, Num b) {

		Num al = new Num();
		Num ah = new Num();
		Num bl = new Num();
		Num bh = new Num();

		if (b.num.size() > a.num.size()) {
			boolean setb=a.negativeSignBit;
			boolean seta= b.negativeSignBit;
			
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

		ah.setBase(a.getBase());
		al.setBase(a.getBase());
		bl.setBase(b.getBase());
		bh.setBase(b.getBase());
		
		if(a.negativeSignBit)
		{
			al.setNegativeSignBit(true);
			ah.setNegativeSignBit(true);
		}
		
		if(b.negativeSignBit)
		{
			bl.setNegativeSignBit(true);
			bh.setNegativeSignBit(true);
		}
		
		
		Iterator<Long> iteratora=a.num.iterator();
		Iterator<Long> iteratorb=b.num.iterator();
		int count=0;

		while(iteratora.hasNext() && iteratorb.hasNext() && count<k)
		{
			al.num.add(iteratora.next());
			bl.num.add(iteratorb.next());
			count++;
			
		}
		
//		for (int i = 0; i < k; i++) {
//			al.num.add(a.num.get(i));
//			bl.num.add(b.num.get(i));
//		}
		
		while(iteratora.hasNext())
		{
			ah.num.add(iteratora.next());
		}

//		for (int j = k; j < b.num.size(); j++) {
//			bh.num.add(b.num.get(j));
//		}

		while(iteratorb.hasNext())
		{
			bh.num.add(iteratorb.next());
		}
//		for (int j = k; j < a.num.size(); j++) {
//			ah.num.add(a.num.get(j));
//		}

//		System.out.println();
		Num prod1 = product(ah, bh);
//		System.out.println("prod1");
//		prod1.printList();
//		System.out.println();
//
		Num prod2 = product(al, bl);
//		System.out.println("prod2");
//		prod2.printList();
//		System.out.println();
//
		Num prod3 = product(add(al, ah), add(bl, bh));
//		System.out.println("prod3");
//		prod3.printList();
//		System.out.println();
//
		Num sub1 = subtract(prod3, prod1);
//		System.out.println("sub 1");
//		sub1.printList();
//		System.out.println();
//
		Num sub2 = subtract(sub1, prod2);
//		System.out.println("sub 2");
//		sub2.printList();
//		System.out.println();
//
		Num shift1 = shift(prod1, 2 * k);
//		System.out.println("shifting prod1");
//		shift1.printList();
//		System.out.println();
//
		Num shift2 = shift(sub2, k);
//		System.out.println("shifting prod2");
//		shift2.printList();
//		System.out.println();
//
		Num add1 = add(shift1, shift2);
//		System.out.println("add 1");
//		add1.printList();
//		System.out.println();
//
		Num add2 = add(add1, prod2);
//		System.out.println("add 2");
//		add2.printList();
//		System.out.println();

		if(a.negativeSignBit!=b.negativeSignBit) {
			add2.setNegativeSignBit(true);
		}
		 return add2;
		//return add(add(shift(prod1, 2 * k), shift(subtract(subtract(prod3, prod1), prod2), k)), prod2);
	}

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

		if(a.negativeSignBit!=b.negativeSignBit) {
			res.setNegativeSignBit(true);
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
			Num temp = power(a, n / 2);
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
		Num zeroNum = new Num(0);
		zeroNum.setBase(a.base);
		if(b.compareTo(zeroNum)==0)
		{
			throw new IllegalArgumentException("Divisor is 0");
		}
		Num first = new Num(0);
		first.setBase(a.base);
		Num last = new Num();
		last.base=a.base;
		for(Long i :a.num) {
			last.num.add(i);
		}
		last=add(last,new Num(1));// To handle integers.
		Num med = rightShift(add(first,last));
		while(!((product(med,b).compareTo(a)<=0)&&(a.compareTo(product(add(med,new Num(1)),b))<0))) {
			if(first.compareTo(last)>=0) {
				return new Num(0);
			}
			else {
				if (product(med, b).compareTo(a) < 0) {
					first = med;
					med = rightShift(add(first, last));

				} else {
					last = med;
					med = rightShift(add(first, last));
				}
			}
		}
		if(a.negativeSignBit!=b.negativeSignBit) {
			med.negativeSignBit=true;
		}
		return med;
	}

	private static Num rightShift(Num x) {
//		long tempBase = x.base;
//		x.toDecimal();
//		x.setBase(2);
//		x.num.remove();
//		x.toDecimal();
//		x.setBase(tempBase);
//		return x;
		Timer timer = new Timer();
		Num t2 = new Num(2);
		t2.setBase(x.base);
		Num t1 = new Num(1);
		t1.setBase(x.base);
		Num temp;
		if(x.base%2==1) {
			Num tempX = new Num();
			tempX.setBase(x.base);
			for(Long i :x.num) {
				tempX.num.add(i);
			}
			temp = new Num(0);
			temp.setBase(x.base);
			while(tempX.compareTo(t2)>0) {
				tempX=subtract(tempX,t2);
				temp=add(temp,t1);
			}
		}
		else {
			Num tb = new Num(x.base/2);
			tb.setBase(x.base);
			temp = Num.product(x,tb);
			temp.num.removeFirst();		
		}
		timer.end();
		System.out.println(timer);
		return temp;
	}

	public static Num mod(Num a, Num b) {

		if(b.negativeSignBit)
		{
			throw new ArithmeticException("b should be positive");
		}
		if(a.negativeSignBit)
		{
			Num prod=product(a,b);
			prod.printList();
			return subtract(b,subtract(a, product(divide(a, b), b)));
		}
		else
		{
			return subtract(a, product(divide(a, b), b));
		}
		
	}
	
	// Use divide and conquer
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

	private long shift() {
		return num.removeFirst();
	}

	public static Num squareRoot(Num a) {
		Num first = new Num(1);
		first.setBase(a.base);
		Num last = new Num();
		last.base=a.base;
		for(Long i :a.num) {
			last.num.add(i);
		}
		Num med = rightShift(add(first,last));
		while (!(power(med, 2).compareTo(a) <= 0 && a.compareTo(power(add(med, new Num(1)), 2)) < 0)) {
			if (power(med, 2).compareTo(a) < 0) {
				first = med;
				med = rightShift(add(first, last));

			} else {
				last = med;
				med = rightShift(add(first, last));
			}
		}
		return med;
		
	}
	/* End of Level 2 */

	// Utility functions
	// compare "this" to "other": return +1 if this is greater, 0 if equal, -1
	// otherwise
	public int compareTo(Num other) {
		if (this.num.size() > other.num.size()) {
			return 1;
		} else if (this.num.size() == other.num.size()) {
			ListIterator<Long> iterator1 = num.listIterator(num.size());
			ListIterator<Long> iterator2 = other.num.listIterator(other.num.size());
			long temp,temp1;
			while(iterator1.hasPrevious())
			{
				temp=iterator1.previous();
				temp1=iterator2.previous();
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

	// Output using the format "base: elements of list ..."
	// For example, if base=100, and the number stored corresponds to 10965,
	// then the output is "100: 65 9 1"				// I have a doubt with this output 
	public void printList() {
		System.out.print(base + ": ");
		for (Long i : num) {
			System.out.print(i + " ");
		}
		if(isNegative())
		{
			System.out.print("Negative number");
		}
		System.out.println();
	}

	// Return number to a string in base 10
	public String toString() {
		String zero="0";
		Num temp = new Num();
		temp.base=base;
		for(Long i : num) {
			temp.num.add(i);
		}
		temp.toDecimal();
		temp.setNegativeSignBit(negativeSignBit);
		StringBuilder strBuild = new StringBuilder();
		if(num.size()==0) {
			strBuild.insert(0, 0);
		}
		for (Long i : temp.num)
			strBuild.insert(0, i);
		if(temp.negativeSignBit&&!strBuild.equals(zero)) {
			strBuild.insert(0,"-");
		}
		return strBuild.toString();
	}


}