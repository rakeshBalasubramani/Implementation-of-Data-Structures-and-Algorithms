//Sample code for Level 1 driver for lp1

//Change following line to your group number
package cs6301.g38;

import java.util.Scanner;

public class LP1L1 {
 public static void main(String[] args) {
 	Scanner in =new Scanner(System.in);
 	Num x;
 	System.out.println("Enter Biggest Number");
 	x = new Num(in.next());
 	x.printList();
 	System.out.println("Enter base");
 	x.setBase(in.nextLong());
 	//x.printList();
 	System.out.println();
 	x.toBase();
 	x.printList();
 	//x.toDecimal();
 	//System.out.println();
 	//x.printList();
 	Num y;
 	System.out.println("Enter Biggest Number");
 	y = new Num(in.next());
 	y.printList();
 	System.out.println("Enter base");
 	y.setBase(in.nextLong());
 	//y.printList();
 	System.out.println();
 	y.toBase();
 	y.printList();
 	//y.toDecimal();
 	//System.out.println();
 	//y.printList();
 	
 	Num z = Num.add(x, y);
 	System.out.println("\nAddition ");
 	//z.printList();
 	Num add = Num.add(x, y);
 	add.printList();
 	
 	System.out.println("\nSubtraction");
 	Num sub = Num.subtract(x, y);
 	sub.printList();
 	in.close();
 }
}
