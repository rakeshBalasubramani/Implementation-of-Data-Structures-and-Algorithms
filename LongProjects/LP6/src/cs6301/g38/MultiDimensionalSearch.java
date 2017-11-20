package cs6301.g38;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class MultiDimensionalSearch {
	
	private HashMap<Long,LinkedList<Long>> itemDescription = new HashMap<Long,LinkedList<Long>>();
	private HashMap<Item,TreeSet<SupplierItemInfo>> itemSupplierMap= new HashMap<Item,TreeSet<SupplierItemInfo>>();
	private TreeMap<Supplier,LinkedList<ItemPrice>> supplierItemMap= new TreeMap<Supplier,LinkedList<ItemPrice>>();
	Item it= new Item();
	Supplier s= new Supplier();
	
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
	
	
//	public MultiDimensionalSearch()
//	{
//		
//	}
	
	public boolean add(long id, Long[] description){
		return addDescription(id, description);
	}
	
	private boolean addDescription(long id, Long[ ] description) {
		 
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
			 System.out.println("desc added");
			 
			 for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet())
			 {
				 System.out.println(" Present Item Information");
				 System.out.println("id:"+iteminfo.getKey().id);
				 
				 for(long d:iteminfo.getKey().description)
				 {
					 System.out.println(d);
				 }
				 
			 }
			 
			 
			 
			 
			 return true; 
		 }
		 
		 else
		 {
			 it= new Item(id,description);
			 itemSupplierMap.put(it,new TreeSet<>());
			 
			 for(long d:description)
			 {
				 LinkedList<Long> idWithDescription= new LinkedList<Long>();
				 idWithDescription.add(id);
				 itemDescription.put(d,idWithDescription);
			 }
			 
			 System.out.println(" new element added");
			 
			 for(Map.Entry<Item, TreeSet<SupplierItemInfo>> iteminfo: itemSupplierMap.entrySet())
			 {
				 System.out.println(" New Item Information");
				 System.out.println("id:"+iteminfo.getKey().id);
				 
				 for(long d:iteminfo.getKey().description)
				 {
					 System.out.println(d);
				 }
				 
			 }
			 return false;
		 }
			
		    }
	
	public boolean add(long supplier, float reputation){
		
		return addSupplier(supplier, reputation);
	}

	private boolean addSupplier(long supplier, float reputation) {
		
		
		
		return false;
	}
}
