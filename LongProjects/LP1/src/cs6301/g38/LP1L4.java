package cs6301.g38;

import java.util.Scanner;

public class LP1L4 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ExpressionLevel4 e4 = new ExpressionLevel4();
		try {
			if (args.length > 0) {
				int base = Integer.parseInt(args[0]);
				e4.setBase(base);
			}
			else {
				e4.setBase(10);
			}
			String word;
			String line = "";
			while (in.hasNext()) {
				word = in.next();
				if (word.equals(";")) {
					if (line == "") {
						break;
					}
					e4.setProgram(line.split("\\s+"));
					line = "";
				} else {
					if (line == "") {
						line = word;
					} else {
						line = line + " " + word;
					}
				}
			}
			e4.execute();
			e4.end();
		} catch (Exception err) {
			System.out.println("!ERROR IN THE GIVEN INPUTS " + err.getMessage());
		}
		in.close();
	}
}
