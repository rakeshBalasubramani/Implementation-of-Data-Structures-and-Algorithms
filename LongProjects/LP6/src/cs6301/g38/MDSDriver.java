package cs6301.g38;

public class MDSDriver {

	public static void main(String[] args) {
		
		MDS mds = new MDS();
		Long[] description = {(long) 34575656, (long) 543554636};
		boolean status=mds.add(12L, description);
		System.out.println("Status:"+status);

		status=mds.add(12L, description);
		System.out.println("Status:"+status);
	}

}
