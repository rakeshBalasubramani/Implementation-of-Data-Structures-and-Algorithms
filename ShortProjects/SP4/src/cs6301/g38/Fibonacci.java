package cs6301.g38;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 *	sp4 - question 2 - Computing nth Fibonacci number
 *
 */


public class Fibonacci {

	// method to calculate fibonacci using linear scan algorithm
	private static BigInteger linearFibonacci(int num) {
		if(num == 0){
			return BigInteger.ZERO;
		}
		else if(num == 1){
			return BigInteger.ONE;
		}
		else{
			return linearFibonacci(num -2).add(linearFibonacci(num-1)); 
		}
		
	}
	
	//method to calculate fibonacci using O(log n) algorithm
	private static BigInteger logFibonacci(int num){
		
		BigInteger v[][] = new BigInteger[][]{{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE, BigInteger.ZERO}};
		if(num == 0){
			return BigInteger.ZERO;
		}
		calculatePower(v, num-1);
		return v[0][0]; // return  Fib(n) - v[0][0]th element 
		
	}
	
	// calculate the matrix power (n-1)
	private static void calculatePower(BigInteger v[][], int num){
		BigInteger v1[][] = new BigInteger[][]{{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE, BigInteger.ZERO}};
		
		//multiply the matrix n-1 times 
		for(int i=1; i<= num-1; i++){
			multiplyMatrix(v,v1);   
		}
	}
	
	
	// method to multiply two matrices 
	private static void multiplyMatrix(BigInteger[][] v, BigInteger[][] v1) {
		BigInteger a = (v[0][0].multiply(v1[0][0])).add(v[0][1].multiply(v1[1][0]));
		BigInteger b = (v[0][0].multiply(v1[0][1])).add(v[0][1].multiply(v1[1][1]));
		BigInteger c = (v[1][0].multiply(v1[0][0])).add(v[1][1].multiply(v1[1][0]));
		BigInteger d = (v[1][0].multiply(v1[0][1])).add(v[1][1].multiply(v1[1][1]));
		v[0][0] = a;//store the result back in the same matrix v[][]
		v[0][1] = b;
		v[1][0] = c;
		v[1][1] = d;
	}

	public static void main(String[] args) {
		System.out.println("Enter the number");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		Timer t = new Timer();
		
		//linear fibonacci
		t.start(); // start timer
		BigInteger result1 = linearFibonacci(num);
		System.out.println("Linear Fibonacci "+ result1);
		System.out.println(t.end()); // end timer
		System.out.println();
		
		//log fibonacci
		t.start(); // start timer
		BigInteger result2 = logFibonacci(num);
		System.out.println("Log Fibonacci "+ result2);
		System.out.println(t.end()); // end timer
		System.out.println();
		in.close();

	}
}
