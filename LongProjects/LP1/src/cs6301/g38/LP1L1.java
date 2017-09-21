//Sample code for Level 1 driver for lp1

//Change following line to your group number
package cs6301.g38;

import java.math.BigInteger;
import java.util.Scanner;

public class LP1L1 {
 public static void main(String[] args) {
 	Scanner in =new Scanner(System.in);
 	Num x,y;
 	String p = "12";
 	String q = "32";
 	BigInteger a = new BigInteger(p);
 	BigInteger b = new BigInteger(q);
 	x = new Num(p);
 	y = new Num(q);
 	x.setBase(2);
 	y.setBase(2);
 	System.out.println("Big:"+a.pow(new Integer(q)));
 	System.out.println("Div:"+Num.power(x,y));
 }
}
