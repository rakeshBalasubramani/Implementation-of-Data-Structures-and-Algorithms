package cs6301.g38;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


class Program {
	Program(String[] code2) {
		if(checkLineNo(code2[0])) {
			lineNo=Long.parseLong(code2[0]);
			code = Arrays.copyOfRange(code2,1,code2.length);
		}
		else {
			lineNo=-1;
			code = code2;
		}
		
	}
	private boolean checkLineNo(String string) {
		return string.matches("\\d+");
	}
	String[] code;
	long lineNo;
}

public class ExpressionLvl4 {
	String questionMark="?";
	String collin=":";
	long base;
	public void setBase(long base) {
		this.base = base;
	}

	LinkedList<Program> program = new LinkedList<Program>();
	Expression e = new Expression();

	public void setProgram(String[] code) {
		program.add(new Program(code));
		e.setBase(base);
	}

	public void execute() {
		assignLineNumbers();
		for (int pc = 0; pc < program.size(); pc++) {
			if (program.get(pc).code.length > 2 && !program.get(pc).code[1].equals(questionMark)) {
				shuntingYardAlgo(program.get(pc).code);
				e.eval(program.get(pc).code);
			} else if (program.get(pc).code.length > 2 && program.get(pc).code[1].equals(questionMark)) {
				if (e.variables.get(program.get(pc).code[0]).compareTo(new Num(0)) != 0) {
					pc=Integer.parseInt(program.get(pc).code[2])-1;
				}
				else if(program.get(pc).code.length > 3 && program.get(pc).code[3].equals(collin)) {
					pc=Integer.parseInt(program.get(pc).code[4]);
				}
			} else {
				e.eval(program.get(pc).code);
			}
		}
	}
	
	public void assignLineNumbers() {
		
		for(Program p : program) {
			if(p.code.length>1&&p.code[1].equals(questionMark)) {
				p.code[2]=getLineNo(p.code[2]);
				if(p.code.length>3&&p.code[3].equals(collin)) {
					p.code[4]=getLineNo(p.code[4]);
				}
			}
		}
	}

	private String getLineNo(String lno) {
		for(Program p :program) {
			if(p.lineNo==Long.parseLong(lno)) {
				return Integer.toString(program.indexOf(p));
			}
		}
		return null;
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
		int j = -1;
		for (int i = 0; i < exp.length; i++) {
			if (exp[i] == "=") {
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
		int i = j + 1;
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
