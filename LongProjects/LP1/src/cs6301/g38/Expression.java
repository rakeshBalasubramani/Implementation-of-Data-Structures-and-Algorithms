package cs6301.g38;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description This class is used to perform the execution of each line from
 *              the input.
 */
public class Expression {
	String[] exp;
	long base;

	/**
	 * Function to set the base of the numbers used in this operation.
	 * 
	 * @param base
	 *            - Used to set the base of the numbers.
	 */
	public void setBase(long base) {
		this.base = base;
	}

	/**
	 * A hashmap used to store the variables and the corresponding values given in
	 * the iput program.
	 */
	HashMap<String, Num> variables = new HashMap<String, Num>();

	/**
	 * Function to execute each line and display the output.
	 * 
	 * @param currentLine
	 *            - The current statement that is going to be executed.
	 */
	public void eval(String[] currentLine) {
		exp = currentLine;
		String numeric = "012132456789";
		if (exp.length == 3) {
			if (numeric.indexOf(exp[2].charAt(0)) > -1) {
				variables.put(exp[0], new Num(exp[2], base));
			} else {
				variables.put(exp[0], variables.get(exp[2]));
			}

		} else if (exp.length > 3) {
			variables.put(exp[0], postFix());
		}
		System.out.println(variables.get(exp[0]));
	}

	/**
	 * Function that evaluates the postfix expression.
	 * 
	 * @return - The resultant vale of the expression.
	 */
	private Num postFix() {
		String operatorList = "+-=*/%^|";
		String numeric = "012132456789";
		Num a, b;
		String[] expTemp = Arrays.copyOfRange(exp, 2, exp.length);
		ArrayDeque<Num> stack = new ArrayDeque<>();
		for (String i : expTemp) {
			if (numeric.indexOf(i.charAt(0)) > -1) {
				stack.push(new Num(i, base));
			} else if (!operatorList.contains(i)) {
				stack.push(variables.get(i));
			} else {
				if (i.equals("+")) {
					a = stack.pop();
					b = stack.pop();
					stack.push(Num.add(b, a));
				} else if (i.equals("-")) {
					a = stack.pop();
					b = stack.pop();
					stack.push(Num.subtract(b, a));
				} else if (i.equals("*")) {
					a = stack.pop();
					b = stack.pop();
					stack.push(Num.product(b, a));
				} else if (i.equals("/")) {
					a = stack.pop();
					b = stack.pop();
					stack.push(Num.divide(b, a));
				} else if (i.equals("%")) {
					a = stack.pop();
					b = stack.pop();
					stack.push(Num.mod(b, a));
				} else if (i.equals("^")) {
					a = stack.pop();
					b = stack.pop();
					stack.push(Num.power(b, a));
				} else if (i.equals("|")) {
					stack.push(Num.squareRoot(stack.pop()));
				}
			}

		}
		return stack.pop();
	}

	/**
	 * Function to print the contents of the list once the input is done executing.
	 */
	public void end() {
		variables.get(exp[0]).printList();
	}
}
