package cs6301.g38.test;

import cs6301.g38.Num;

public class AddNumTest {
	
	public static void testAddition()
	{

		// TODO Auto-generated method stub
		
		//Addition test cases.
		
		// both are positive.
		System.out.println("Addition test cases");
		
		Num na1 = new Num(10);
		Num na2 = new Num(2);
		Num result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");


		 na1 = new Num("00000000000000");
		 na2 = new Num("00000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("00000000000000");
		 na2 = new Num("00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(1000000000);
		 na2 = new Num(2000000000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(1000000000);
		 na2 = new Num(2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("15154644446444");
		 na2 = new Num("10000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("15154644446444");
		 na2 = new Num("10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("646466444444555005");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("10");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// both are negative
		
		na1 = new Num("-00000000000000");
		 na2 = new Num("-00000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-00000000000000");
		 na2 = new Num("-00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(-1000000000);
		 na2 = new Num(-2000000000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(-1000000000);
		 na2 = new Num(-2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-15154644446444");
		 na2 = new Num("-10000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("-15154644446444");
		 na2 = new Num("-10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("-646466444444555005");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("-10");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// one number is positive, other is negative.
		
		na1 = new Num("00000000000000");
		 na2 = new Num("-00000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("00000000000000");
		 na2 = new Num("-00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(1000000000);
		 na2 = new Num(-2000000000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(1000000000);
		 na2 = new Num(-2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("15154644446444");
		 na2 = new Num("-10000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("15154644446444");
		 na2 = new Num("-10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("-646466444444555005");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("-10");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// other way
		
		na1 = new Num("-00000000000000");
		 na2 = new Num("00000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-00000000000000");
		 na2 = new Num("00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		 na1 = new Num(-1000000000);
		 na2 = new Num(2000000000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(-1000000000);
		 na2 = new Num(2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-15154644446444");
		 na2 = new Num("10000000000000");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("-15154644446444");
		 na2 = new Num("10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("646466444444555005");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("10");
		result = Num.add(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
	/// Special cases:
		
		 na1 = new Num("151546444464449978787877880800808098080808080800000808080808080878787");
		 na2 = new Num("10");
		 result = Num.add(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			

	
	
	}

}
	