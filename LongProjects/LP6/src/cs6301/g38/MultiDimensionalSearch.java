package cs6301.g38;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class MultiDimensionalSearch {
	
	public  static HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>();
	public static  HashMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
	public  static TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
	//Item it= new Item();
	//Supplier s= new Supplier();
	
	public static class Item
	{
		private long id;
		private List<Long> description;
		
		public Item()
		{
			
		}
		
		public Item(long id, Long[] desc)
		{
			this.id=id;
			description=new LinkedList<Long>();
			
			for(long d:desc)
			{
				description.add(d);
			}
		}
		
		
		@Override
		public int hashCode()
		{
			return (int)id;
		}
		
		@Override
		public boolean equals(Object i)
		{
			Item item=(Item) i;
			return this.id==item.id;
		}
	}
	
	public static class Supplier implements Comparable<Supplier>
	{
		private long vid;
		private float reputation;
		
		public Supplier()
		{
			
		}
		public Supplier(long vid, float reputation)
		{
			this.vid=vid;
			this.reputation=reputation;
		}
		
		public int compareTo(Supplier s)
		{
			return (int) (this.reputation-s.reputation);
		}
		
		@Override
		public boolean equals(Object obj) {
			
			Supplier s= (Supplier) obj;
			return this.vid==s.vid;
		}
		
	}
	
	public static class SupplierItemInfo implements Comparable<SupplierItemInfo>
	{
		private long id,vid;
		private float reputation;
		private int price;
		
		public SupplierItemInfo(long id, long vid, float reputation, int price)
		{
			this.id=id;
			this.vid=vid;
			this.reputation=reputation;
			this.price=price;
		}
		
		public int compareTo(SupplierItemInfo sio)
		{
			return (int) (this.reputation-sio.reputation);
		}
		
	}

	public static class ItemPrice
	{
		private long id;
		private int price;
		
		public ItemPrice (long id, int price)
		{
			this.id=id;
			this.price=price;
		}
	}
	
	
	public MultiDimensionalSearch()
	{
		
	}
	
	public boolean add(long id, Long[] description){
		return addDescription(id, description);
	}
	
	private boolean addDescription(long id, Long[ ] description) {
		Item it= new Item();
		Supplier s= new Supplier();
		
		 it.id=id;
		 
		 if(itemSupplierMap.containsKey(it))
		 {
			 TreeSet<SupplierItemInfo> supplierinfo= itemSupplierMap.get(it);
			 List<Long> itemDesc=it.description;
			 
			 for(long desc: description)
			 {
				 itemDesc.add(desc);
			 }
			 
			 itemSupplierMap.put(it,supplierinfo);
			 
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
			 
			/* for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet())
			 {
				 System.out.println(" Present Item Information");
				 System.out.println("id:"+iteminfo.getKey().id);
				 
				 for(long d:iteminfo.getKey().description)
				 {
					 System.out.println(d);
				 }
				 
			 }*/
			
			 
			 return true; 
		 }
		 
		 else
		 {
			 it= new Item(id,description);
			 itemSupplierMap.put(it,new TreeSet<>());
			 
			 for(long d: description)
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
			 
			 System.out.println(" new element added");
			 
		/*	for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet())
			 {
				 System.out.println(" New Item Information");
				 System.out.println("id:"+iteminfo.getKey().id);
				 
				 for(long d:iteminfo.getKey().description)
				 {
					 System.out.println(d);
				 }
				 
			 }*/
			 return false;
		 }
			
		    }
	
	public boolean add(long supplier, float reputation){
		
		return addSupplier(supplier, reputation);
	}

	private boolean addSupplier(long supplier, float reputation) {
		Item it= new Item();
		Supplier s= new Supplier();
		
		s.vid=supplier;
		
		
		
		return false;
	}
	
	

	//----k -----
	public Long[] purge(float maxReputation){
		//remove items having supplier <= maxReputation
		return purgeItems(maxReputation);
		
	}
	
	private Long[] purgeItems(float maxReputation){
		
		/*private HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>();
		private HashMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
		private TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		*/
		
		LinkedList<Long> itemsRemoved = new LinkedList<Long>();
		LinkedList<Supplier> supplierRemoved = new LinkedList<Supplier>();
	 
		// get the items and suppliers where suppliers's reputation <= maxReputation
		for(Map.Entry<Supplier, LinkedList<ItemPrice>> supplierMaxReputation : supplierItemMap.entrySet()){
			
			Supplier supplierEntry = supplierMaxReputation.getKey();
			LinkedList<ItemPrice> supplierItems = supplierMaxReputation.getValue();
			
			if(supplierEntry.reputation <= maxReputation){
				supplierRemoved.add(supplierEntry);
				for(ItemPrice item : supplierItems){
					itemsRemoved.add(item.id);						
				}				
			}						 
		}
		
		System.out.println("Items to be purged :" + itemsRemoved);
		
		//remove the supplier entry from supplierItemMap
		for(Supplier supplier : supplierRemoved){
			if(supplierItemMap.containsKey(supplier)){
				supplierItemMap.remove(supplier);			
			}
		}
		
		
		//remove the item entry from itemSupplierMap
		for(Long itemId : itemsRemoved){
			if(itemSupplierMap.containsKey(itemId)){				
				itemSupplierMap.remove(itemId);
			}
		}
		
		//remove the items with thier description from the itemDescriptionMap
		for(Map.Entry<Long, LinkedList<Long>> descItem : itemDescription.entrySet()){
			
			//check for item ids to be removed
			for(Long id : itemsRemoved){
				if(descItem.getValue().contains(id)){
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
		private HashMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
		private TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
		*/
		List<Long> desc = new LinkedList<Long>();
		long sumOfDescription = 0;
		boolean flag = false;
		Item deleteItem = null ;
		
		
		for(Map.Entry<Item, TreeSet<SupplierItemInfo>> itemInfo : itemSupplierMap.entrySet()){
			if(itemInfo.getKey().id == id){
				desc = itemInfo.getKey().description;
				deleteItem = itemInfo.getKey();
				flag = true;
				break;
			}
		}
			
		if(flag){
			System.out.println("Item found ");
			// remove  entry from itemSupplierMap
			itemSupplierMap.remove(deleteItem);
					
			// remove the items with thier description from the itemDescriptionMap
			for(Map.Entry<Long, LinkedList<Long>> descItem : itemDescription.entrySet()){
				//check for item id to be removed				
				if(descItem.getValue().contains(desc)){
						descItem.getValue().remove(desc);
				}
			}	
			
			// remove the item from supplierItemMap
			for(Map.Entry<Supplier, LinkedList<ItemPrice>> supplierItemInfo : supplierItemMap.entrySet()){
				LinkedList<ItemPrice> info = supplierItemInfo.getValue();
				for(ItemPrice i : info){
					if(i.id == id){
						info.remove(i);
					}
				}
			}		
			
			// find the sum of description
			for(Long d : desc){
				sumOfDescription += d;
			}					
			return sumOfDescription;
			
		}else{
			System.out.println("Item not found ");
			return 0L;
		}	
	}
	 
	
	public  void printItems(){
		
		System.out.println("\n--------- Present Item Information-----------");
		 for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet()){
			 System.out.println("id:"+iteminfo.getKey().id);
			 for(long d : iteminfo.getKey().description){
					 System.out.println(d);
			 }
		 }		
	}
	
	
	
}
