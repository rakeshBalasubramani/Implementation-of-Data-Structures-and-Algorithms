package cs6301.g38;
 
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

	private HashMap<Long, HashSet<Long>> itemDescription = new HashMap<Long, HashSet<Long>>();// desc, list of Item id's, Replace LL to HashSet
	private TreeMap<Item, TreeSet<SupplierItemInfo>> itemSupplierMap = new TreeMap<Item, TreeSet<SupplierItemInfo>>();
	private TreeMap<Supplier, TreeSet<ItemPrice>> supplierItemMap = new TreeMap<Supplier, TreeSet<ItemPrice>>(); // Replace LL to Treeset
	private HashMap<Long,Float> suppplierIdReputation = new HashMap<Long,Float>();
	
	Item it = new Item() ;
	Supplier  s = new Supplier();
	ItemPrice ip= new ItemPrice();

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
		 public int hashCode()
		 {
		 return (int)id;
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
			
			//return (int) (this.vid-s.vid);
			return Float.compare(this.reputation, s.getReputation());
		}

		@Override
		public boolean equals(Object obj) {

			Supplier s = (Supplier) obj;
			return this.vid == s.vid && this.reputation==s.reputation;
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

	public static class ItemPrice implements Comparable<ItemPrice> {
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

	public MultiDimensionalSearch() {

	}

	public boolean add(long id, Long[] description) {
		return addDescription(id, description);
	}

	private boolean addDescription(long id, Long[] descriptions) {

		it.setId(id);

		 if(itemSupplierMap.containsKey(it))
		 {			 
			 Item it1 = getItemDetails(it);
			 TreeSet<SupplierItemInfo> supplierinfo= itemSupplierMap.get(it1);
			 List<Long> itemDesc=it1.description;
			 
			 for(long desc: descriptions)
			 {
				 itemDesc.add(desc);
				 
				 if(itemDescription.containsKey(desc))
				 {

					 HashSet<Long> idWithDescription=itemDescription.get(desc);
					 
					 idWithDescription.add(id); // check the existence of Id in the HashSet before adding the item id. ??
					 itemDescription.put(desc, idWithDescription);
					 
				 }
				 
				 else
				 {
					 HashSet<Long> idWithDescription= new HashSet<Long>();
					 idWithDescription.add(id);
					 itemDescription.put(desc,idWithDescription);
				 }
				 
			 }
			 
			 itemSupplierMap.put(it1,supplierinfo);
			 
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
			
			 Item item = new Item(id, descriptions);
			itemSupplierMap.put(item, new TreeSet<SupplierItemInfo>());

			for (long desc : descriptions) {
				
				if (itemDescription.containsKey(desc)) {
					HashSet<Long> ids = itemDescription.get(desc);
					ids.add(id);
					itemDescription.put(desc, ids); // check this line is required or not??

				}

				else {
					HashSet<Long> idWithDescription = new HashSet<Long>();
					idWithDescription.add(id);
					itemDescription.put(desc, idWithDescription);

				}

			}

		 // Debug purpose
			System.out.println(" new element added");
			System.out.println(" New Item Information");

			for (Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo : itemSupplierMap.entrySet()) {
				System.out.println("###");
				System.out.println("id:" + iteminfo.getKey().id);

				for (long d : iteminfo.getKey().description) {
					System.out.println(d);
				}

			}
         /// End of debug
			return true;
		}

	}

	public boolean add(long vid, float reputation) {

		return addSupplier(vid, reputation);
	}

	private boolean addSupplier(long vid, float reputation) {

		Supplier[] supplierArr;
		float rep=0.0f;
		
		if(suppplierIdReputation.containsKey(vid))
		{
			rep=suppplierIdReputation.get(vid);
			s.setVid(vid);
			s.setReputation(rep);		

			if (supplierItemMap.containsKey(s)) {
				
				Set<Supplier> supplierSet = supplierItemMap.keySet();
				supplierArr = new Supplier[supplierSet.size()];
				supplierSet.toArray(supplierArr);

				int index = BinarySearch.recursiveBinarySearch(supplierArr, s);
				Supplier supplier = supplierArr[index];

				System.out.println("Supplier id:" + supplier.getVid());

				TreeSet<ItemPrice> itemPrice = supplierItemMap.get(supplier);
				supplier.setReputation(reputation);
				supplierItemMap.put(supplier, itemPrice);
				
				suppplierIdReputation.put(supplier.getVid(), reputation);
				
				System.out.println("Updated reputation of supplier");

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
			else
			{
				System.out.println("INCONSISTENT SUPPLIER MAP");
			}
			return false;
		}
	
		else {
			
			Supplier supplier = new Supplier(vid, reputation);
			supplierItemMap.put(supplier, new TreeSet<ItemPrice>());
			
			//adding id and reputation of Supplier in HashMap
			suppplierIdReputation.put(vid, reputation);
			
			System.out.println("NEW supplier");

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
		private TreeMap<Supplier,TreeSet<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		*/
		Supplier sii = new Supplier();
		sii.setReputation(maxReputation);
		
		HashSet<Long> itemsRemoved = new HashSet<Long>();
		
//HashSet<Supplier> supplierRemoved = new HashSet<Supplier>();
		HashSet<Long> descriptionChecked = new HashSet<Long>();
		
		
		// get the items and suppliers where suppliers's reputation <= maxReputation
		NavigableMap<Supplier, TreeSet<ItemPrice>> supplierResultMap = supplierItemMap.headMap(sii, true);
				
		for (Entry<Supplier, TreeSet<ItemPrice>> supplierMaxReputation : supplierResultMap.entrySet()) {
			Supplier supplierEntry = supplierMaxReputation.getKey();
			TreeSet<ItemPrice> supplierItems = supplierMaxReputation.getValue();
			// supplierRemoved.add(supplierEntry);
			for (ItemPrice item : supplierItems) {
				itemSupplierMap.remove(item.id);   // remove the item entry from itemSupplierMap
				itemsRemoved.add(item.id);	// storing the items to be removed 			
				supplierItems.remove(item);				
				
				
				//----storing descriptions to be checked to remove item entry -------
				it = new Item();
				it.setId(item.id);
				Item it1 = getItemDetails(it);
				for(Long d : it1.description){
					descriptionChecked.add(d);
				}				
			}
			supplierItemMap.remove(supplierEntry.vid);
		}
		

		System.out.println("Items to be purged :" + itemsRemoved);
		
		// -------------------checking for item entry only in stored description----------------
		for(Long desc : descriptionChecked){
			if(itemDescription.containsKey(desc)){
				//check for presence of items removed , if so remove the item entry 
				for(Long id : itemsRemoved){
					if(itemDescription.get(desc).contains(id)){
						itemDescription.get(desc).remove(id);
					}
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
			
			it.setId(id);
			 
			if(itemSupplierMap.containsKey(it))
			{
				 

			
				Item it1 = getItemDetails(it);
				//Supplier supplierEntry;
				List<Long> desc =it1.description;
				
				for(Long d: desc)
				{
					itemDescription.get(d).remove(id);
					
				}
				
				HashSet<Long> removeItemsForSupplier = new HashSet<Long>();
			// remove the items with thier description from the itemDescriptionMap
//			for(Entry<Long, HashSet<Long>> descItem : itemDescription.entrySet()){
//				//check for item id to be removed				
//				if(descItem.getValue().contains(it1.getId())){
//						descItem.getValue().remove(it1.getId());
//				}
//			}

			// check for suppliers to be removed, find the item entry and remove item from supplierItemMap
			for(Long supp : removeItemsForSupplier){
				//supplierEntry = new Supplier();
				s.setVid(supp);
				if(supplierItemMap.containsKey(s)){
					TreeSet<ItemPrice> info = supplierItemMap.get(s);
					ItemPrice dummyIP = new ItemPrice();
					dummyIP.setId(id);
					info.remove(dummyIP);					
				}				
			}			
			
				// get the suppliers info for the item to be removed
				TreeSet<SupplierItemInfo> supplierValue = itemSupplierMap.get(it1); 
				for(SupplierItemInfo s : supplierValue){
					removeItemsForSupplier.add(s.vid);
				}			
					
				// remove  entry from itemSupplierMap
				itemSupplierMap.remove(it1);  
						
				// remove the items with thier description from the itemDescriptionMap
//				for(Entry<Long, HashSet<Long>> descItem : itemDescription.entrySet()){
//					//check for item id to be removed				
//					if(descItem.getValue().contains(it1.getId())){
//							descItem.getValue().remove(it1.getId());
//					}
//				}

				// check for suppliers to be removed, find the item entry and remove item from supplierItemMap
				for(Long supp : removeItemsForSupplier){
					//supplierEntry = new Supplier();
					s.setVid(supp);
					if(supplierItemMap.containsKey(s)){
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
			float rep=0.0f;
			
			if(suppplierIdReputation.containsKey(vid))
			{
				rep=suppplierIdReputation.get(vid);
				
				s.setVid(vid);
				s.setReputation(rep);
				if (supplierItemMap.containsKey(s)) {
					
//					Set<Supplier> supplierSet = supplierItemMap.keySet();
	//
//					supplierArr = new Supplier[supplierSet.size()];
//					supplierSet.toArray(supplierArr);
	//
//					int sIndex = BinarySearch.recursiveBinarySearch(supplierArr, s);
//					Supplier supplier = supplierArr[sIndex];

					Supplier supplier=s;
					
					TreeSet<ItemPrice> ipSet = supplierItemMap.get(supplier);

					for (Pair p : idPrice) {
						

						it.setId(p.id);

						if (itemSupplierMap.containsKey(it)) {
							
							
							isnewProd = true;
							Item item;
							ip.setId(p.id);
							item=getItemDetails(it);

							if(ipSet.contains(ip)){
								
								
								itemPriceArr = new ItemPrice[ipSet.size()];
								ipSet.toArray(itemPriceArr);

								int ipIndex = BinarySearch.recursiveBinarySearch(itemPriceArr,ip);
								ItemPrice itemPrice = itemPriceArr[ipIndex];
								int oldItemPrice = itemPrice.getPrice();
								itemPrice.setPrice(p.price);
								isnewProd = false;

									SupplierItemInfo si = new SupplierItemInfo(p.id, vid, supplier.getReputation(), oldItemPrice);

							//		it.setId(p.id);

								//	if (itemSupplierMap.containsKey(it)) {

										supplierItemSet= itemSupplierMap.get(item);
										
										//if(item.getId()==itemPrice.getId())
										
										
										supplierItemSet.remove(si);
										si.setPrice(p.price);
										supplierItemSet.add(si);
										
										itemSupplierMap.put(item, supplierItemSet);

										
									//}
								//	else 
								//	{
//										item= new Item();
//										item.setId(p.id);
//										supplierItemSet = new TreeSet<>();
//										supplierItemSet.add(si);
									//	System.out.println("UNKNOWN ITEM");
										
								//	}
								//	break;
								}

							else if (isnewProd) {
									
									newProductscount++;
									ItemPrice newItemPrice = new ItemPrice(p.id, p.price);
									ipSet.add(newItemPrice);
									
									SupplierItemInfo si = new SupplierItemInfo(p.id, vid, supplier.getReputation(), p.price);
									
								TreeSet<SupplierItemInfo> supplierInfoSet=	itemSupplierMap.get(item);
									supplierInfoSet.add(si);
									
									
									itemSupplierMap.put(item, supplierInfoSet);
								}
							
							
						}
						else
						{
//							item= new Item();
//							item.setId(p.id);
//							supplierItemSet = new TreeSet<>();
//							supplierItemSet.add(si);
							System.out.println("UNKNOWN ITEM:" +it.getId());
						}

			
						}
					supplierItemMap.put(supplier, ipSet);
					}
					
			} 

		
			else 
//			{
//				
//				
				System.out.println("UNKNOWN SUPPLIER:"+ vid);
//				
//				
//				Supplier supplier = new Supplier(vid, 0.0f);
//
//				TreeSet<ItemPrice> itemPriceSet = new TreeSet<>();
//				Item ite;
//				
//
//				for (Pair p : idPrice) {
//					itemPriceSet.add(new ItemPrice(p.id, p.price));
//					newProductscount++;
//					
//					it.setId(p.id);
//
//					if (itemSupplierMap.containsKey(it)) {
// 
//						ite=getItemDetails(it);
//
//						SupplierItemInfo sio= new SupplierItemInfo(p.id,vid,0.0f,p.price);
//						TreeSet<SupplierItemInfo> supplierItemInfoSet= itemSupplierMap.get(ite);
//						
//							supplierItemInfoSet.add(sio);
//							itemSupplierMap.put(ite, supplierItemInfoSet);
//						
//						
//					}
//					else 
//					{
//						TreeSet<SupplierItemInfo> supplierItemInfoSet= new TreeSet<SupplierItemInfo>();
//						ite= new Item();
//						ite.setId(p.id);
//						
//						SupplierItemInfo sio= new SupplierItemInfo(p.id,vid,0.0f,p.price);
//						supplierItemInfoSet.add(sio);
//						
//						itemSupplierMap.put(ite, supplierItemInfoSet);
//					}
//					
//
//				}
//
//				supplierItemMap.put(supplier, itemPriceSet);
//			}

			return newProductscount;

		}

		// --- m ---
		public int remove(Long id, Long[ ] arr) {
			
	    	//return 0;
	    	return removeItemDesc(id, arr);
	    }

		private int removeItemDesc(Long id, Long[] arr){
			
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
			return removeAllFromItemDesc(arr);
		}
		
		
		public int removeAllFromItemDesc(Long[] arr){
			
			int countOfItems = 0;		
			// remove all items associated with desc in arr in itemDescriptionMap
			for(Long i : arr){
				if(itemDescription.containsKey(i)){
					itemDescription.remove(i);
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
			TreeSet<SupplierItemInfo> supplierInfo ;
			SupplierItemInfo sii = new SupplierItemInfo();
			sii.setReputation(minReputation);
			
			int sumOfMinPrice = 0;
			
			for(Long id : arr){	
					
				it.setId(id);
				if(itemSupplierMap.containsKey(it)){
					//Item it1 = getItemDetails(it);
				//	System.out.println("item details from func " + it1.id + " desc " + it1.description );
					supplierInfo = itemSupplierMap.get(it);
					System.out.println("\t supplier info for item " + it.id + " = " + supplierInfo);
					
					// suppliers for the item whose reputation >= minReputation
					NavigableSet<SupplierItemInfo> result =	supplierInfo.tailSet(sii, true);
					//the min price of the item whose supplier >= minReputation
					if(result.size() >=1){
						SupplierItemInfo firstElement = result.first();
						sumOfMinPrice += firstElement.price;
					}
				}			
			}				
			return sumOfMinPrice;
		}
		
		
		
		private Item getItemDetails(Item it){
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
				 System.out.println("###");
				 System.out.println("id:"+iteminfo.getKey().id);
				
				 if(iteminfo.getKey().description!=null)
				 {
					 for(long d : iteminfo.getKey().description){
						 System.out.println(d);
					 }
				 }
					 else
					 {
						 System.out.println("No description");
					 }
				  
				 TreeSet<SupplierItemInfo> supplierInfo=iteminfo.getValue(); 
				 
				 System.out.println("Value of Item--Supplier Map");
				 System.out.println("-------");
				 
				 for(SupplierItemInfo sii:supplierInfo)
				 {
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
		
	}	