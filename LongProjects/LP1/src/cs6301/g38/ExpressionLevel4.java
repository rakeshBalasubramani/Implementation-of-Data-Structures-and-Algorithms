package cs6301.g38;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description This class is used to store the input with line numbers and the
 *              code.
 */
class Program {
	/**
	 * @param inputLine
	 *            - The input line.
	 */
	Program(String[] inputLine) {
		if (checkLineNo(inputLine[0])) {
			lineNo = Long.parseLong(inputLine[0]);
			code = Arrays.copyOfRange(inputLine, 1, inputLine.length);
		} else {
			lineNo = -1;
			code = inputLine;
		}

	}

	/**
	 * Function to check if the current line has line number.
	 * 
	 * @param currentLine
	 *            - The current line.
	 * @return - True if it contains line number else false.
	 */
	private boolean checkLineNo(String currentLine) {
		return currentLine.matches("\\d+");
	}

	/**
	 * Individual lines of the input.
	 */
	String[] code;
	/**
	 * Line number of each line.
	 */
	long lineNo;
}

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description This class is used to perform the execution of each line from
 *              the input.
 */
public class ExpressionLevel4 {
	String questionMark = "?";
	String collin = ":";
	long base;

	/**
	 * Function to set the base of the numbers.
	 * 
	 * @param base
	 *            - The value passed as the base that needs to be used as the base
	 *            of all numbers in this class.
	 */
	public void setBase(long base) {
		e.setBase(base);
		this.base = base;
	}

	/**
	 * Linkedlist of program objects to store each line of the input.
	 */
	LinkedList<Program> program = new LinkedList<Program>();
	Expression e = new Expression();

	/**
	 * Function to add the input to the list.
	 * 
	 * @param input
	 *            - The input line.
	 */
	public void setProgram(String[] input) {
		program.add(new Program(input));
		e.setBase(base);
	}

	/**
	 * The function that executes the lines stored in the program list.
	 */
	public void execute() {
		assignLineNumbers();
		String temp[];
		for (int pc = 0; pc < program.size(); pc++) {
			e.print=true;
			if (program.get(pc).code.length > 2 && !program.get(pc).code[1].equals(questionMark)) {
				temp=program.get(pc).code;
				if (checkInfix(program.get(pc).code)) {
					e.print=false;
					temp = shuntingYardAlgo(program.get(pc).code);
				}
				e.eval(temp);
			} else if (program.get(pc).code.length > 2 && program.get(pc).code[1].equals(questionMark)) {
				if (e.variables.get(program.get(pc).code[0]).compareTo(new Num(0)) != 0) {
					pc = Integer.parseInt(program.get(pc).code[2]) - 1;
				} else if (program.get(pc).code.length > 3 && program.get(pc).code[3].equals(collin)) {
					pc = Integer.parseInt(program.get(pc).code[4]) - 1;
				}
			} else {
				e.eval(program.get(pc).code);
			}
		}
	}

	/**
	 * Function to check if the expression is in infix or postfix.
	 * 
	 * @param expression 
	 *            - Given expression
	 * @return - True if expression is in infix else false.
	 */
	private boolean checkInfix(String[] expression) {
		String operatorList = "/*-+^%|()";
		int j = -1;
		for (int i = 0; i < expression.length; i++) {
			if (expression[i].equals("=")) {
				j = i;
				break;
			}
		}
		if (expression.length > j + 2) {
			if (!operatorList.contains(expression[j + 1]) && !operatorList.contains(expression[j + 2]))
				return false;
			else
				return true;
		} else {
			return true;
		}
	}

	/**
	 * Function to assign line numbers for the program.
	 */
	public void assignLineNumbers() {

		for (Program p : program) {
			if (p.code.length > 1 && p.code[1].equals(questionMark)) {
				p.code[2] = getLineNo(p.code[2]);
				if (p.code.length > 3 && p.code[3].equals(collin)) {
					p.code[4] = getLineNo(p.code[4]);
				}
			}
		}
	}

	/**
	 * Function to get the line number of the given line.
	 * 
	 * @param line
	 *            - The given line.
	 * @return - The line number of the given line.
	 */
	private String getLineNo(String line) {
		for (Program p : program) {
			if (p.lineNo == Long.parseLong(line)) {
				return Integer.toString(program.indexOf(p));
			}
		}
		return null;
	}

	/**
	 * Function to print the contents of the list that was last processed.
	 */
	public void end() {
		e.end();
	}

	/**
	 * Function to convert the infix expression to postfix expression.
	 * 
	 * @param exp
	 *            - The expression given in the input.
	 * @return - The resultant postfix expression.
	 */
	private String[] shuntingYardAlgo(String[] exp) {
		ArrayDeque<String> operatorStack = new ArrayDeque<String>(); // Operator Stack
		ArrayDeque<String> outputQueue = new ArrayDeque<String>(); // Output Queue
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		operatorList.add("*");
		operatorList.add("/");
		operatorList.add("%");
		operatorList.add(")");
		operatorList.add("(");
		operatorList.add("^");
		operatorList.add("|");
		int j = -1, op = 0;
		for (int i = 0; i < exp.length; i++) {
			if (exp[i].equals("=")) {
				j = i;
				break;
			}
		}
		for (int i = j + 1; i < exp.length; i++) {
			if (!operatorList.contains(exp[i])) { // Add operands directly to the output queue.
				outputQueue.add(exp[i]);
			} else {
				if (operatorStack.isEmpty()) { // If the stack is empty add the operator directly to the stack.
					operatorStack.push(exp[i]);
				} else if (exp[i].equals("(")) {
					;// If the current char is '(' add it to the stack.
					operatorStack.push(exp[i]);
				} else if (exp[i].equals(")")) {
					op++;// If the current char is ')' pop all the characters from the stack to the
							// output queue until a '('
					while (!operatorStack.peek().equals("(")) {
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
					while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")
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
		String[] temp = new String[exp.length - 2 * op];
		for (int p = 0; p <= j; p++) {
			temp[p] = exp[p];
		}
		int i = j + 1;
		while (!outputQueue.isEmpty())
			temp[i++] = outputQueue.removeFirst();
		return temp;
	}

	/**
	 * Returns the precedence of the given operator.
	 * 
	 * @param i
	 *            - Operator
	 * @return - Precedence of the given operator.
	 */
	private static int getPrecedence(String i) {
		switch (i) {
		case "+":
		case "-":
			return 0;
		case "*":
		case "/":
		case "%":
			return 1;
		case "^":
			return 2;
		case "|":
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
	private static String getAssociativity(String i) {
		if (i == "+" || i == "-" || i == "*" || i == "/" || i == "%")
			return "left";
		else if (i == "^")
			return "right";
		else
			return null;
	}
}
