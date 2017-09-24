package cs6301.g38.test;

import cs6301.g38.Num;

public class ModNumTest {

	public static void testMod()
	{
	
		// b is always positive and a is non-negative
			System.out.println("Mod test cases");
			
			Num na1 = new Num(10);
			Num na2 = new Num(2);
			Num result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");


			 na1 = new Num("00000000000000");
			 na2 = new Num("45365454747");
			result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			

			 na1 = new Num("00000000000000");
			 na2 = new Num("45365454747");
			
			na1.setBase(99);
			na2.setBase(99);
			result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			
			 na1 = new Num(1000000000);
			 na2 = new Num(2000000000);
			 result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");

			 na1 = new Num(1000000000);
			 na2 = new Num(2000000000);
			 na1.setBase(55);
			 na2.setBase(55);
			 result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			

			 na1 = new Num("15154644446444");
			 na2 = new Num("10000000000000");
			result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			
			na1 = new Num("15154644446444");
			 na2 = new Num("10000000000000");
			 na1.setBase(10000);
			 na2.setBase(10000);
			 result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			
			
			 na1 = new Num("15154644446444");
			 na2 = new Num("646466444444555005");
			result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			
			
			
			 na1 = new Num("15154644446444");
			 na2 = new Num("10");
			result = Num.mod(na1, na2);
			result.printList();
			System.out.println(result);
			System.out.println("--------------");
			
									
		
		
		}
	
}
