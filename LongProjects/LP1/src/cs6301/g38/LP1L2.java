package cs6301.g38;

import java.util.Scanner;

public class LP1L2 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Num x, y;
		String ch = "n";
		int c;
		try {
			do {
				System.out.println("1.Power");
				System.out.println("2.Division");
				System.out.println("3.Modulo");
				System.out.println("4.Squareroot");
				c = in.nextInt();
				switch (c) {
				case 1:
					System.out.println("Enter base number");
					x = new Num(in.next());
					System.out.println("Enter exponent");
					y = new Num(in.next());
					System.out.println("Result = " + Num.power(x, y));
					break;
				case 2:
					System.out.println("Enter dividend");
					x = new Num(in.next());
					System.out.println("Enter divisor");
					y = new Num(in.next());
					System.out.println("Result = " + Num.divide(x, y));
					break;
				case 3:
					System.out.println("Enter dividend");
					x = new Num(in.next());
					System.out.println("Enter  divisor");
					y = new Num(in.next());
					System.out.println("Result = " + Num.mod(x, y));
					break;
				case 4:
					System.out.println("Enter number");
					x = new Num(in.next());
					System.out.println("Result = " + Num.squareRoot(x));
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
				System.out.println("Do you want to perform another operation? (Y?N)");
				ch = in.next();
			} while (ch.equals("y") || ch.equals("Y"));
		} catch (Exception e) {
			System.out.println("!ERROR IN THE GIVEN INPUTS " + e.getMessage());
		}
		in.close();
	}
}
