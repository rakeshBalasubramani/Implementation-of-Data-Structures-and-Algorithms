package cs6301.g38;

import java.util.Scanner;

public class MDSDriver {

	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		MDS mds = new MDS();
		Long[] description1 = {(long) 34575656, (long) 543554636};
		Long[] description2 = {(long) 345, (long) 5435};
		
		boolean status=mds.add(12L, description1);
		System.out.println("Status:"+status);
		System.out.println("---------------------");
		//mds.printItems();
		

		status=mds.add(3L, description2);
		System.out.println("Status:"+status);
		System.out.println("---------------------");
		
		//status=mds.add(12L, description);
		
		//mds.printItems();
		
		status=mds.add(12L, description2);
		System.out.println("Status:"+status);
		System.out.println("---------------------");
		
		// e - findItem 
		System.out.println("\n find Item ");
		System.out.println("List of items having the description " + mds.findItem(description1));
		
		
		// g - find supplier
		System.out.println("\n find supplier - Enter id");
		Long id2 = in.nextLong();
		System.out.println("List of Suppliers " + mds.findSupplier(id2));
				
		// h - find supplier given reputation
		System.out.println("\n find supplier - Enter id");
		Long id3 = in.nextLong();
		System.out.println("List of Suppliers meeting reputation " + mds.findSupplier(id3, 3.5f));
				
		
		// j - invoice(arr, minReputation)
		System.out.println("Invoice ");
		Long[] ids = {(long) 12, (long) 3};
		System.out.println("Total Price of Items " + mds.invoice(ids, 3.3f));
		
		// k - purge(max Reputation)
		System.out.println("Enter max reputation: ");
		float maxReputation = in.nextFloat();
		System.out.println("Items removed: "+ mds.purge(maxReputation));
		
		
		// l - remove item and return sum of description
		System.out.println("Enter the item to be removed: ");
		long id = in.nextLong();
		System.out.println("Sum of description of item " + id + " is " + mds.remove(id));
		mds.printItems();
		
		
		// m- remove(item, arr)
		System.out.println("Enter item");
		Long id1 = in.nextLong();
		Long[] arr = {(long) 233, (long) 34575656, (long) 345};
		System.out.println("Num of elements removed from description " + mds.remove(id1, arr));		
		mds.printItems();
		
		
		// n - removeAll(arr)
		//System.out.println("Num of items that lost one or more terms from desc " + mds.removeAll(arr));
		mds.printItems();
		

		status=mds.add(2134L, 2.0f);
		System.out.println("Status:"+status);
		System.out.println("---------------------");
		
		
		status=mds.add(54343L, 4.7f);
		System.out.println("Status:"+status);
		System.out.println("---------------------");
		
		
		status=mds.add(2134L, 3.2f);
		System.out.println("Status:"+status);
		System.out.println("---------------------");
		
		
		
		
//		// k - purge(max Reputation)
//		System.out.println("Enter max reputation: ");
//		float maxReputation = in.nextFloat();
//		System.out.println("Items removed: "+ mds.purge(maxReputation));
//		
//		
//		// l - remove item and return sum of description
//		System.out.println("Enter the item to be removed: ");
//		long id = in.nextLong();
//		System.out.println("Sum of description of item " + id + " is " + mds.remove(id));
//		mds.printItems();

		
	}

}
