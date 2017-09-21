package cs6301.g38.test;

import java.math.BigInteger;

import cs6301.g38.Num;

public class DivideNumTest {

	


	
	public static void testDivision()
	{

		// TODO Auto-generated method stub
		
		//divide test cases.
		
		// both are positive.
		System.out.println("Division test cases");
		
		Num na1 = new Num(10);
		Num na2 = new Num(2);
		Num result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

try{
		 na1 = new Num("14466454546545446454846466545444654648674122154441616548484855554564");
		 na2 = new Num("1");
		 BigInteger bn1 = new BigInteger("14466454546545446454846466545444654648674122154441616548484855554564");
		 BigInteger bn2 = new BigInteger("2");
		System.out.println( bn1.divide(bn2));
		 System.out.println(System.currentTimeMillis());
		result = Num.divide(na1, na2);
		 System.out.println(System.currentTimeMillis());

		result.printList();
		System.out.println(result);
		System.out.println("--------------");
}

catch(Exception e)
{
	System.out.println(e.getMessage());
}
		
try{
		 na1 = new Num("00000000000000");
		 na2 = new Num("00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
	
}

catch(Exception e)
{
	System.out.println(e.getMessage());
}
		 na1 = new Num(1000000000);
		 na2 = new Num(200000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(1000000000);
		 na2 = new Num(200000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("151546444464447879879");
		 na2 = new Num("10000000000000");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("151546444464447879879");
		 na2 = new Num("10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("646466444444555005");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("151546444464449978787877880800808098080808080800000808080808080878787");
		 na2 = new Num("10");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// both are negative
		try{
		na1 = new Num("-00000000000000");
		 na2 = new Num("-00000000000000");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try{
		 na1 = new Num("-00000000000000");
		 na2 = new Num("-00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		 na1 = new Num(-1000000000);
		 na2 = new Num(-2000000000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(-1000000000);
		 na2 = new Num(-56);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-15154644446444");
		 na2 = new Num("-10000000");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("-15154644446444");
		 na2 = new Num("-10000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("-646466444444555005");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("-151546444464449978787877880800808098080808080800000808080808080878787");
		 na2 = new Num("-10");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// one number is positive, other is negative.
		
		try{
		na1 = new Num("00000000000000");
		 na2 = new Num("-00000000000000");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		try{
		 na1 = new Num("00000000000000");
		 na2 = new Num("-00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		 na1 = new Num(10515131);
		 na2 = new Num(-2020);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(10515131);
		 na2 = new Num(-2020);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("15154644446444");
		 na2 = new Num("-1000051510");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("15154644446444");
		 na2 = new Num("-1000051510");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("15154644446444");
		 na2 = new Num("-1");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("15154644446444114466454555");
		 na2 = new Num("-10");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		// other way
		try{
		na1 = new Num("-00000000000000");
		 na2 = new Num("00000000000000");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		try{

		 na1 = new Num("-00000000000000");
		 na2 = new Num("00000000000000");
		
		na1.setBase(99);
		na2.setBase(99);
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		 na1 = new Num(-1000000000);
		 na2 = new Num(2000000000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");

		 na1 = new Num(-1000000000);
		 na2 = new Num(2000000000);
		 na1.setBase(55);
		 na2.setBase(55);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		

		 na1 = new Num("-15154644446444");
		 na2 = new Num("10000000000000");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		na1 = new Num("-15154644446444");
		 na2 = new Num("10000000000000");
		 na1.setBase(10000);
		 na2.setBase(10000);
		 result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("646466444444555005");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
		
		
		 na1 = new Num("-15154644446444");
		 na2 = new Num("10");
		result = Num.divide(na1, na2);
		result.printList();
		System.out.println(result);
		System.out.println("--------------");
		
	
	
	}


	
	





}
