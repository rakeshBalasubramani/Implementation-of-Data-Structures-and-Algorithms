package cs6301.g38;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Class used to implement the shunting yard algorithm.
 */
public class ShuntingYard {
	ArrayDeque<Character> operatorStack = new ArrayDeque<Character>(); // Operator Stack
	ArrayDeque<Character> outputQueue = new ArrayDeque<Character>(); // Output Queue
	ArrayList<Character> operatorList = new ArrayList<Character>(); // List of operators

	/**
	 * Method to add operators to the operator list.
	 */
	private void operatorList() {
		operatorList.add('+');
		operatorList.add('-');
		operatorList.add('*');
		operatorList.add('/');
		operatorList.add(')');
		operatorList.add('(');
		operatorList.add('!');
		operatorList.add('^');

	}

	/**
	 * Converts the infix expression to the corresponding expression.
	 * 
	 * @param -
	 *            infixExp The given infix expression.
	 * @return - The corresponding postfix expression.
	 */
	public static char[] infixToPostfix(char[] infixExp) {
		ShuntingYard shuntingYard = new ShuntingYard();
		shuntingYard.operatorList();
		shuntingYard.shuntingYardAlgo(infixExp);
		return shuntingYard.getPostfix();
	}

	/**
	 * Converts the outputQueue to a char array.
	 * 
	 * @return-The postfix expression in a char array
	 */
	private char[] getPostfix() {
		char[] exp = new char[outputQueue.size()];
		int i = 0;
		while (!outputQueue.isEmpty())
			exp[i++] = outputQueue.removeFirst();
		return exp;
	}

	/**
	 * Implements the shunting yard algorithm on the given infix expression.
	 * 
	 * @param exp
	 *            - The given infix expression.
	 * 
	 */
	private void shuntingYardAlgo(char[] exp) {
		for (int i = 0; i < exp.length; i++) {
			if (!operatorList.contains(exp[i])) { // Add operands directly to the output queue.
				outputQueue.add(exp[i]);
			} else {
				if (operatorStack.isEmpty()) { // If the stack is empty add the operator directly to the stack.
					operatorStack.push(exp[i]);
				} else if (exp[i] == '(') { // If the current char is '(' add it to the stack.
					operatorStack.push(exp[i]);
				} else if (exp[i] == ')') { // If the current char is ')' pop all the characters from the stack to the
											// output queue until a '('
					while (operatorStack.peek() != '(') {
						outputQueue.add(operatorStack.pop());
					}
					operatorStack.pop(); // Pop the '('
				} else if (getPrecedence(exp[i]) == getPrecedence(operatorStack.peek()) // If the current char and top
																						// of stack has same precedence
																						// and has left associativity
																						// pop from stack and add to
																						// queue.
						&& getAssociativity(exp[i]) == "left" && getAssociativity(operatorStack.peek()) == "left") {
					outputQueue.add(operatorStack.pop());
					operatorStack.push(exp[i]);
				} else if (getPrecedence(exp[i]) >= getPrecedence(operatorStack.peek())) {// If the current char has
																							// greater precedence than
																							// the top of the stack push
																							// the current char to the
																							// stack.
					operatorStack.push(exp[i]);
				} else {// If the current char is an operator pop until the top of the stack has lower
						// precedence than the current char.
					while (!operatorStack.isEmpty() && operatorStack.peek() != '('
							&& getPrecedence(exp[i]) <= getPrecedence(operatorStack.peek())) {
						outputQueue.add(operatorStack.pop());
					}
					operatorStack.push(exp[i]);
				}
			}
		}
		while (!operatorStack.isEmpty()) {
			outputQueue.add(operatorStack.pop());
		}
	}

	/**
	 * Returns the precedence of the given operator.
	 * 
	 * @param i
	 *            - Operator
	 * @return - Precedence of the given operator.
	 */
	private static int getPrecedence(char i) {
		switch (i) {
		case '+':
		case '-':
			return 0;
		case '*':
		case '/':
			return 1;
		case '^':
			return 2;
		case '!':
			return 3;
		default:
			return -1;
		}
	}

	/**
	 * Returns the associativity of the given operator.
	 * 
	 * @param i
	 *            - Operator
	 * @return - Associativity of the given operator.
	 */
	private static String getAssociativity(char i) {
		if (i == '+' || i == '-' || i == '*' || i == '/')
			return "left";
		else if (i == '^')
			return "right";
		else
			return null;
	}

	public static void main(String args[]) {
		Scanner in;
		in = new Scanner(System.in);
		System.out.println("Enter expression");
		String expression = in.next();
		expression = expression.replaceAll("\\s+", "");
		char[] infixExp = expression.toCharArray();
		char[] postfixExp = infixToPostfix(infixExp);
		System.out.println("Output : ");
		for (char i : postfixExp) {
			System.out.print(i);
		}
		in.close();
	}

}
