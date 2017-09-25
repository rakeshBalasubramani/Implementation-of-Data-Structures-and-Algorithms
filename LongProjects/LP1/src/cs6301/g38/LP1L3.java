package cs6301.g38;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LP1L3 {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		Expression e = new Expression();
		String word;
		String line = "";
		try {
			while (in.hasNext()) {
				word = in.next();
				if (word.equals(";")) {
					if (line == "") {
						break;
					}
					e.eval(line.split("\\s+"));
					line = "";
				} else {
					if (line == "") {
						line = word;
					} else {
						line = line + " " + word;
					}
				}
			}
		} catch (Exception err) {
			System.out.println("!ERROR IN THE GIVEN INPUTS " + err.getMessage());
		}
		e.end();
		in.close();
	}
}
