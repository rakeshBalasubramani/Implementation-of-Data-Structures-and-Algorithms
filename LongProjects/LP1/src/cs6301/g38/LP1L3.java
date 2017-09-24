// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g38;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LP1L3 {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
		    File inputFile = new File(args[0]);
		    in = new Scanner(inputFile);
		} else {
		    in = new Scanner(System.in);
		}
	
		// LP1L3 x = new LP1L3();
		Expression e = new Expression();
		String word;
		String line=""; 
		while (in.hasNext()) {
			word = in.next();
			if (word.equals(";")) {
				if(line=="") {
					break;
				}
				e.eval(line.split("\\s+"));
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
		e.end();
		in.close();
	}
}
