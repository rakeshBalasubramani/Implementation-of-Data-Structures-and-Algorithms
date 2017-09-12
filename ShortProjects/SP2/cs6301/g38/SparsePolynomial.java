package cs6301.g38;

import java.util.Scanner;

import cs6301.g38.SinglyLinkedList.Entry;

/**
 * @author Avinash Venkatesh - axv165330 <br>
 * 		   HariPriyaa - hum160030 <br>
 * 		   Rakesh Balasubramani - rxb162130 <br>
 * 		   Rajkumar Panneer Selvam - rxp162130 
 *
 * @description This class contains addition, multiplication and evaluation functionality for Sparse polynomial.
 */
public class SparsePolynomial {

	/**
	 * List to store polynomials Object (value and its coefficient) from lower order to higher order.
	 */
	private SinglyLinkedList<Polynomial> polynomial = new SinglyLinkedList<Polynomial>();

	static class Polynomial {
		Integer value;
		Integer coefficient;

		Polynomial(Integer value, Integer coefficient) {
			this.value = value;
			this.coefficient = coefficient;
		}

	}

	/**Method: put a polynomial object (value and its coefficient).
	 * @param value - value to be added.
	 * @param coefficient - coefficient to be added for the given value.
	 */
	public void put(Integer value, Integer coefficient) {
		polynomial.add(new Polynomial(value, coefficient));
	}

	/**Method: Provides polynomial multiplication between this polynomial with the given polynomial. 
	 * @param otherSparePolynomial - polynomial to be multiplied with this polynomial.
	 * @return - Multiplied polynomial.
	 */
	public SparsePolynomial multiplyPolynomial(
			SparsePolynomial otherSparePolynomial) {
		if (isNull(otherSparePolynomial)) {
			return null;
		}

		SparsePolynomial temp, temp1 = null;
		for (Polynomial poly : otherSparePolynomial.polynomial) {
			temp = elementWiseMultiply(poly);
			if (!isNull(temp) && !isNull(temp1)) {
				temp1 = temp.addPolynomial(temp1);
			} else {
				temp1 = temp;
			}

		}

		return temp1;
	}

	/**Method : Multiplies an polynomial entry with a entire Sparse Polynomial.
	 * @param entry - polynomial object to be multiplied with the another entire sparsePolynomial.
	 * @return - Result sparse polynomial.
	 */
	private SparsePolynomial elementWiseMultiply(Polynomial entry) {
		SparsePolynomial elementMulPoly = new SparsePolynomial();

		Integer value = entry.value;
		Integer coefficient = entry.coefficient;

		for (Polynomial poly : polynomial) {
			Integer mulVal = value * poly.value;
			Integer mulCoeff = coefficient + poly.coefficient;
			elementMulPoly.put(mulVal, mulCoeff);
		}

		return elementMulPoly;
	}

	/**Method : checks whether given object is null or not.
	 * @param obj - object to the tested.
	 * @return - true: if null , false: otherwise.
	 */
	private boolean isNull(Object obj) {
		return obj == null;
	}


	/**Method: Provides polynomial addition between this polynomial with the given polynomial. 
	 * @param otherSparePolynomial - polynomial to be added with this polynomial.
	 * @return - Added polynomial.
	 */
	public SparsePolynomial addPolynomial(SparsePolynomial otherSparePoly) {

		if (isNull(otherSparePoly)) {
			return this;
		}

		SparsePolynomial addSparePoly = new SparsePolynomial();
		Entry<Polynomial> thisPolyEntry = polynomial.head.next;
		Entry<Polynomial> otherPolyEntry = otherSparePoly.polynomial.head.next;

		while (thisPolyEntry != null && otherPolyEntry != null) { // when both sparse polynomials has entries.
			Polynomial thisPoly = thisPolyEntry.element;
			Polynomial otherPoly = otherPolyEntry.element;
			if (thisPoly.coefficient.compareTo(otherPoly.coefficient) == 0) { // For equal coefficient case.
				Integer value = thisPoly.value + otherPoly.value;
				addSparePoly.put(value, thisPoly.coefficient);
				thisPolyEntry = thisPolyEntry.next;
				otherPolyEntry = otherPolyEntry.next;
			} else if (thisPoly.coefficient.compareTo(otherPoly.coefficient) < 0) { // lower coefficient case in this Sparse polynomial.
				addSparePoly.put(thisPoly.value, thisPoly.coefficient);
				thisPolyEntry = thisPolyEntry.next;

			} else { // lower coefficient case for the other Sparse polynomial.

				addSparePoly.put(otherPoly.value, otherPoly.coefficient);
				otherPolyEntry = otherPolyEntry.next;
			}

		}

		if (thisPolyEntry == null) { // when this sparse polynomial is empty 
									//	then copy the other sparse polynomial entries
									// to the result sparse polynomial.
			while (otherPolyEntry != null) {
				addSparePoly.put(otherPolyEntry.element.value,
						otherPolyEntry.element.coefficient);
				otherPolyEntry = otherPolyEntry.next;
			}

		} else { // For the opposite case.
			while (thisPolyEntry != null) {
				addSparePoly.put(thisPolyEntry.element.value,
						thisPolyEntry.element.coefficient);
				thisPolyEntry = thisPolyEntry.next;
			}
		}
		return addSparePoly;

	}
	

	/**Method: Provides polynomial evaluation for this polynomial. 
	 * @param x - value to be evaluated in this polynomial.
	 * @return - evaluated result .
	 */

	public long evaluatePolynomial(int x) {
		long evalValue = 0;

		for (Polynomial poly : polynomial) {
			evalValue = (long) (evalValue + (poly.value * Math.pow(x,
					poly.coefficient)));
		}

		return evalValue;
	}

	/**
	 * Prints the sparse polynomial.
	 */
	public void print() {
		for (Polynomial poly : polynomial) {
			System.out.println("Value : " + poly.value + " coefficient : "
					+ poly.coefficient);
		}
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		boolean isDone = false;
		SparsePolynomial polynomial1, polynomial2;
		do {
			System.out.println("Polynomial operations:");

			System.out
					.println("Enter 1 for Addition \nEnter 2 for Multiplication \nEnter 3 for evaluation \nEnter 4 for exit");
			int operation = in.nextInt();
			switch (operation) {
			case 1:
				polynomial1 = createPolynomial(in);
				polynomial2 = createPolynomial(in);
				SparsePolynomial polynomial3 = polynomial1
						.addPolynomial(polynomial2);
				System.out.println("Given polynomials");
				System.out.println("Polynomial1 :");
				polynomial1.print();
				System.out.println("Polynomial2 :");
				polynomial2.print();
				System.out.println(" Added Polynomial");

				polynomial3.print();

				break;
			case 2:
				polynomial1 = createPolynomial(in);
				polynomial2 = createPolynomial(in);
				SparsePolynomial polynomial4 = polynomial1
						.multiplyPolynomial(polynomial2);
				System.out.println("Given polynomials");
				System.out.println("Polynomial1 :");
				polynomial1.print();
				System.out.println("Polynomial2 :");
				polynomial2.print();
				System.out.println(" Multiplied Polynomial");

				polynomial4.print();

				break;
			case 3:
				polynomial1 = createPolynomial(in);
				System.out.println("Given polynomial");
				polynomial1.print();

				System.out
						.println(" Enter the value to evaluate given polynomial");
				int val = in.nextInt();
				System.out.println("Solution is "
						+ polynomial1.evaluatePolynomial(val));
				break;
			default:
				isDone = true;
			}

		} while (!isDone);

		in.close();

	}

	/**Method : Creates a new Sparse polynomial.
	 * @param in - Scanner input.
	 * @return - New sparse polynomial.
	 */
	private static SparsePolynomial createPolynomial(Scanner in) {
		System.out.println("No of entries for the polynomial");

		int noOfEntries = in.nextInt();
		int value, coefficient;
		System.out
				.println(" Enter from lower order to higher order coefficients");
		SparsePolynomial polynomial1 = new SparsePolynomial();
		for (int i = 0; i < noOfEntries; i++) {
			System.out.println("Enter Coefficient : ");
			coefficient = in.nextInt();
			System.out.println("Enter the Value : ");
			value = in.nextInt();
			polynomial1.put(value, coefficient);
		}

		return polynomial1;
	}

}
