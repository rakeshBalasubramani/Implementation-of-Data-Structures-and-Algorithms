package cs6301.g38;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;

//Input Validation need to be done
public class Expression {
	String[] exp;
	HashMap<String, Num> variables = new HashMap<String, Num>();

	public void eval(String word) {
		exp = word.split("\\s+");
		if (exp.length == 4) {
			variables.put(exp[0], new Num(exp[2]));
		} else {
			variables.put(exp[0], postFix());
		}
		System.out.println(variables.get(exp[0]));
	}

	private Num postFix() {
		String operatorList = "+-*/%^|";
		Num a, b;
		String[] expTemp = Arrays.copyOfRange(exp, 2, exp.length);
		ArrayDeque<Num> stack = new ArrayDeque<>();
		for (String i : expTemp) {
			if (i.equals(";")) {
				break;
			}
			if (!operatorList.contains(i)) {
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
}
