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
 	System.out.println("Enter base");
 	x.setBase(in.nextLong());
 	x.toBase();
 	x.toDecimal();
 	x.printList(); 	
 	
 	Num y;
 	System.out.println("\nEnter Biggest Number");
 	y = new Num(in.next());
 	System.out.println("Enter base");
 	y.setBase(in.nextLong());
 	y.toBase();
 	y.printList();
 	
 	Num add = Num.add(x, y);
 	System.out.println(add.outList);
 	
 	Num sub = Num.subtract(x, y);
 	System.out.println(sub.outList);
 	in.close();
	//Num z = Num.add(x, y);
	//System.out.println(z);
	//Num a = Num.power(x, 8);
	//System.out.println(a);
	//z.printList();
 }
}
