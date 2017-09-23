package cs6301.g38;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

public class ExpressionLvl4 {
	LinkedList<String[]> program = new LinkedList<String[]>();
	Expression e = new Expression();
	public void setProgram(String code) {
		program.add(code.split("\\s+"));
		execute(code);		
	}
	private void execute(String code) {
		String[] tempCode = code.split("\\s+");
		if(tempCode.length==3) {
			e.eval(tempCode);
		}
		else if(!code.contains("?")) {
			shuntingYardAlgo(tempCode);
			e.eval(tempCode);
		}
//		else {
//			if(e.variables.get(tempCode[1]).compareTo(new Num(0))!=0){
//				String[] temp;
//				for(int i=0;i<program.size();i++) {
//					if(tempCode[3]==program.get(i)[0]) {
//					}
//				}
//			}
//		}
	}	
	public void end() {
		e.end();		
	}
	private void shuntingYardAlgo(String[] exp) {
		ArrayDeque<String> operatorStack = new ArrayDeque<String>(); // Operator Stack
		ArrayDeque<String> outputQueue = new ArrayDeque<String>(); // Output Queue
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		operatorList.add("*");
		operatorList.add("/");
		operatorList.add(")");
		operatorList.add("(");
		operatorList.add("^");
		operatorList.add("|");
		int j=-1;
		for(int i=0;i<exp.length;i++) {
			if(exp[i]=="=") {
				j=i;
				break;
			}
		}
		for (int i = j+1; i < exp.length; i++) {
			if (!operatorList.contains(exp[i])) { // Add operands directly to the output queue.
				outputQueue.add(exp[i]);
			} else {
				if (operatorStack.isEmpty()) { // If the stack is empty add the operator directly to the stack.
					operatorStack.push(exp[i]);
				} else if (exp[i] == "(") { // If the current char is '(' add it to the stack.
					operatorStack.push(exp[i]);
				} else if (exp[i] == ")") { // If the current char is ')' pop all the characters from the stack to the
											// output queue until a '('
					while (operatorStack.peek() != "(") {
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
					while (!operatorStack.isEmpty() && operatorStack.peek() != "("
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
		int i = j+1;
		while (!outputQueue.isEmpty())
			exp[i++] = outputQueue.removeFirst();
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
		if (i == "+" || i == "-" || i == "*" || i == "/")
			return "left";
		else if (i == "^")
			return "right";
		else
			return null;
	}
}
