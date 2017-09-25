package cs6301.g38;

import java.util.Scanner;

public class LP1L1 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Num x, y;
		String ch = "n";
		int c;
		try {
			do {
				System.out.println("1.Addition");
				System.out.println("2.Subtraction");
				System.out.println("3.Multiplication");
				System.out.println("4.Power");
				c = in.nextInt();
				switch (c) {
				case 1:
					System.out.println("Enter first number");
					x = new Num(in.next());
					System.out.println("Enter second number");
					y = new Num(in.next());
					System.out.println("Result = " + Num.add(x, y));
					break;
				case 2:
					System.out.println("Enter first number");
					x = new Num(in.next());
					System.out.println("Enter second number");
					y = new Num(in.next());
					System.out.println("Result = " + Num.subtract(x, y));
					break;
				case 3:
					System.out.println("Enter first number");
					x = new Num(in.next());
					System.out.println("Enter second number");
					y = new Num(in.next());
					System.out.println("Result = " + Num.product(x, y));
					break;
				case 4:
					System.out.println("Enter base number");
					x = new Num(in.next());
					System.out.println("Enter exponent (long)");
					long e = in.nextLong();
					System.out.println("Result = " + Num.power(x, e));
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
				System.out.println("Do you want to perform another operation? (Y?N)");
				ch = in.next();
			} while (ch.equals("y") || ch.equals("Y"));

		}
		catch (Exception e) {
			System.out.println("!ERROR IN THE GIVEN INPUTS " + e.getMessage());
		}
		in.close();
	}
}
