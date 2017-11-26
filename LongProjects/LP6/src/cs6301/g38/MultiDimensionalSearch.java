package cs6301.g38;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cs6301.g38.MDS.Pair;

public class MultiDimensionalSearch {

	private HashMap<Long, HashSet<Long>> itemDescription = new HashMap<Long, HashSet<Long>>();// desc,
																								// list
																								// of
																								// Item
																								// id's,
																								// Replace
																								// LL
																								// to
																								// HashSet
	private TreeMap<Item, TreeSet<SupplierItemInfo>> itemSupplierMap = new TreeMap<Item, TreeSet<SupplierItemInfo>>();
	private TreeMap<Supplier, TreeSet<ItemPrice>> supplierItemMap = new TreeMap<Supplier, TreeSet<ItemPrice>>(); // Replace
																													// LL
																													// to
																													// Treeset
	private HashMap<Long, Float> suppplierIdReputation = new HashMap<Long, Float>();

	Item it = new Item();
	Supplier s = new Supplier();
	ItemPrice ip = new ItemPrice();
	

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

		@Override
		public int hashCode() {
			return (int) id;
		}

		@Override
		public boolean equals(Object i) {
			Item item = (Item) i;
			return this.id == item.id;
		}

		@Override
		public int compareTo(Item item) {
			return (int) (this.id - item.id);

		}

		@Override
		public String toString() {
			return "Item [id=" + id + ", description=" + description + "]";
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

			// return (int) (this.vid-s.vid);
			int cmp= Float.compare(this.reputation, s.getReputation());
			
			return (int) (cmp==0?this.vid-s.vid:cmp);
		}

		@Override
		public boolean equals(Object obj) {

			Supplier s = (Supplier) obj;
			return this.vid == s.vid && this.reputation == s.reputation;
		}

		@Override
		public String toString() {
			return "Supplier [vid=" + vid + ", reputation=" + reputation + "]";
		}

	}

	public static class SupplierItemInfo implements Comparable<SupplierItemInfo> {

		@Override
		public String toString() {
			return "SupplierItemInfo [id=" + id + ", vid=" + vid + ", reputation=" + reputation + ", price=" + price
					+ "]";
		}



		private long id, vid;
		private float reputation;
		private int price;

		public SupplierItemInfo() {

		}

		public SupplierItemInfo(long id, long vid, float reputation, int price) {
			this.id = id;
			this.vid = vid;
			this.reputation = reputation;
			this.price = price;
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
		public int compareTo(SupplierItemInfo sio) {
			int reputationDiff = Float.compare(this.reputation, sio.getReputation());
			int priceDiff= ((reputationDiff == 0) ? Integer.compare(this.price, sio.getPrice()) : reputationDiff);
			return ((priceDiff==0))?Long.compare(this.vid, sio.vid):priceDiff;
			
			//return ((reputationDiff == 0) ? Integer.compare(this.price, sio.getPrice()) : reputationDiff);
			// return (int) (this.reputation-sio.reputation);
		}
		
		

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SupplierItemInfo other = (SupplierItemInfo) obj;
			if (price != other.price)
				return false;
			if (Float.floatToIntBits(reputation) != Float.floatToIntBits(other.reputation))
				return false;
			if (vid != other.vid)
				return false;
			return true;
		}

	}
	
	
	private class PriceComparator implements Comparator<SupplierItemInfo>{

		@Override
		public int compare(SupplierItemInfo sp1, SupplierItemInfo sp2) {
			
			int priceDiff = Float.compare(sp1.price, sp2.price);
			return ((priceDiff==0))?Long.compare(sp1.vid, sp2.vid):priceDiff;
			
			
//			if(sp1.getPrice() > sp2.getPrice()){
//				return 1;
//			}else{
//				return -1;
//			}
			
		}
		
	}
	
	

	public static class ItemPrice implements Comparable<ItemPrice> {
		@Override
		public String toString() {
			return "ItemPrice [id=" + id + ", price=" + price + "]";
		}

		private long id;
		private int price;

		public ItemPrice(long id, int price) {
			this.id = id;
			this.price = price;
		}

		public ItemPrice() {

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

		@Override
		public int compareTo(ItemPrice o) {

			return (int) (this.id - o.id);

		}
	}

	private class ItemPriceComp implements Comparator<ItemPrice>{

		@Override
		public int compare(ItemPrice ip1, ItemPrice ip2) {
			if(ip1.getPrice() > ip2.getPrice()){
				return 1;
			}else{
				return -1;
			}
		}
		
	}
	
	public static class ItemDescOccurence implements Comparable<ItemDescOccurence>{

		private long itemId;
		private int numOfOccurence;		
		
		public ItemDescOccurence(){
			
		}
		
		public ItemDescOccurence(long itemId, int numOfOccurence) {
			this.itemId = itemId;
			this.numOfOccurence = numOfOccurence;
		}
		
		public long getItemId(){
			return itemId;
		}
		
		public int getNumOfOccurence(){
			return numOfOccurence;
		}
		
		public void setItemId(long itemId){
			this.itemId = itemId;
		}
		
		public void setNumOfOccurence(int numOfOccurence){
			this.numOfOccurence = numOfOccurence;
		}
		
		@Override
		public int compareTo(ItemDescOccurence o) {
			return (int) (this.numOfOccurence - o.numOfOccurence)*-1;
		}
		

		@Override
		public boolean equals(Object obj) {

			ItemDescOccurence ido = (ItemDescOccurence) obj;
			return this.itemId == ido.itemId;
		}
		

		 @Override
		 public int hashCode()
		 {
		 return (int)itemId;
		 }
				
	}	

	private class OccComparator implements Comparator<ItemDescOccurence>{

		@Override
		public int compare(ItemDescOccurence sp1, ItemDescOccurence sp2) {
			
			//return Integer.compare(sp2.getNumOfOccurence(), sp1.getNumOfOccurence());
			
//			return sp1.getNumOfOccurence() > sp2.getNumOfOccurence() ? -1 :(sp1.getNumOfOccurence() < sp2.getNumOfOccurence() ? 1 : 0);
			
			
			if(sp1.getNumOfOccurence() < sp2.getNumOfOccurence()){
				return 1;
			}
			else{
				return -1;
			}
			
		}
		
	}
	
	public MultiDimensionalSearch() {

	}

	public boolean add(long id, Long[] description) {
		return addDescription(id, description);
	}

	private boolean addDescription(long id, Long[] descriptions) {

		it.setId(id);

		if (itemSupplierMap.containsKey(it)) {
			Item it1 = getItemDetails(it);
			TreeSet<SupplierItemInfo> supplierinfo = itemSupplierMap.get(it1);
			List<Long> itemDesc = it1.description;

			for (long desc : descriptions) {
				itemDesc.add(desc);

				if (itemDescription.containsKey(desc)) {

					HashSet<Long> idWithDescription = itemDescription.get(desc);

					idWithDescription.add(id); // check the existence of Id in
												// the HashSet before adding the
												// item id. ??
					itemDescription.put(desc, idWithDescription);

				}

				else {
					HashSet<Long> idWithDescription = new HashSet<Long>();
					idWithDescription.add(id);
					itemDescription.put(desc, idWithDescription);
				}

			}

			itemSupplierMap.put(it1, supplierinfo);

//			System.out.println("New desc added");
//			System.out.println(" Present Item Information");
//
//			for (Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo : itemSupplierMap.entrySet()) {
//				System.out.println("###");
//				System.out.println("id:" + iteminfo.getKey().id);
//
//				for (long d : iteminfo.getKey().description) {
//					System.out.println(d);
//				}
//
//			}
//
			return false;
		}

		else {

			Item item = new Item(id, descriptions);
			itemSupplierMap.put(item, new TreeSet<SupplierItemInfo>());

			for (long desc : descriptions) {

				if (itemDescription.containsKey(desc)) {
					HashSet<Long> ids = itemDescription.get(desc);
					ids.add(id);
					itemDescription.put(desc, ids); // check this line is
													// required or not??

				}

				else {
					HashSet<Long> idWithDescription = new HashSet<Long>();
					idWithDescription.add(id);
					itemDescription.put(desc, idWithDescription);

				}

			}

//			// Debug purpose
//			System.out.println(" new element added");
//			System.out.println(" New Item Information");
//
//			for (Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo : itemSupplierMap.entrySet()) {
//				System.out.println("###");
//				System.out.println("id:" + iteminfo.getKey().id);
//
//				for (long d : iteminfo.getKey().description) {
//					System.out.println(d);
//				}
//
//			}
//			/// End of debug
			return true;
		}

	}

	public boolean add(long vid, float reputation) {

		return addSupplier(vid, reputation);
	}

	private boolean addSupplier(long vid, float reputation) {

		Supplier[] supplierArr;
		float rep = 0.0f;

		if (suppplierIdReputation.containsKey(vid)) {
			rep = suppplierIdReputation.get(vid);
			s.setVid(vid);
			s.setReputation(rep);

			if (supplierItemMap.containsKey(s)) {

				Set<Supplier> supplierSet = supplierItemMap.keySet();
				supplierArr = new Supplier[supplierSet.size()];
				supplierSet.toArray(supplierArr);

				int index = BinarySearch.recursiveBinarySearch(supplierArr, s);
				Supplier supplier = supplierArr[index];

				//System.out.println("Supplier id:" + supplier.getVid());

				TreeSet<ItemPrice> itemPrice = supplierItemMap.get(supplier);
				//supplier.setReputation(reputation);
				supplierItemMap.remove(supplier);
				Supplier newSupp = new Supplier(supplier.getVid(), reputation);
				supplierItemMap.put(newSupp, itemPrice);

				suppplierIdReputation.put(supplier.getVid(), reputation);
//
//				System.out.println("Updated reputation of supplier");
//
//				for (Entry<Supplier, TreeSet<ItemPrice>> map : supplierItemMap.entrySet()) {
//					Supplier up = map.getKey();
//					TreeSet<ItemPrice> ip = map.getValue();
//
//					System.out.println("Supplier id:" + up.getVid());
//					System.out.println("Supplier Reputation:" + up.getReputation());
//
//					for (ItemPrice i : ip) {
//						System.out.println("Item id:" + i.getId());
//						System.out.println("Item Price:" + i.getPrice());
//					}
//
//				}

			} else {
				System.out.println("INCONSISTENT SUPPLIER MAP");
			}
			return false;
		}

		else {

			Supplier supplier = new Supplier(vid, reputation);
			supplierItemMap.put(supplier, new TreeSet<ItemPrice>());

			// adding id and reputation of Supplier in HashMap
			suppplierIdReputation.put(vid, reputation);

//			System.out.println("NEW supplier");
//
//			for (Entry<Supplier, TreeSet<ItemPrice>> map : supplierItemMap.entrySet()) {
//				Supplier up = map.getKey();
//				TreeSet<ItemPrice> ip = map.getValue();
//
//				System.out.println("Supplier id:" + up.getVid());
//				System.out.println("Supplier Reputation:" + up.getReputation());
//
//				for (ItemPrice i : ip) {
//					System.out.println("Item id:" + i.getId());
//					System.out.println("Item Price:" + i.getPrice());
//				}
//
//			}
			return true;
		}

	}

	// ----k -----
	public Long[] purge(float maxReputation) {
		// remove items having supplier <= maxReputation
		return purgeItems(maxReputation);

	}

	private Long[] purgeItems(float maxReputation) {

		/*
		 * private HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>(); private
		 * TreeMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new  HashMap<Item,TreeSet<SupplierItemInfo>>(); private
		 * TreeMap<Supplier,TreeSet<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		 */
		SupplierItemInfo sii = new SupplierItemInfo();
		sii.setReputation(maxReputation);
		ArrayList<Long> result = new ArrayList<Long>();
		
		for(Map.Entry<Item, TreeSet<SupplierItemInfo>> itemInfo : itemSupplierMap.entrySet()){
			Item item = itemInfo.getKey();
			TreeSet<SupplierItemInfo> value = itemInfo.getValue();
			
			NavigableSet<SupplierItemInfo> itemsWithReputation = value.tailSet(sii, false);
			if(itemsWithReputation.size() == 0){
				result.add(item.id);
				//remove(item.id);
			}		
			
		}
		
		for(Long i : result){
			remove(i);
		}
		
		
//		
//		Supplier sii = new Supplier();
//		sii.setReputation(maxReputation);
//
//		HashSet<Long> itemsRemoved = new HashSet<Long>();
//		// HashSet<Supplier> supplierRemoved = new HashSet<Supplier>();
//		HashSet<Long> descriptionChecked = new HashSet<Long>();
//
//		// get the items and suppliers where suppliers's reputation <=
//		// maxReputation
//		NavigableMap<Supplier, TreeSet<ItemPrice>> supplierResultMap = supplierItemMap.headMap(sii, true);
//
//		for (Entry<Supplier, TreeSet<ItemPrice>> supplierMaxReputation : supplierResultMap.entrySet()) {
//			Supplier supplierEntry = supplierMaxReputation.getKey();
//			TreeSet<ItemPrice> supplierItems = supplierMaxReputation.getValue();
//			// supplierRemoved.add(supplierEntry);
//			for (ItemPrice item : supplierItems) {
//				it.setId(item.id);
//
//				
//				itemsRemoved.add(item.id); // storing the items to be removed
////				supplierItems.remove(item);
//
//				// ----storing descriptions to be checked to remove item entry
//				// -------
//				
//				it.setId(item.id);
//				Item it1 = getItemDetails(it);
//				itemSupplierMap.remove(it); // remove the item entry from
//				// itemSupplierMap
//				for (Long d : it1.description) {
//					descriptionChecked.add(d);
//				}
//			}
//			supplierItems.clear();
//			
//			supplierItemMap.remove(supplierEntry.vid);
//		}
//
//		System.out.println("Items to be purged :" + itemsRemoved);
//
//		// -------------------checking for item entry only in stored
//		// description----------------
//		for (Long desc : descriptionChecked) {
//			if (itemDescription.containsKey(desc)) {
//				// check for presence of items removed , if so remove the item
//				// entry
//				for (Long id : itemsRemoved) {
//					if (itemDescription.get(desc).contains(id)) {
//						itemDescription.get(desc).remove(id);
//					}
//				}
//			}
//		}
//
		return result.toArray(new Long[result.size()]);
	}

	// --- l ---
	public Long remove(Long id) {
		return removeItem(id);
	}

	private Long removeItem(Long id) {

		/*
		 * private HashMap<Long,LinkedList<Long>> itemDescription = new
		 * HashMap<Long,LinkedList<Long>>(); private
		 * TreeMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new
		 * HashMap<Item,TreeSet<SupplierItemInfo>>(); private
		 * TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new
		 * TreeMap<Supplier,LinkedList<ItemPrice>>();
		 */
		long sumOfDescription = 0;
		// Item []arr;

		it.setId(id);

		if (itemSupplierMap.containsKey(it)) {

			/*
			 * Set<Item> itemSet= itemSupplierMap.keySet(); arr = new
			 * Item[itemSet.size()]; itemSet.toArray(arr);
			 * 
			 * int index=BinarySearch.recursiveBinarySearch(arr,it);
			 * 
			 * it=arr[index]; System.out.println("Id From binary Search:"+
			 * it.getId() + " and desc " + it.getDescription());
			 * TreeSet<SupplierItemInfo> supplierinfo= itemSupplierMap.get(it);
			 */

			Item it1 = getItemDetails(it);
			// Supplier supplierEntry;
			List<Long> desc = it1.description;

			for (Long d : desc) {
				itemDescription.get(d).remove(id);

			}

			HashSet<Long> removeItemsForSupplier = new HashSet<Long>();

			// get the suppliers info for the item to be removed
			TreeSet<SupplierItemInfo> supplierValue = itemSupplierMap.get(it1);
			for (SupplierItemInfo s : supplierValue) {
				removeItemsForSupplier.add(s.vid);
			}

			// remove entry from itemSupplierMap
			itemSupplierMap.remove(it1);

			// remove the items with thier description from the
			// itemDescriptionMap
			// for(Entry<Long, HashSet<Long>> descItem :
			// itemDescription.entrySet()){
			// //check for item id to be removed
			// if(descItem.getValue().contains(it1.getId())){
			// descItem.getValue().remove(it1.getId());
			// }
			// }

			// check for suppliers to be removed, find the item entry and remove
			// item from supplierItemMap
			for (Long supp : removeItemsForSupplier) {
				// supplierEntry = new Supplier();
				s.setVid(supp);
				s.setReputation(suppplierIdReputation.get(supp));
				if (supplierItemMap.containsKey(s)) {
					TreeSet<ItemPrice> info = supplierItemMap.get(s);
					ItemPrice dummyIP = new ItemPrice();
					dummyIP.setId(id);
					info.remove(dummyIP);
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

		ItemPrice[] itemPriceArr;

		TreeSet<SupplierItemInfo> supplierItemSet;
		float rep = 0.0f;

		if (suppplierIdReputation.containsKey(vid)) {
			rep = suppplierIdReputation.get(vid);

			s.setVid(vid);
			s.setReputation(rep);
			if (supplierItemMap.containsKey(s)) {


				Supplier supplier = s;

				TreeSet<ItemPrice> ipSet = supplierItemMap.get(supplier);

				for (Pair p : idPrice) {

					it.setId(p.id);

					if (itemSupplierMap.containsKey(it)) {

						isnewProd = true;
						Item item;
						item = getItemDetails(it);

						ip.setId(p.id);
						if (ipSet.contains(ip)) {

							itemPriceArr = new ItemPrice[ipSet.size()];
							ipSet.toArray(itemPriceArr);

							int ipIndex = BinarySearch.recursiveBinarySearch(itemPriceArr, ip);
							ItemPrice itemPrice = itemPriceArr[ipIndex];
							int oldItemPrice = itemPrice.getPrice();
							itemPrice.setPrice(p.price);
							isnewProd = false;

							SupplierItemInfo si = new SupplierItemInfo(p.id, vid, supplier.getReputation(),
									oldItemPrice);

							// it.setId(p.id);

							// if (itemSupplierMap.containsKey(it)) {

							supplierItemSet = itemSupplierMap.get(item);

							// if(item.getId()==itemPrice.getId())

							supplierItemSet.remove(si);
							si.setPrice(p.price);
							supplierItemSet.add(si);

							itemSupplierMap.put(item, supplierItemSet);

							// }
							// else
							// {
							// item= new Item();
							// item.setId(p.id);
							// supplierItemSet = new TreeSet<>();
							// supplierItemSet.add(si);
							// System.out.println("UNKNOWN ITEM");

							// }
							// break;
						}

						else if (isnewProd) {

							if(vid==4543 && p.id==9891)
							{
								System.out.println("");
							}
							
							newProductscount++;
							ItemPrice newItemPrice = new ItemPrice(p.id, p.price);
							ipSet.add(newItemPrice);

							SupplierItemInfo si = new SupplierItemInfo(p.id, vid, supplier.getReputation(), p.price);

							TreeSet<SupplierItemInfo> supplierInfoSet = itemSupplierMap.get(item);
							supplierInfoSet.add(si);

							
							
							itemSupplierMap.put(item, supplierInfoSet);
						}

					} else {
						Item item= new Item();
						 item.setId(p.id);
						 ipSet.add(new ItemPrice(p.id,p.price));
						newProductscount++; 
						 supplierItemSet = new TreeSet<>();
						SupplierItemInfo si = new SupplierItemInfo(p.id, vid, supplier.getReputation(), p.price);
						supplierItemSet.add(si);
						itemSupplierMap.put(item, supplierItemSet);
						//System.out.println("UNKNOWN ITEM:" + it.getId());
					}

				}
				supplierItemMap.put(supplier, ipSet);
			}

		}

		else
			{ 
			
			//System.out.println("UNKNOWN SUPPLIER:" + vid);
		
		 Supplier supplier = new Supplier(vid, 0.0f);
		
		 TreeSet<ItemPrice> itemPriceSet = new TreeSet<>();
		 Item ite;
		
		
		 for (Pair p : idPrice) {
		 itemPriceSet.add(new ItemPrice(p.id, p.price));
		 
		
		 it.setId(p.id);
		
		 if (itemSupplierMap.containsKey(it)) {
		
		 ite=getItemDetails(it);
		
		 SupplierItemInfo sio= new SupplierItemInfo(p.id,vid,0.0f,p.price);
		 TreeSet<SupplierItemInfo> supplierItemInfoSet=itemSupplierMap.get(ite);
		
		 supplierItemInfoSet.add(sio);
		 itemSupplierMap.put(ite, supplierItemInfoSet);
		 newProductscount++;
		
		 }
		 else
		 {
			newProductscount++;
		 TreeSet<SupplierItemInfo> supplierItemInfoSet= new TreeSet<SupplierItemInfo>();
		 ite= new Item();
		 ite.setId(p.id);
		
		 SupplierItemInfo sio= new SupplierItemInfo(p.id,vid,0.0f,p.price);
		 supplierItemInfoSet.add(sio);
		
		 itemSupplierMap.put(ite, supplierItemInfoSet);
		 }
	 }
		
		 supplierItemMap.put(supplier, itemPriceSet);
		 suppplierIdReputation.put(vid,0.0f);
		 }

		return newProductscount;

	}

	// --- m ---
	public int remove(Long id, Long[] arr) {

		// return 0;
		return removeItemDesc(id, arr);
	}

	private int removeItemDesc(Long id, Long[] arr) {
		int numOfElementsRemoved = 0;

		// remove for item id , all desc in arr
		for (Long desc : arr) {
			if (itemDescription.containsKey(desc)) {
				// LinkedList<Long> items = new LinkedList<Long>();
				if (itemDescription.get(desc).contains(id)) {
					itemDescription.get(desc).remove(id);
					numOfElementsRemoved++;
				}
			}
		}

		// remove arr elements from description in itemSupplierMap
		it.setId(id);

		if (itemSupplierMap.containsKey(it)) {

			Item it1 = getItemDetails(it);
			/*
			 * Set<Item> itemSet= itemSupplierMap.keySet(); arr1 = new
			 * Item[itemSet.size()]; itemSet.toArray(arr1);
			 * 
			 * int index=BinarySearch.recursiveBinarySearch(arr1,it);
			 * it=arr1[index]; System.out.println("Id From binary Search:"+
			 * it.getId() + " and desc " + it.getDescription());
			 */
			List<Long> desc = it1.description;
			for (Long d : arr) {
				if (desc.contains(d)) {
					desc.remove(d);
				}
			}
		} else {
			System.out.println("Item not found ");
			return 0;
		}
		return numOfElementsRemoved;
	}

	// --- n ---
	public int removeAll(Long[] arr) {
		return removeAllFromItemDesc(arr);
	}

	public int removeAllFromItemDesc(Long[] arr) {
		int countOfItems = 0;
		// remove all items associated with desc in arr in itemDescriptionMap
		for (Long i : arr) {
			if (itemDescription.containsKey(i)) {
				itemDescription.remove(i);
			}
		}
		// remove desc in arr from all items in itemSupplierMap
		for (Map.Entry<Item, TreeSet<SupplierItemInfo>> itemSupplier : itemSupplierMap.entrySet()) {
			int countOfDescInArr = 0;
			Item item = itemSupplier.getKey();
			for (Long desc : arr) {
				if (item.description.contains(desc)) {
					countOfDescInArr++;
					item.description.remove(desc);
				}
			}
			if (countOfDescInArr >= 1) {
				countOfItems++;
			}
		}
		return countOfItems;
	}

	// --- j ---
	public int invoice(Long[] arr, float minReputation) {
		TreeSet<SupplierItemInfo> supplierInfo;
		SupplierItemInfo sii = new SupplierItemInfo();
		sii.setReputation(minReputation);
		List<SupplierItemInfo> supplierWithReputation = new ArrayList<SupplierItemInfo>();
		SupplierItemInfo[] suppliersByPrice; 
		
		int minPrice = 0;
		int sumOfMinPrice = 0;

		for (Long id : arr) {

			it.setId(id);
			if (itemSupplierMap.containsKey(it)) {
				supplierInfo = itemSupplierMap.get(it);
				
				// suppliers for the item whose reputation >= minReputation
				NavigableSet<SupplierItemInfo> result = supplierInfo.tailSet(sii, true);
				
				for(SupplierItemInfo s : result){
					supplierWithReputation.add(s);
				}	
				
				suppliersByPrice = sortByPrice(supplierWithReputation); 
				
				if (suppliersByPrice.length > 0) {
					minPrice = suppliersByPrice[0].price;
					int i = 0; 
					while(i < suppliersByPrice.length && suppliersByPrice[i].price == minPrice ){
						sumOfMinPrice += suppliersByPrice[i++].price;
					}
					
				}
			}
		}
		return sumOfMinPrice;
	}

	private Item getItemDetails(Item it) {
		Item[] arr1;
		//System.out.println("Item " + it.id + " present ");

		Set<Item> itemSet = itemSupplierMap.keySet();
		arr1 = new Item[itemSet.size()];
		itemSet.toArray(arr1);

		int index = BinarySearch.recursiveBinarySearch(arr1, it);
		it = arr1[index];
		//System.out.println("Id From binary Search:" + it.getId() + " and desc " + it.getDescription());
		return it;
	}

	public void printItems() {

		System.out.println("\n--------- Present Item Information \n item supplier map -----------");
		for (Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo : itemSupplierMap.entrySet()) {
			System.out.println("###");
			System.out.println("id:" + iteminfo.getKey().id);

			if (iteminfo.getKey().description != null) {
				for (long d : iteminfo.getKey().description) {
					System.out.println(d);
				}
			} else {
				System.out.println("No description");
			}

			TreeSet<SupplierItemInfo> supplierInfo = iteminfo.getValue();

			System.out.println("Value of Item--Supplier Map");
			System.out.println("-------");

			for (SupplierItemInfo sii : supplierInfo) {	
				System.out.println("Supplier id:" + sii.getVid());
				System.out.println("Supplier Reputation:" + sii.getReputation());
				System.out.println("Item id:" + sii.getId());
				System.out.println("Item Price:" + sii.getPrice());
			}

		}

		System.out.println("\n---------   supplier item map -----------");

		for (Entry<Supplier, TreeSet<ItemPrice>> map : supplierItemMap.entrySet()) {
			Supplier up = map.getKey();
			TreeSet<ItemPrice> ip = map.getValue();

			System.out.println("Supplier id:" + up.getVid());
			System.out.println("Supplier Reputation:" + up.getReputation());

			for (ItemPrice i : ip) {
				System.out.println("Item id:" + i.getId());
				System.out.println("Item Price:" + i.getPrice());
			}

		}

	}

	// --- g ---
		public Long[] findSupplier(Long id) {
			return  findSupplierForId(id);
		}

		private Long[] findSupplierForId(Long id){
			//HashSet<Long> supplierResult = new HashSet<Long>();
			List<SupplierItemInfo> supplierResult = new ArrayList<SupplierItemInfo>();
			TreeSet<SupplierItemInfo> supplierInfo ;
			SupplierItemInfo[] suppliersByPrice; 
			Long[] resultArr;
			int i = 0;
			
			it.setId(id);
				
			if(itemSupplierMap.containsKey(it)){
				supplierInfo = itemSupplierMap.get(it);  
				for(SupplierItemInfo s : supplierInfo){
					supplierResult.add(s);
				}				
			}
			
			suppliersByPrice = sortByPrice(supplierResult); 			
					
//			Collections.sort(supplierResult, new PriceComparator());
			resultArr = new Long[supplierResult.size()];
			
			for(SupplierItemInfo supplier : suppliersByPrice){
				resultArr[i++] = supplier.getVid();
			}
			
			return resultArr;		
			
		}
		
		private SupplierItemInfo[] sortByPrice(List<SupplierItemInfo> supplierResult){
			SupplierItemInfo[] resultArr;
			int i = 0;
			
			Collections.sort(supplierResult, new PriceComparator());
			resultArr = new SupplierItemInfo[supplierResult.size()];
			
			for(SupplierItemInfo supplier : supplierResult){
				resultArr[i++] = supplier;
			}
					
			return resultArr;
			
		}
		
		// --- h ---
		public Long[] findSupplier(Long id, float minReputation){
			return findSupplierForId(id, minReputation);
		}
		
		private Long[] findSupplierForId(Long id, float minReputation) {

			TreeSet<SupplierItemInfo> supplierInfo ;
			List<SupplierItemInfo> supplierWithReputation = new ArrayList<SupplierItemInfo>();
			Long[] resultArr;
			SupplierItemInfo[] suppliersByPrice; 
			int i = 0;
			
			SupplierItemInfo sii = new SupplierItemInfo();
			sii.setReputation(minReputation);
			it.setId(id);
			
			if(itemSupplierMap.containsKey(it)){
				supplierInfo = itemSupplierMap.get(it);
				
				// suppliers for the item whose reputation >= minReputation
				NavigableSet<SupplierItemInfo> result = supplierInfo.tailSet(sii, true);
				
				for(SupplierItemInfo s : result){
					supplierWithReputation.add(s);
				}				
			}
			
			suppliersByPrice= sortByPrice(supplierWithReputation);
			
//			Collections.sort(supplierWithReputation, new PriceComparator());
			resultArr = new Long[supplierWithReputation.size()];
			
			for(SupplierItemInfo supplier : suppliersByPrice){
				resultArr[i++] = supplier.getVid();
			}
			
			
			return resultArr;	
			
			//return supplierResult.toArray(new Long[supplierResult.size()]);
			
		}

		public Long[] findItem(Long[] arr) {
			return findItemWithGivenDesc(arr);
		}

		private Long[] findItemWithGivenDesc(Long[] arr){
			
//			private HashMap<Long, HashSet<Long>> itemDescription = new HashMap<Long, HashSet<Long>>();// desc, list of Item id's, Replace LL to HashSet
//			private TreeMap<Item, TreeSet<SupplierItemInfo>> itemSupplierMap = new TreeMap<Item, TreeSet<SupplierItemInfo>>();
//			private TreeMap<Supplier, TreeSet<ItemPrice>> supplierItemMap = new TreeMap<Supplier, TreeSet<ItemPrice>>(); // Replace LL to Treeset
			
			ItemDescOccurence itd;
			HashSet<Long> items;
			
			HashMap<Long, Integer> idOccurenceMap = new HashMap<Long, Integer>();		
			//ItemDescOccurence[] idOccArr;
			//ItemDescOccurence[] resultArr = null;
			Long[] resultArr;
			List<ItemDescOccurence> idOccList = new ArrayList<ItemDescOccurence>();
			int i = 0;
			
			// store id, occurence in idOccurenceMap		
			for(Long desc : arr){
				items = itemDescription.get(desc);
				if(items!=null){
				
				for(Long item : items){
					if(!idOccurenceMap.containsKey(item)){
						idOccurenceMap.put(item, 1);
					}else{
						idOccurenceMap.put(item, idOccurenceMap.get(item)+1);
					}				
				}
				}
			}
		
			// store the entries of the hashmap in the array
			//idOccArr = new ItemDescOccurence[idOccurenceMap.size()]; 
					
			for(Map.Entry<Long, Integer> idOccurence : idOccurenceMap.entrySet()){
				itd = new ItemDescOccurence();
				itd.setItemId(idOccurence.getKey());
				itd.setNumOfOccurence(idOccurence.getValue());
				idOccList.add(itd);
				//idOccArr[i++] = itd;	
				
			}
			
			Collections.sort(idOccList, new OccComparator());
			
			resultArr = new Long[idOccList.size()];
			for(ItemDescOccurence it : idOccList){
				resultArr[i++] = it.getItemId();
			}
			
			//resultArr = Arrays.copyOf(idOccArr, idOccArr.length);
			// sort based on the num of Occurence
			
			//MergeSort.mergeSort(idOccArr, resultArr);
			//finalResultArr = new Long[idOccArr.length];
			
			
			return resultArr;
		}
			
		
		public Long[] findItem(Long n, int minPrice, int maxPrice, float minReputation){
			return findItemGivenCriteria(n, minPrice, maxPrice, minReputation);
		}

		private Long[] findItemGivenCriteria(Long n, int minPrice, int maxPrice, float minReputation) {
			HashSet<Long> listOfItems = null;
			TreeSet<SupplierItemInfo> supplierInfo ;
			SupplierItemInfo sii = new SupplierItemInfo();
			sii.setReputation(minReputation);
			
			TreeSet<ItemPrice> itemPrice = new TreeSet<ItemPrice>(new ItemPriceComp());
			Long resultItemArr[];
			
			// get the list of items for given n
			if(itemDescription.containsKey(n)){
				listOfItems = itemDescription.get(n);
				int tempMinPrice;
				// for each item find the suppliers meeting given criteria
				for(Long itemId : listOfItems){
					it.setId(itemId);
					tempMinPrice = Integer.MAX_VALUE;
					// get the supplier item info for that item
					supplierInfo = itemSupplierMap.get(it);
					// get only the supplies who meets the given minReputation
//					if(supplierInfo.size()>0)
				//{
					NavigableSet<SupplierItemInfo> suppliersWithMinReputation = supplierInfo.tailSet(sii, true);
					// get only the suppliers with given price range 
					if(suppliersWithMinReputation.size()>0)
					{
					for(SupplierItemInfo s : suppliersWithMinReputation ){
						if(s.price >= minPrice && s.price <= maxPrice){
							//itemPrice.add(new ItemPrice(s.id, s.price));
							if(tempMinPrice > s.price){
								tempMinPrice = s.price;
							}
							
						}
					}			
					//for(supplierInPriceRange  )		
					itemPrice.add(new ItemPrice(itemId, tempMinPrice));
					}
					//}
				}
				resultItemArr = new Long[itemPrice.size()];
				ItemPrice[] tmpArray = itemPrice.toArray(new ItemPrice[itemPrice.size()]);
				
				for(int i =0; i <itemPrice.size(); i++){
					resultItemArr[i] = tmpArray[i].id;
				}
				return resultItemArr;

			}
			return null;
			
		}

		public Long[] description(Long id) {
			
			return getDescription(id);
	
		}

		private Long[] getDescription(Long id) {
			
			it.setId(id);

			if (itemSupplierMap.containsKey(it)) {
			
			
			Item item = getItemDetails(it);
			Long[] descriptions= new Long[item.getDescription().size()];
			item.getDescription().toArray(descriptions);
			return descriptions;
			}
			else
			{
			return null;
			}
	}
}