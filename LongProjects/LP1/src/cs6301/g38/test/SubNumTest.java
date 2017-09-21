package cs6301.g38.test;

import cs6301.g38.Num;

public class SubNumTest {

	
	public static void testSubtract()
	{

		// TODO Auto-generated method stub
		
		//Subtract test cases.
		
		// both are positive.
		System.out.println("Subtraction test cases");
		
		Num na1 = new Num(10);
		Num na2 = new Num(2);
		Num result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");


		 na1 = new Num("00000000000000");
		 na2 = new Num("00000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("00000000000000");
		 na2 = new Num("00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(1000000000);
		 na2 = new Num(2000000000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(1000000000);
		 na2 = new Num(2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("15154644446444");
		 na2 = new Num("10000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("15154644446444");
		 na2 = new Num("10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("646466444444555005");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("10");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// both are negative
		
		na1 = new Num("-00000000000000");
		 na2 = new Num("-00000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-00000000000000");
		 na2 = new Num("-00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(-1000000000);
		 na2 = new Num(-2000000000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(-1000000000);
		 na2 = new Num(-2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-15154644446444");
		 na2 = new Num("-10000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("-15154644446444");
		 na2 = new Num("-10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("-646466444444555005");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("-10");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// one number is positive, other is negative.
		
		na1 = new Num("00000000000000");
		 na2 = new Num("-00000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("00000000000000");
		 na2 = new Num("-00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(1000000000);
		 na2 = new Num(-2000000000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(1000000000);
		 na2 = new Num(-2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("15154644446444");
		 na2 = new Num("-10000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("15154644446444");
		 na2 = new Num("-10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("-646466444444555005");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("-10");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// other way
		
		na1 = new Num("-00000000000000");
		 na2 = new Num("00000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-00000000000000");
		 na2 = new Num("00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(-1000000000);
		 na2 = new Num(2000000000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(-1000000000);
		 na2 = new Num(2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-15154644446444");
		 na2 = new Num("10000000000000");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("-15154644446444");
		 na2 = new Num("10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("646466444444555005");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("10");
		result = Num.subtract(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
	
	
	}


	
	

}
