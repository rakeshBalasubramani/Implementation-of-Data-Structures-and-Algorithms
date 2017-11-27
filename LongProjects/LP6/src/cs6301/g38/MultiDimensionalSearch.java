package cs6301.g38;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cs6301.g38.MDS.Pair;

public class MultiDimensionalSearch {

	private HashMap<Long, HashMap<Long, Long>> itemDescription = new HashMap<Long, HashMap<Long, Long>>();
	private TreeMap<Item, TreeSet<SupplierItemInfo>> itemSupplierMap = new TreeMap<Item, TreeSet<SupplierItemInfo>>();
	private TreeMap<Supplier, TreeSet<ItemPrice>> supplierItemMap = new TreeMap<Supplier, TreeSet<ItemPrice>>();
	private HashMap<Long, Float> suppplierIdReputation = new HashMap<Long, Float>();

	Item dummyItemKey = new Item();
	Supplier dummySuppKey = new Supplier();
	ItemPrice dummyItemPrice = new ItemPrice();

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

			int cmp = Float.compare(this.reputation, s.getReputation());

			return (int) (cmp == 0 ? this.vid - s.vid : cmp);
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

	public static class SupplierItemInfo implements
			Comparable<SupplierItemInfo> {

		@Override
		public String toString() {
			return "SupplierItemInfo [id=" + id + ", vid=" + vid
					+ ", reputation=" + reputation + ", price=" + price + "]";
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
			int reputationDiff = Float.compare(this.reputation,
					sio.getReputation());
			int priceDiff = ((reputationDiff == 0) ? Integer.compare(
					this.price, sio.getPrice()) : reputationDiff);
			return ((priceDiff == 0)) ? Long.compare(this.vid, sio.vid)
					: priceDiff;

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
			if (Float.floatToIntBits(reputation) != Float
					.floatToIntBits(other.reputation))
				return false;
			if (vid != other.vid)
				return false;
			return true;
		}

	}

	private class PriceComparator implements Comparator<SupplierItemInfo> {

		@Override
		public int compare(SupplierItemInfo sp1, SupplierItemInfo sp2) {

			int priceDiff = Float.compare(sp1.price, sp2.price);
			return ((priceDiff == 0)) ? Long.compare(sp1.vid, sp2.vid)
					: priceDiff;

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
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ItemPrice other = (ItemPrice) obj;
			if (id != other.id)
				return false;
			if (price != other.price)
				return false;
			return true;
		}

		@Override
		public int compareTo(ItemPrice o) {
			int idDiff = Long.compare(this.id, o.id);
			return ((idDiff == 0)) ? Integer.compare(this.price, o.price)
					: idDiff;
		}
	}

	private class ItemPriceComp implements Comparator<ItemPrice> {

		@Override
		public int compare(ItemPrice ip1, ItemPrice ip2) {
			if (ip1.getPrice() > ip2.getPrice()) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	public static class SupplierItemPairs implements
			Comparable<SupplierItemPairs> {
		@Override
		public String toString() {
			return "SupplierItemPairs [supplier=" + supplier + ", listOfItems="
					+ listOfItems + ", numOfItems=" + numOfItems + "]";
		}

		private Supplier supplier;
		private TreeSet<ItemPrice> listOfItems;
		int numOfItems;

		public SupplierItemPairs() {

		}

		public SupplierItemPairs(Supplier supplier,
				TreeSet<ItemPrice> listOfItems) {
			this.supplier = supplier;
			this.listOfItems = listOfItems;
		}

		public void setSupplier(Supplier supplier) {
			this.supplier = supplier;
		}

		public void setListOfItems(TreeSet<ItemPrice> listOfItems) {
			this.listOfItems = listOfItems;
		}

		public Supplier getSupplier() {
			return this.supplier;
		}

		public void setNumOfItems(int numOfItems) {
			this.numOfItems = numOfItems;
		}

		public int getNumOfItems() {
			return this.numOfItems;
		}

		public TreeSet<ItemPrice> getListOfItems() {
			return this.listOfItems;
		}

		@Override
		public int compareTo(SupplierItemPairs sip) {

			int supplierDiff = Float.compare(this.supplier.getReputation(),
					sip.supplier.getReputation());

			Set<ItemPrice> result = new TreeSet<>();
			intersect(this.listOfItems, sip.listOfItems, result);
			int minSize = Math.min(this.listOfItems.size(),
					sip.listOfItems.size());

			return ((supplierDiff == 0) ? Integer.compare(result.size(),
					minSize) : supplierDiff);

		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SupplierItemPairs other = (SupplierItemPairs) obj;

			if (supplier == null) {
				if (other.supplier != null)
					return false;
			} else if (!(supplier.getReputation() == (other.supplier
					.getReputation())))
				return false;

			if (listOfItems == null) {
				if (other.listOfItems != null)
					return false;
			} else {
				Set<ItemPrice> result = new TreeSet<>();
				intersect(this.listOfItems, other.listOfItems, result);
				int minSize = Math.min(this.listOfItems.size(),
						other.listOfItems.size());
				return minSize == result.size();
			}

			return true;

		}

	}

	private static <T> T hasNext(Iterator<T> it) {
		return it.hasNext() ? it.next() : null;
	}

	public static <T extends Comparable<? super T>> void intersect(Set<T> l1,
			Set<T> l2, Set<T> outList) {
		// set the iterator for both lists
		Iterator<T> it1 = l1.iterator();
		Iterator<T> it2 = l2.iterator();

		// pointing to first element of both lists
		T x1 = hasNext(it1);
		T x2 = hasNext(it2);

		// now traverse till the end of both the lists
		// find the common elements and append to the outList

		while (x1 != null && x2 != null) {
			// since the elements are sorted, if the element is small, then
			// advance the iterator
			if ((x1).compareTo(x2) < 0) {
				x1 = hasNext(it1);
			} else if ((x1).compareTo(x2) > 0) {
				x2 = hasNext(it2);
			}

			else {
				outList.add(x1); // adding the common element to the outList and
									// increment both iterator
				x1 = hasNext(it1);
				x2 = hasNext(it2);
			}

		}
	}

	public static class ItemDescOccurence implements
			Comparable<ItemDescOccurence> {

		private long itemId;
		private Long numOfOccurence;

		public ItemDescOccurence() {

		}

		public ItemDescOccurence(long itemId, long numOfOccurence) {
			this.itemId = itemId;
			this.numOfOccurence = numOfOccurence;
		}

		public long getItemId() {
			return itemId;
		}

		public Long getNumOfOccurence() {
			return numOfOccurence;
		}

		public void setItemId(long itemId) {
			this.itemId = itemId;
		}

		public void setNumOfOccurence(Long long1) {
			this.numOfOccurence = long1;
		}

		@Override
		public int compareTo(ItemDescOccurence o) {
			return (int) (this.numOfOccurence - o.numOfOccurence) * -1;
		}

		@Override
		public boolean equals(Object obj) {

			ItemDescOccurence ido = (ItemDescOccurence) obj;
			return this.itemId == ido.itemId;
		}

		@Override
		public int hashCode() {
			return (int) itemId;
		}

		@Override
		public String toString() {
			return "ItemDescOccurence [itemId=" + itemId + ", numOfOccurence="
					+ numOfOccurence + "]";
		}

	}

	private class OccComparator implements Comparator<ItemDescOccurence> {

		@Override
		public int compare(ItemDescOccurence sp1, ItemDescOccurence sp2) {

			return Long.compare(sp2.getNumOfOccurence(),
					sp1.getNumOfOccurence());

		}

	}

	public MultiDimensionalSearch() {

	}

	public boolean add(long id, Long[] description) {
		return addDescription(id, description);
	}

	private boolean addDescription(long id, Long[] descriptions) {

		dummyItemKey.setId(id);

		if (itemSupplierMap.containsKey(dummyItemKey)) {
			Item it1 = getItemDetails(dummyItemKey);
			TreeSet<SupplierItemInfo> supplierinfo = itemSupplierMap.get(it1);
			List<Long> itemDesc = it1.description;

			for (Long desc : descriptions) {
				itemDesc.add(desc);

				if (itemDescription.containsKey(desc)) {

					HashMap<Long, Long> idWithDescription = itemDescription
							.get(desc);
					if (idWithDescription.get(id) != null) {
						idWithDescription
								.put(id, idWithDescription.get(id) + 1);
					} else {
						idWithDescription.put(id, (long) 1);
						// check the existence of Id in
					}
					// the HashSet before adding the
					// item id. ??
					itemDescription.put(desc, idWithDescription);

				}

				else {
					HashMap<Long, Long> idWithDescription = new HashMap<Long, Long>();
					idWithDescription.put(id, (long) 1);
					itemDescription.put(desc, idWithDescription);
				}

			}

			itemSupplierMap.put(it1, supplierinfo);

			return false;
		}

		else {

			Item item = new Item(id, descriptions);
			itemSupplierMap.put(item, new TreeSet<SupplierItemInfo>());

			for (long desc : descriptions) {

				if (itemDescription.containsKey(desc)) {
					HashMap<Long, Long> ids = itemDescription.get(desc);
					ids.put(id, (long) 1);
					itemDescription.put(desc, ids);

				}

				else {
					HashMap<Long, Long> idWithDescription = new HashMap<Long, Long>();
					idWithDescription.put(id, (long) 1);
					itemDescription.put(desc, idWithDescription);

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
		float rep = 0.0f;

		if (suppplierIdReputation.containsKey(vid)) {
			rep = suppplierIdReputation.get(vid);
			dummySuppKey.setVid(vid);
			dummySuppKey.setReputation(rep);

			if (supplierItemMap.containsKey(dummySuppKey)) {

				Set<Supplier> supplierSet = supplierItemMap.keySet();
				supplierArr = new Supplier[supplierSet.size()];
				supplierSet.toArray(supplierArr);

				int index = BinarySearch.recursiveBinarySearch(supplierArr,
						dummySuppKey);
				Supplier supplier = supplierArr[index];

				TreeSet<ItemPrice> itemPrice = supplierItemMap.get(supplier);
				// supplier.setReputation(reputation);
				supplierItemMap.remove(supplier);
				Supplier newSupp = new Supplier(supplier.getVid(), reputation);
				supplierItemMap.put(newSupp, itemPrice);

				suppplierIdReputation.put(supplier.getVid(), reputation);

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

			return true;
		}

	}

	// ----k -----
	public Long[] purge(float maxReputation) {
		return purgeItems(maxReputation);

	}

	private Long[] purgeItems(float maxReputation) {

		SupplierItemInfo sii = new SupplierItemInfo();
		sii.setReputation(maxReputation);
		ArrayList<Long> result = new ArrayList<Long>();

		for (Map.Entry<Item, TreeSet<SupplierItemInfo>> itemInfo : itemSupplierMap
				.entrySet()) {
			Item item = itemInfo.getKey();
			TreeSet<SupplierItemInfo> value = itemInfo.getValue();

			NavigableSet<SupplierItemInfo> itemsWithReputation = value.headSet(
					sii, false);
			NavigableSet<SupplierItemInfo> itemsWithReputationT = value
					.tailSet(sii, true);
			int equalsMaxRep = 0;
			for (SupplierItemInfo si : itemsWithReputationT) {
				if (si.getReputation() == maxReputation) {
					equalsMaxRep++;
				} else {
					break;
				}
			}
			if (itemsWithReputation.size() + equalsMaxRep == value.size()) {
				result.add(item.id);
			}

		}

		for (Long i : result) {
			remove(i);
		}

		return result.toArray(new Long[result.size()]);
	}

	// --- l ---
	public Long remove(Long id) {
		return removeItem(id);
	}

	private Long removeItem(Long id) {

		long sumOfDescription = 0;
		HashSet<Long> removeItemsForSupplier = new HashSet<Long>();

		dummyItemKey.setId(id);

		if (itemSupplierMap.containsKey(dummyItemKey)) {
			Item it1 = getItemDetails(dummyItemKey);
			// Supplier supplierEntry;
			List<Long> desc = it1.description;

			for (Long d : desc) {
				itemDescription.get(d).remove(id);

			}

			// get the suppliers info for the item to be removed
			TreeSet<SupplierItemInfo> supplierValue = itemSupplierMap.get(it1);
			for (SupplierItemInfo s : supplierValue) {
				removeItemsForSupplier.add(s.vid);
			}

			// remove entry from itemSupplierMap
			itemSupplierMap.remove(it1);

			// check for suppliers to be removed, find the item entry and remove
			// item from supplierItemMap
			for (Long supp : removeItemsForSupplier) {
				// supplierEntry = new Supplier();
				dummySuppKey.setVid(supp);
				dummySuppKey.setReputation(suppplierIdReputation.get(supp));
				if (supplierItemMap.containsKey(dummySuppKey)) {
					TreeSet<ItemPrice> info = supplierItemMap.get(dummySuppKey);
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

			dummySuppKey.setVid(vid);
			dummySuppKey.setReputation(rep);
			if (supplierItemMap.containsKey(dummySuppKey)) {

				Supplier supplier = dummySuppKey;

				TreeSet<ItemPrice> ipSet = supplierItemMap.get(supplier);

				for (Pair p : idPrice) {

					dummyItemKey.setId(p.id);

					if (itemSupplierMap.containsKey(dummyItemKey)) {

						isnewProd = true;
						Item item;
						item = getItemDetails(dummyItemKey);

						dummyItemPrice.setId(p.id);
						if (ipSet.contains(dummyItemPrice)) {

							itemPriceArr = new ItemPrice[ipSet.size()];
							ipSet.toArray(itemPriceArr);

							int ipIndex = BinarySearch.recursiveBinarySearch(
									itemPriceArr, dummyItemPrice);
							ItemPrice itemPrice = itemPriceArr[ipIndex];
							int oldItemPrice = itemPrice.getPrice();
							itemPrice.setPrice(p.price);
							isnewProd = false;

							SupplierItemInfo si = new SupplierItemInfo(p.id,
									vid, supplier.getReputation(), oldItemPrice);

							supplierItemSet = itemSupplierMap.get(item);


							supplierItemSet.remove(si);
							si.setPrice(p.price);
							supplierItemSet.add(si);

							itemSupplierMap.put(item, supplierItemSet);

						}

						else if (isnewProd) {

							newProductscount++;
							ItemPrice newItemPrice = new ItemPrice(p.id,
									p.price);
							ipSet.add(newItemPrice);

							SupplierItemInfo si = new SupplierItemInfo(p.id,
									vid, supplier.getReputation(), p.price);

							TreeSet<SupplierItemInfo> supplierInfoSet = itemSupplierMap
									.get(item);
							supplierInfoSet.add(si);

							itemSupplierMap.put(item, supplierInfoSet);
						}

					} else {
						Item item = new Item();
						item.setId(p.id);
						ipSet.add(new ItemPrice(p.id, p.price));
						newProductscount++;
						supplierItemSet = new TreeSet<>();
						SupplierItemInfo si = new SupplierItemInfo(p.id, vid,
								supplier.getReputation(), p.price);
						supplierItemSet.add(si);
						itemSupplierMap.put(item, supplierItemSet);
					}

				}
				supplierItemMap.put(supplier, ipSet);
			}

		}

		else {

			Supplier supplier = new Supplier(vid, 0.0f);

			TreeSet<ItemPrice> itemPriceSet = new TreeSet<>();
			Item ite;

			for (Pair p : idPrice) {
				itemPriceSet.add(new ItemPrice(p.id, p.price));

				dummyItemKey.setId(p.id);

				if (itemSupplierMap.containsKey(dummyItemKey)) {

					ite = getItemDetails(dummyItemKey);

					SupplierItemInfo sio = new SupplierItemInfo(p.id, vid,
							0.0f, p.price);
					TreeSet<SupplierItemInfo> supplierItemInfoSet = itemSupplierMap
							.get(ite);

					supplierItemInfoSet.add(sio);
					itemSupplierMap.put(ite, supplierItemInfoSet);
					newProductscount++;

				} else {
					newProductscount++;
					TreeSet<SupplierItemInfo> supplierItemInfoSet = new TreeSet<SupplierItemInfo>();
					ite = new Item();
					ite.setId(p.id);

					SupplierItemInfo sio = new SupplierItemInfo(p.id, vid,
							0.0f, p.price);
					supplierItemInfoSet.add(sio);

					itemSupplierMap.put(ite, supplierItemInfoSet);
				}
			}

			supplierItemMap.put(supplier, itemPriceSet);
			suppplierIdReputation.put(vid, 0.0f);
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
				if (itemDescription.get(desc).containsKey(id)) {
					itemDescription.get(desc).remove(id);
					numOfElementsRemoved++;
				}
			}
		}

		// remove arr elements from description in itemSupplierMap
		dummyItemKey.setId(id);

		if (itemSupplierMap.containsKey(dummyItemKey)) {

			Item it1 = getItemDetails(dummyItemKey);

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
		for (Map.Entry<Item, TreeSet<SupplierItemInfo>> itemSupplier : itemSupplierMap
				.entrySet()) {
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
		List<SupplierItemInfo> supplierWithReputation;
		SupplierItemInfo[] suppliersByPrice;

		int sumOfMinPrice = 0;

		for (Long id : arr) {
			supplierWithReputation = new ArrayList<SupplierItemInfo>();
			dummyItemKey.setId(id);
			if (itemSupplierMap.containsKey(dummyItemKey)) {
				supplierInfo = itemSupplierMap.get(dummyItemKey);

				// suppliers for the item whose reputation >= minReputation
				NavigableSet<SupplierItemInfo> itemsWithReputation = supplierInfo
						.tailSet(sii, true);

				NavigableSet<SupplierItemInfo> itemsWithReputationH = supplierInfo
						.headSet(sii, true);
				int equalsMaxRep = 0;

				for (SupplierItemInfo si : itemsWithReputationH) {
					if (si.getReputation() == minReputation) {
						equalsMaxRep++;
						supplierWithReputation.add(si);
					} else {
						break;
					}
				}
				if (itemsWithReputation.size() + equalsMaxRep == itemsWithReputation
						.size()) {

					for (SupplierItemInfo s : itemsWithReputation) {
						supplierWithReputation.add(s);
					}

					suppliersByPrice = sortByPrice(supplierWithReputation);

					if (suppliersByPrice.length > 0) {
						int i = 0;
						sumOfMinPrice += suppliersByPrice[0].price;
						

					}
				}
			} else {
				System.out.println(" Skipping id " + id + " : not available");
			}
		}
		return sumOfMinPrice;
	}

	private Item getItemDetails(Item it) {
		Item[] arr1;

		Set<Item> itemSet = itemSupplierMap.keySet();
		arr1 = new Item[itemSet.size()];
		itemSet.toArray(arr1);

		int index = BinarySearch.recursiveBinarySearch(arr1, it);
		it = arr1[index];
		
		return it;
	}

	// --- g ---
	public Long[] findSupplier(Long id) {
		return findSupplierForId(id);
	}

	private Long[] findSupplierForId(Long id) {
		List<SupplierItemInfo> supplierResult = new ArrayList<SupplierItemInfo>();
		TreeSet<SupplierItemInfo> supplierInfo;
		SupplierItemInfo[] suppliersByPrice;
		Long[] resultArr;
		int i = 0;

		dummyItemKey.setId(id);

		if (itemSupplierMap.containsKey(dummyItemKey)) {
			supplierInfo = itemSupplierMap.get(dummyItemKey);
			for (SupplierItemInfo s : supplierInfo) {
				supplierResult.add(s);
			}
		}

		suppliersByPrice = sortByPrice(supplierResult);

		resultArr = new Long[supplierResult.size()];

		for (SupplierItemInfo supplier : suppliersByPrice) {
			resultArr[i++] = supplier.getVid();
		}

		return resultArr;

	}

	private SupplierItemInfo[] sortByPrice(List<SupplierItemInfo> supplierResult) {
		SupplierItemInfo[] resultArr;
		int i = 0;

		Collections.sort(supplierResult, new PriceComparator());
		resultArr = new SupplierItemInfo[supplierResult.size()];

		for (SupplierItemInfo supplier : supplierResult) {
			resultArr[i++] = supplier;
		}

		return resultArr;

	}

	// --- h ---
	public Long[] findSupplier(Long id, float minReputation) {
		return findSupplierForId(id, minReputation);
	}

	private Long[] findSupplierForId(Long id, float minReputation) {

		TreeSet<SupplierItemInfo> supplierInfo;
		List<SupplierItemInfo> supplierWithReputation = new ArrayList<SupplierItemInfo>();
		Long[] resultArr;
		SupplierItemInfo[] suppliersByPrice;
		int i = 0;

		SupplierItemInfo sii = new SupplierItemInfo();
		sii.setReputation(minReputation);
		dummyItemKey.setId(id);

		if (itemSupplierMap.containsKey(dummyItemKey)) {
			supplierInfo = itemSupplierMap.get(dummyItemKey);

			// suppliers for the item whose reputation >= minReputation
			NavigableSet<SupplierItemInfo> result = supplierInfo.tailSet(sii,
					true);

			for (SupplierItemInfo s : result) {
				supplierWithReputation.add(s);
			}
		}

		suppliersByPrice = sortByPrice(supplierWithReputation);

		resultArr = new Long[supplierWithReputation.size()];

		for (SupplierItemInfo supplier : suppliersByPrice) {
			resultArr[i++] = supplier.getVid();
		}

		return resultArr;


	}

	public Long[] findItem(Long[] arr) {
		return findItemWithGivenDesc(arr);
	}

	private Long[] findItemWithGivenDesc(Long[] arr) {



		ItemDescOccurence itd;
		HashMap<Long, Long> items;

		HashMap<Long, Long> idOccurenceMap = new HashMap<Long, Long>();
	
		Long[] resultArr;
		List<ItemDescOccurence> idOccList = new ArrayList<ItemDescOccurence>();
		int i = 0;

		// store id, occurence in idOccurenceMap
		for (Long desc : arr) {
			items = itemDescription.get(desc);
			if (items != null) {

				for (Long item : items.keySet()) {
					if (!idOccurenceMap.containsKey(item)) {
						idOccurenceMap.put(item, items.get(item));
					} else {
						idOccurenceMap.put(item, idOccurenceMap.get(item)
								+ items.get(item));
					}
				}
			}
		}

	

		for (Map.Entry<Long, Long> idOccurence : idOccurenceMap.entrySet()) {
			itd = new ItemDescOccurence();
			itd.setItemId(idOccurence.getKey());
			itd.setNumOfOccurence(idOccurence.getValue());
			idOccList.add(itd);

		}

		Collections.sort(idOccList, new OccComparator());

		resultArr = new Long[idOccList.size()];
		for (ItemDescOccurence it : idOccList) {
			resultArr[i++] = it.getItemId();
		}

		return resultArr;
	}

	public Long[] findItem(Long n, int minPrice, int maxPrice,
			float minReputation) {
		return findItemGivenCriteria(n, minPrice, maxPrice, minReputation);
	}

	private Long[] findItemGivenCriteria(Long n, int minPrice, int maxPrice,
			float minReputation) {
		HashMap<Long, Long> listOfItems = null;
		TreeSet<SupplierItemInfo> supplierInfo;
		SupplierItemInfo sii = new SupplierItemInfo();
		sii.setReputation(minReputation);

		TreeSet<ItemPrice> itemPrice = new TreeSet<ItemPrice>(
				new ItemPriceComp());
		Long resultItemArr[];

		// get the list of items for given n
		if (itemDescription.containsKey(n)) {
			listOfItems = itemDescription.get(n);
			int tempMinPrice;
			// for each item find the suppliers meeting given criteria
			for (Long itemId : listOfItems.keySet()) {
				dummyItemKey.setId(itemId);
				tempMinPrice = Integer.MAX_VALUE;
				// get the supplier item info for that item
				supplierInfo = itemSupplierMap.get(dummyItemKey);
				// get only the supplies who meets the given minReputation
				
				NavigableSet<SupplierItemInfo> suppliersWithMinReputation = supplierInfo
						.tailSet(sii, true);
				// get only the suppliers with given price range
				if (suppliersWithMinReputation.size() > 0) {
					for (SupplierItemInfo s : suppliersWithMinReputation) {
						if (s.price >= minPrice && s.price <= maxPrice) {
							// itemPrice.add(new ItemPrice(s.id, s.price));
							if (tempMinPrice > s.price) {
								tempMinPrice = s.price;
							}

						}
					}
					// for(supplierInPriceRange )
					itemPrice.add(new ItemPrice(itemId, tempMinPrice));
				}
				// }
			}
			resultItemArr = new Long[itemPrice.size()];
			ItemPrice[] tmpArray = itemPrice.toArray(new ItemPrice[itemPrice
					.size()]);

			for (int i = 0; i < itemPrice.size(); i++) {
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

		dummyItemKey.setId(id);

		if (itemSupplierMap.containsKey(dummyItemKey)) {

			Item item = getItemDetails(dummyItemKey);
			Long[] descriptions = new Long[item.getDescription().size()];
			item.getDescription().toArray(descriptions);
			return descriptions;
		} else {
			return null;
		}
	}

	public Long[] identical() {
		return identicalSuppliers();
	}

	private Long[] identicalSuppliers() {

		
		TreeMap<SupplierItemPairs, Long> suppliersSorted = new TreeMap<SupplierItemPairs, Long>();
		HashSet<Long> identicalSuppliers = new HashSet<Long>();
		SupplierItemPairs obj;

		

		for (Map.Entry<Supplier, TreeSet<ItemPrice>> supplierEntry : supplierItemMap
				.entrySet()) {

			
			if (supplierEntry.getValue().size() < 5) {
				continue;
			} else {
				obj = new SupplierItemPairs();
				obj.setSupplier(supplierEntry.getKey());
				obj.setListOfItems(supplierEntry.getValue());
				obj.setNumOfItems(supplierEntry.getValue().size());
				Long val = suppliersSorted.get(obj);
				if (val != null) {
					suppliersSorted.put(obj, val);
					identicalSuppliers.add(obj.getSupplier().getVid());
					identicalSuppliers.add(val);

				} else {
					suppliersSorted.put(obj, obj.getSupplier().getVid());
				}
			}

		}

		

		return identicalSuppliers.toArray(new Long[identicalSuppliers.size()]);

	}

}
