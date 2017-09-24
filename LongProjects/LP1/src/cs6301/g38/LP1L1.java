//Sample code for Level 1 driver for lp1

//Change following line to your group number
package cs6301.g38;

import java.math.BigInteger;
import java.util.Scanner;

public class LP1L1 {
 public static void main(String[] args) {
 	Scanner in =new Scanner(System.in);
 	Num x,y;
 	String p = "1001";
 	String q = "3";
 	BigInteger a = new BigInteger(p);
 	BigInteger b = new BigInteger(q);
 	BigInteger c = new BigInteger("1");
 	x = new Num(p);
 	y = new Num(q);
// 	while(a.pow(3).toString().equals(Num.power(x,3).toString())) {
// 		a=a.add(c);
// 		x=Num.add(x,new Num(1));
// 	}
// 	x.setBase(3);
// 	y.setBase(3);
 	System.out.println(a+":"+a.pow(2));
 	System.out.println(x+":"+Num.power(x,3));
 	System.out.println(Num.product(x,x));
 	Num.add(x,y).printList();
 }
}
