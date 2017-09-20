//Sample code for Level 1 driver for lp1

//Change following line to your group number
package cs6301.g38;

import java.math.BigInteger;
import java.util.Scanner;

public class LP1L1 {
 public static void main(String[] args) {
 	Scanner in =new Scanner(System.in);
 	Num x,y;
 	String p = "6513756375676513762573657153761253716235712365712653721653";
 	String q = "345679973246637126387162837612378128321638726873612876";
 	BigInteger a = new BigInteger(p);
 	BigInteger b = new BigInteger(q);
 	x = new Num(in.next());
 	y = new Num(in.next());
 	System.out.println("Big:"+a.divide(b));
 	System.out.println("Div:"+Num.divide(x,y));
 }
}
