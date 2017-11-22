package cs6301.g38;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cs6301.g38.MDS.Pair;

public class MultiDimensionalSearch {

	private HashMap<Long, LinkedList<Long>> itemDescription = new HashMap<Long, LinkedList<Long>>();
	private TreeMap<Item, TreeSet<SupplierItemInfo>> itemSupplierMap = new TreeMap<Item, TreeSet<SupplierItemInfo>>();
	private TreeMap<Supplier, LinkedList<ItemPrice>> supplierItemMap = new TreeMap<Supplier, LinkedList<ItemPrice>>();

	Item it;
	Supplier s;

	public static class Item implements Comparable<Item> {

		private long id;
		private List<Long> description;

		public Item() {

		}

		public Item(long id, Long[] desc) {
			this.id = id;
			description = new LinkedList<Long>();

			for (long d : desc) {
				description.add(d);
			}
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public List<Long> getDescription() {
			return description;
		}

		public void setDescription(List<Long> description) {
			this.description = description;
		}

		// @Override
		// public int hashCode()
		// {
		// return (int)id;
		// }
		//
		@Override
		public boolean equals(Object i) {
			Item item = (Item) i;
			return this.id == item.id;
		}

		@Override
		public int compareTo(Item item) {
			return (int) (this.id - item.id);

		}

	}

	public static class Supplier implements Comparable<Supplier> {
		private long vid;
		private float reputation;

		public Supplier() {

		}

		public Supplier(long vid, float reputation) {
			this.vid = vid;
			this.reputation = reputation;
		}

		public long getVid() {
			return vid;
		}

		public void setVid(long vid) {
			this.vid = vid;
		}

		public float getReputation() {
			return reputation;
		}

		public void setReputation(float reputation) {
			this.reputation = reputation;
		}

		public int compareTo(Supplier s) {
			return (int) (this.vid - s.vid);
		}

		@Override
		public boolean equals(Object obj) {

			Supplier s = (Supplier) obj;
			return this.vid == s.vid;
		}

	}

	public static class SupplierItemInfo implements Comparable<SupplierItemInfo> {

		private long id, vid;
		private float reputation;
		private int price;

		
		public SupplierItemInfo(){
			
		}
		
		public SupplierItemInfo(long id, long vid, float reputation, int price)
		{
			this.id=id;
			this.vid=vid;
			this.reputation=reputation;
			this.price=price;
		}
		

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getVid() {
			return vid;
		}

		public void setVid(long vid) {
			this.vid = vid;
		}

		public float getReputation() {
			return reputation;
		}

		public void setReputation(float reputation) {
			this.reputation = reputation;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}


		@Override
		public int compareTo(SupplierItemInfo sio)
		{
			
			
			
			int reputationDiff = Float.compare(this.reputation, sio.getReputation());			
			return ((reputationDiff == 0) ? Integer.compare(this.price,sio.getPrice()) : reputationDiff);			
			//return (int) (this.reputation-sio.reputation);
		}

	}

	public static class ItemPrice {
		private long id;
		private int price;

		public ItemPrice(long id, int price) {
			this.id = id;
			this.price = price;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}
	}

	public MultiDimensionalSearch() {

	}

	public boolean add(long id, Long[] description) {
		return addDescription(id, description);
	}

	private boolean addDescription(long id, Long[] description) {

		Item[] arr;
		it = new Item();
		it.setId(id);

		 if(itemSupplierMap.containsKey(it))
		 {			 
			 /* 
			 Set<Item> itemSet= itemSupplierMap.keySet();
			 arr = new Item[itemSet.size()];
			 itemSet.toArray(arr);
			 
			 int index=BinarySearch.recursiveBinarySearch(arr,it);
			 
			 it=arr[index];
			 
			 //System.out.println("Id From binary Search:"+ it.getId());
			 */			
			
			 Item it1 = getItemDetails(it);
			 TreeSet<SupplierItemInfo> supplierinfo= itemSupplierMap.get(it1);
			 List<Long> itemDesc=it1.description;
			 
			 for(long desc: description)
			 {
				 itemDesc.add(desc);
			 }
			 
			 itemSupplierMap.put(it1,supplierinfo);
			 
			 for(long d: itemDesc)
			 {
				 if(itemDescription.containsKey(d))
				 {
					 LinkedList<Long> idWithDescription=itemDescription.get(d);
					 idWithDescription.add(id);
					 itemDescription.put(d, idWithDescription);
					 
				 }
				 
				 else
				 {
					 LinkedList<Long> idWithDescription= new LinkedList<Long>();
					 idWithDescription.add(id);
					 itemDescription.put(d,idWithDescription);
				 }
				 
				 
			 }
			 
			 System.out.println("New desc added");
			 System.out.println(" Present Item Information");
			 
			for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet())
			 {
				System.out.println("###");
				System.out.println("id:" + iteminfo.getKey().id);

				for (long d : iteminfo.getKey().description) {
					System.out.println(d);
				}

			}

			return false;
		}

		else {
			it = new Item(id, description);
			itemSupplierMap.put(it, new TreeSet<>());

			for (long d : description) {
				if (itemDescription.containsKey(d)) {
					LinkedList<Long> idWithDescription = itemDescription.get(d);
					idWithDescription.add(id);
					itemDescription.put(d, idWithDescription);

				}

				else {
					LinkedList<Long> idWithDescription = new LinkedList<Long>();
					idWithDescription.add(id);
					itemDescription.put(d, idWithDescription);

				}

			}

			System.out.println(" new element added");
			System.out.println(" New Item Information");

			for (Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo : itemSupplierMap.entrySet()) {
				System.out.println("###");
				System.out.println("id:" + iteminfo.getKey().id);

				for (long d : iteminfo.getKey().description) {
					System.out.println(d);
				}

			}

			return true;
		}

	}

	public boolean add(long vid, float reputation) {

		return addSupplier(vid, reputation);
	}

	private boolean addSupplier(long vid, float reputation) {

		Supplier[] supplierArr;
		s = new Supplier();
		s.setVid(vid);

		if (supplierItemMap.containsKey(s)) {
			Set<Supplier> supplierSet = supplierItemMap.keySet();
			supplierArr = new Supplier[supplierSet.size()];
			supplierSet.toArray(supplierArr);

			int index = BinarySearch.recursiveBinarySearch(supplierArr, s);
			s = supplierArr[index];

			System.out.println("Supplier id:" + s.getVid());
			LinkedList<ItemPrice> itemPrice = supplierItemMap.get(s);
			s.setReputation(reputation);
			supplierItemMap.put(s, itemPrice);
			System.out.println("Updated reputation of supplier");

			for (Map.Entry<Supplier, LinkedList<ItemPrice>> map : supplierItemMap.entrySet()) {
				Supplier up = map.getKey();
				LinkedList<ItemPrice> ip = map.getValue();

				System.out.println("Supplier id:" + up.getVid());
				System.out.println("Supplier Reputation:" + up.getReputation());

				for (ItemPrice i : ip) {
					System.out.println("Item id:" + i.getId());
					System.out.println("Item Price:" + i.getPrice());
				}

			}

			return false;
		}

		else {
			s = new Supplier(vid, reputation);
			supplierItemMap.put(s, new LinkedList<ItemPrice>());
			System.out.println("NEW supplier");

			for (Map.Entry<Supplier, LinkedList<ItemPrice>> map : supplierItemMap.entrySet()) {
				Supplier up = map.getKey();
				LinkedList<ItemPrice> ip = map.getValue();

				System.out.println("Supplier id:" + up.getVid());
				System.out.println("Supplier Reputation:" + up.getReputation());

				for (ItemPrice i : ip) {
					System.out.println("Item id:" + i.getId());
					System.out.println("Item Price:" + i.getPrice());
				}

			}
			return true;
		}

	}

	
	//----k -----
	public Long[] purge(float maxReputation){
		//remove items having supplier <= maxReputation
		return purgeItems(maxReputation);

	}

	private Long[] purgeItems(float maxReputation){
		
		/*private HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>();
		private TreeMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
		private TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		*/
		
		LinkedList<Long> itemsRemoved = new LinkedList<Long>();
		LinkedList<Supplier> supplierRemoved = new LinkedList<Supplier>();

		// get the items and suppliers where suppliers's reputation <=
		// maxReputation
		for (Map.Entry<Supplier, LinkedList<ItemPrice>> supplierMaxReputation : supplierItemMap.entrySet()) {

			Supplier supplierEntry = supplierMaxReputation.getKey();
			LinkedList<ItemPrice> supplierItems = supplierMaxReputation.getValue();

			if (supplierEntry.reputation <= maxReputation) {
				supplierRemoved.add(supplierEntry);
				for (ItemPrice item : supplierItems) {
					itemsRemoved.add(item.id);
				}
			}
		}

		System.out.println("Items to be purged :" + itemsRemoved);

		
		// remove the supplier entry from supplierItemMap
		for(Supplier supplier : supplierRemoved){
			if(supplierItemMap.containsKey(supplier)){
				supplierItemMap.get(supplier).clear();
				//supplierItemMap.remove(supplier);			
			}
		}
		
		// remove the item entry from itemSupplierMap
		for(Long itemId : itemsRemoved){
			if(itemSupplierMap.containsKey(itemId)){				
				itemSupplierMap.remove(itemId);
			}
		}

		// remove the items with thier description from the itemDescriptionMap
		for (Map.Entry<Long, LinkedList<Long>> descItem : itemDescription.entrySet()) {

			// check for item ids to be removed
			for (Long id : itemsRemoved) {
				if (descItem.getValue().contains(id)) {
					descItem.getValue().remove(id);
				}
			}
		}

		return itemsRemoved.toArray(new Long[itemsRemoved.size()]);

	}

	// --- l ---
	public Long remove(Long id) {
		return removeItem(id);
	}

	
	private Long removeItem(Long id){
		
		/*private HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>();
		private TreeMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
		private TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		*/
		long sumOfDescription = 0;
		//Item []arr; 
		it= new Item();
		it.setId(id);
		 
		if(itemSupplierMap.containsKey(it))
		{
			 
			/*Set<Item> itemSet= itemSupplierMap.keySet();
			arr = new Item[itemSet.size()];
			itemSet.toArray(arr);
			 
			int index=BinarySearch.recursiveBinarySearch(arr,it);
			
			it=arr[index];
			System.out.println("Id From binary Search:"+ it.getId() + " and desc " + it.getDescription());
			TreeSet<SupplierItemInfo> supplierinfo= itemSupplierMap.get(it);
			*/
			Item it1 = getItemDetails(it);
			List<Long> desc =it1.description;
		
			// remove  entry from itemSupplierMap
			itemSupplierMap.remove(it);
					
			// remove the items with thier description from the itemDescriptionMap
			for(Map.Entry<Long, LinkedList<Long>> descItem : itemDescription.entrySet()){
				//check for item id to be removed				
				if(descItem.getValue().contains(desc)){
						descItem.getValue().remove(desc);
				}
			}

			// remove the item from supplierItemMap
			for (Map.Entry<Supplier, LinkedList<ItemPrice>> supplierItemInfo : supplierItemMap.entrySet()) {
				LinkedList<ItemPrice> info = supplierItemInfo.getValue();
				for (ItemPrice i : info) {
					if (i.id == id) {
						info.remove(i);
					}
				}
			}

			// find the sum of description
			for (Long d : desc) {
				sumOfDescription += d;
			}
			return sumOfDescription;


		} else {
			System.out.println("Item not found ");
			return 0L;
		}
	}



	public int add(Long vid, Pair[] idPrice) {
		return addProducts(vid, idPrice);
	}

	private int addProducts(Long vid, Pair[] idPrice) {

		int newProductscount = 0;
		boolean isnewProd;
		Supplier[] supplierArr;

		s = new Supplier();
		s.setVid(vid);

		if (supplierItemMap.containsKey(s)) {
			Set<Supplier> supplierSet = supplierItemMap.keySet();
			supplierArr = new Supplier[supplierSet.size()];
			supplierSet.toArray(supplierArr);

			int sIndex = BinarySearch.recursiveBinarySearch(supplierArr, s);
			s = supplierArr[sIndex];

			LinkedList<ItemPrice> ip = supplierItemMap.get(s);

			for (Pair p : idPrice) {
				isnewProd = true;
				for (ItemPrice itemP : ip) {

					if (itemP.equals(p)) {
						itemP.setPrice(p.price);
						isnewProd = false;

						SupplierItemInfo si = new SupplierItemInfo(p.id, vid, s.getReputation(), p.price);

						Item[] arr;
						it = new Item();
						it.setId(p.id);

						if (itemSupplierMap.containsKey(it)) {

							Set<Item> itemSet = itemSupplierMap.keySet();
							arr = new Item[itemSet.size()];
							itemSet.toArray(arr);

							int iIndex = BinarySearch.recursiveBinarySearch(arr, it);

							it = arr[iIndex];

							TreeSet<SupplierItemInfo> supplierItemSet = itemSupplierMap.get(it);
							supplierItemSet.add(si);
						} else {

						}
						break;
					}

					if (isnewProd) {
						newProductscount++;
						ItemPrice newItemPrice = new ItemPrice(p.id, p.price);
						ip.add(newItemPrice);

						// for adding supplierItem information

						// SupplierItemInfo si=new SupplierItemInfo(id, vid,
						// reputation, price);

					}

				}
			}
			supplierItemMap.put(s, ip);

		} else {
			s = new Supplier(vid, 0.0f);
			LinkedList<ItemPrice> ip = new LinkedList<>();

			for (Pair p : idPrice) {
				ip.add(new ItemPrice(p.id, p.price));
				newProductscount++;
			}

			supplierItemMap.put(s, ip);
		}

		return newProductscount;

	}


	 
	// --- m ---
	public int remove(Long id, Long[ ] arr) {
		
    	//return 0;
    	return removeItem(id, arr);
    }

	private int removeItem(Long id, Long[] arr){
		
		int numOfElementsRemoved  = 0;
		
		// remove for item id , all desc in arr
		for(Long desc : arr){
			if(itemDescription.containsKey(desc)){
				//LinkedList<Long> items = new LinkedList<Long>();
				if(itemDescription.get(desc).contains(id)){
					itemDescription.get(desc).remove(id);
					numOfElementsRemoved++;
				}
			}
		}
		
		// remove arr elements from description in itemSupplierMap
		
		
		Item []arr1; 
		it= new Item();
		it.setId(id);
		 
		if(itemSupplierMap.containsKey(it))
		{
			
			Item it1 = getItemDetails(it);
			/*Set<Item> itemSet= itemSupplierMap.keySet();
			arr1 = new Item[itemSet.size()];
			itemSet.toArray(arr1);
			 
			int index=BinarySearch.recursiveBinarySearch(arr1,it);
			it=arr1[index];
			System.out.println("Id From binary Search:"+ it.getId() + " and desc " + it.getDescription());
		*/	
			List<Long> desc =it1.description;
			for(Long d: arr){
				if(desc.contains(d)){
					desc.remove(d);
				}
			}		
		}else{
			System.out.println("Item not found ");
			return 0;
		}		
		return numOfElementsRemoved;		
	}
	
	//--- n ---
	public int removeAll(Long[] arr) {
		return removeAllFromItem(arr);
	}
	
	
	public int removeAllFromItem(Long[] arr){
		/*private HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>();
		private TreeMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
		private TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		*/
		
		int countOfItems = 0;		
		// remove all items associated with desc in arr in itemDescriptionMap
		for(Long i : arr){
			if(itemDescription.containsKey(i)){
				itemDescription.get(i).clear();
			}
		}		
		// remove desc in arr from all items in itemSupplierMap 
		for(Map.Entry<Item, TreeSet<SupplierItemInfo>> itemSupplier : itemSupplierMap.entrySet()){
			int countOfDescInArr = 0;
			Item item = itemSupplier.getKey();
			for(Long desc : arr){
				if(item.description.contains(desc)){
					countOfDescInArr++;
					item.description.remove(desc);
				}
			}	
			if(countOfDescInArr >= 1){
				countOfItems++;
			}
		}					
		return countOfItems;		
	}
		
	// --- j ---
	public int invoice(Long[] arr, float minReputation) {
		TreeSet<SupplierItemInfo> supplierInfo = new TreeSet<SupplierItemInfo>();
		SupplierItemInfo sii = new SupplierItemInfo();
		sii.setReputation(minReputation);
		
		int sumOfMinPrice = 0;
		
		for(Long id : arr){
			it = new Item();
				
			it.setId(id);
			if(itemSupplierMap.containsKey(it)){
				Item it1 = getItemDetails(it);
				System.out.println("item details from func " + it1.id + " desc " + it1.description );
				supplierInfo = itemSupplierMap.get(it1);
				System.out.println("\t supplier info for item " + it.id + " = " + supplierInfo);
				
				//int index = supplierInfo.headSet(sii).size()+1;
				
				// suppliers for the item whose reputation >= minReputation
				supplierInfo.tailSet(sii, true);
				//the min price of the item whose supplier >= minReputation
				if(supplierInfo.size() >=1){
					SupplierItemInfo firstElement = supplierInfo.first();
					sumOfMinPrice += firstElement.price;
				}
			}			
		}				
		return sumOfMinPrice;
	}
	
	
	
	public Item getItemDetails(Item it){
		Item []arr1; 
			System.out.println("Item " + it.id + " present ");
			
			Set<Item> itemSet= itemSupplierMap.keySet();
			arr1 = new Item[itemSet.size()];
			itemSet.toArray(arr1);
			 
			int index=BinarySearch.recursiveBinarySearch(arr1,it);
			it=arr1[index];
			System.out.println("Id From binary Search:"+ it.getId() + " and desc " + it.getDescription());
			return it;
	}
	
	
	public  void printItems(){
		
		System.out.println("\n--------- Present Item Information \n item supplier map -----------");
		 for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet()){
			 System.out.println("id:"+iteminfo.getKey().id);
			 for(long d : iteminfo.getKey().description){
					 System.out.println(d);
			 }
		 }	
		 
		 System.out.println("\n---------   supplier item map -----------");
		 for(Map.Entry<Supplier, LinkedList<ItemPrice>> iteminfo: supplierItemMap.entrySet()){
			 System.out.println("id:"+iteminfo.getKey());
			 for(ItemPrice d : iteminfo.getValue()){
					 System.out.println(d);
			 }
		 }	 
		 
	}
	
}
