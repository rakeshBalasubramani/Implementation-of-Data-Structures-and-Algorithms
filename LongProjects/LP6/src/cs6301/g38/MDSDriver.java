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
		mds.printItems();
		

		status=mds.add(3L, description2);
		System.out.println("Status:"+status);
		
		
		//status=mds.add(12L, description);
		
		mds.printItems();
		
		status=mds.add(10L, description1);
		System.out.println("Status:"+status);

		mds.printItems();
		
		
		// k - purge(max Reputation)
		System.out.println("Enter max reputation: ");
		float maxReputation = in.nextFloat();
		System.out.println("Items removed: "+ mds.purge(maxReputation));
		
		
		// l - remove item and return sum of description
		System.out.println("Enter the item to be removed: ");
		long id = in.nextLong();
		System.out.println("Sum of description of item " + id + " is " + mds.remove(id));
		mds.printItems();
		
	}

}
