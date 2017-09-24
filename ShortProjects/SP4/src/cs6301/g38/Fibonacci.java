package cs6301.g38;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 *	@description sp4 - Question 2 - Computing nth Fibonacci number
 *
 */


public class Fibonacci {

	 
	/**
	 * @description - method to calculate fibonacci using linear scan algorithm
	 * @param num - num for which Fibonacci is to be computed
	 * @return - result
	 */
	private static BigInteger linearFibonacci(int num) {
		
		if(num == 0){
			return BigInteger.ZERO;
		}else if(num == 1){
			return BigInteger.ONE;
		}
		BigInteger [] fib = new BigInteger[num+1];
		fib[0]= BigInteger.ZERO;
		fib[1]= BigInteger.ONE;
		for(int i=2; i<=num ;i++){  //scan each element, previous element stored in array
			fib[i]=fib[i-2].add(fib[i-1]); 
		}
		return fib[num];
	}
	
	
	/**
	 * method to calculate fibonacci using O(log n) algorithm
	 * @param num - num for which Fibonacci is to be computed
	 * @return - result
	 */
	
	private static BigInteger logFibonacci(int num){
		
		BigInteger v[][] = new BigInteger[][]{{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE, BigInteger.ZERO}};
		if(num == 0){
			return BigInteger.ZERO;
		}
		calculatePower(v, num-1);
		return v[0][0]; // return  Fib(n) - v[0][0]th element 
		
	}
	
	 
	/**
	 * calculate the matrix power (n-1)
	 *  
	 * @param v - matrix
	 * @param num - input number
	 */
	private static void calculatePower(BigInteger v[][], int num){
		if(num == 0 || num == 1){
			return;
		}
		BigInteger v1[][] = new BigInteger[][]{{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE, BigInteger.ZERO}};
		calculatePower(v, num/2);  // use recursion to calculate power 
		multiplyMatrix(v, v); //(n/2) * (n/2)
		if(num%2 != 0){  // if num is odd, multiply one extra time 
			multiplyMatrix(v, v1);
		}
		
	}
	// method to multiply two matrices 
	/**
	 *  method to multiply two matrices
	 * @param v - 1st matirx 
	 * @param v1 - 2nd matrix
	 */
	private static void multiplyMatrix(BigInteger[][] v, BigInteger[][] v1) {
		BigInteger res[][] = new BigInteger[][]{{BigInteger.ZERO,BigInteger.ZERO},{BigInteger.ZERO, BigInteger.ZERO}};
		for(int i=0; i<2; i++){
			for(int j=0; j<2; j++){
				for(int k=0; k<2; k++){
					res[i][j] =  res[i][j].add(v[i][k].multiply(v1[k][j]));
				}
			}
		}
		//store the result back in first matrix
		for(int i=0; i<2; i++){ 
			for(int j=0; j<2; j++){
				v[i][j] = res[i][j];
			}
		}
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
