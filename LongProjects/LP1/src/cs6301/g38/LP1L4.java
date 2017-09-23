// Starter code for Level 4 driver for lp1

// Change following line to your group number
package cs6301.g38;

import java.util.Scanner;

public class LP1L4 {

	public static void main(String[] args) {
		Scanner in;
//		if (args.length > 0) {
//			int base = Integer.parseInt(args[0]);
//			// Use above base for all numbers (except I/O, which is in base 10)
//		}
		ExpressionLvl4 e4 = new ExpressionLvl4();
//		Expression e3 = new Expression();
		in = new Scanner(System.in);
//		LP1L4 x = new LP1L4();
		String word;
		String line=""; 
		while (in.hasNext()) {
			word = in.next();
			if (word.equals(";")) {
				if(line=="") {
					break;
				}
//				e3.eval(line);
				e4.setProgram(line.split("\\s+"));
				line="";
			}
			else {
				if(line=="") {
					line = word;
				}
				else {
					line=line+" "+word;
				}
			}
		}
		e4.execute();
		e4.end();
		in.close();
	}
}
