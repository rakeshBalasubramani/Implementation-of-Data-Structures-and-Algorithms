package cs6301.g38.test;

import java.math.BigInteger;

import cs6301.g38.Num;

public class PowerNumTest {

	public static void testPower(){
		
		// n is always positive
		
		// -ve number power +ve number
		Num na1 = new Num("-15");
		Num divisor = new Num("5");
		long n=5;
		Num result = Num.power(na1,n);
		Num resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");

		
		na1 = new Num("-33525");
		n=14;
		divisor = new Num(14);
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
		
		// Using bigInteger to check the answer
		BigInteger x= new BigInteger("-33525");
		BigInteger res=x.pow(14);
		System.out.println(res+ " BigInteger");// printing text at last for easy comparison
		System.out.println("--------------");
		
		na1 = new Num("-33525");
		 n=14;
		 divisor = new Num(14);
		 na1.setBase(10000);
		 divisor.setBase(10000);
		 result = Num.power(na1,n);
		 resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
		
		na1 = new Num("-3276237586230756023748");
		n=10;
		divisor = new Num("10");
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
		
		x= new BigInteger("-3276237586230756023748");
		res=x.pow(10);
		System.out.println(res+ " BigInteger");// printing text at last for easy comparison
		System.out.println("--------------");

		
//		// +ve number power +ve number
		na1 = new Num("33525");
		n=14;
		divisor = new Num(14);
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
		
		 na1 = new Num("33525");
		 n=14;
		 divisor = new Num("14");
		 na1.setBase(10000);
		 divisor.setBase(10000);
		 result = Num.power(na1,n);
		 resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(result);
		System.out.println("--------------");
		
		x= new BigInteger("33525");
		res=x.pow(14);
		System.out.println(res+ " BigInteger");// printing text at last for easy comparison
		System.out.println("--------------");
		
		
		na1 = new Num("3276237586230756023748");
		n=10;
		divisor = new Num(10);
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
		
		x= new BigInteger("3276237586230756023748");
		res=x.pow(10);
		System.out.println(res+ " BigInteger");// printing text at last for easy comparison
		System.out.println("--------------");
		
		// 0 power something
		na1 = new Num("0");
		n=14;
		divisor = new Num("14");
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");

		
		// something power 0
		na1 = new Num("236576878");
		n=0;
		divisor = new Num(0);
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
		
		// 0 power 0
		na1 = new Num("0");
		n=0;
		divisor = new Num("0");
		result = Num.power(na1,n);
		resultNum = Num.power(na1, divisor);
		result.printList();
		resultNum.printList();
		System.out.println(result);
		System.out.println(resultNum);
		System.out.println("--------------");
	}

}
