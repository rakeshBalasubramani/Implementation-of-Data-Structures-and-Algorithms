// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g38;

import java.util.Scanner;

public class LP1L3 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// LP1L3 x = new LP1L3();
		Expression e = new Expression();
		String word;
		while (in.hasNext()) {
			word = in.nextLine();
			if (word.equals(";")) {
				break;
			}
			e.eval(word);
		}
		in.close();
	}
}
