package cs6301.g38;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to implement SkipLists
 * @param <T>
 *            - Type of element stored in the SkipLists.
 */
public class SkipList<T extends Comparable<? super T>> {

	/**
	 * @author Rajkumar PanneerSelvam - rxp162130 <br>
	 *         Avinash Venkatesh - axv165330 <br>
	 *         Rakesh Balasubramani - rxb162130 <br>
	 *         HariPriyaa Manian - hum160030
	 *
	 * @Desc Entry class for the Skiplists
	 */
	class SkipListEntry {
		/**
		 * Element to be stored.
		 */
		T element;
		/**
		 * Arraylist of pointers.
		 */
		ArrayList<NextEntry> next;

		/**
		 * Constructor for the Skiplist Entry.
		 */
		SkipListEntry() {
			next = new ArrayList<>();
		}

		public String toString() {
			String print;
			NextEntry n;
			if (this == head) {
				print = "H  ";
				for (int i = 0; i < this.next.size(); i++) {
					n = this.next.get(i);
					if (n.ref != tail) {
						print += n.ref.element + " ";
					} else {
						print += "T" + " ";
					}
				}
			} else if (this == tail) {
				print = "T ";
				for (int i = 0; i < this.next.size(); i++) {
					n = this.next.get(i);
					print += "T" + " ";
				}
			} else {
				print = element + "  ";
				for (int i = 0; i < this.next.size(); i++) {
					n = this.next.get(i);
					if (n.ref != tail) {
						print += n.ref.element + " ";
					} else {
						print += "T" + " ";
					}
				}
			}
			return print;
		}

		/**
		 * Constructor for the Skiplist Entry.
		 * 
		 * @param x
		 *            - The value to be stored in the Skiplist Entry.
		 */
		public SkipListEntry(T x) {
			next = new ArrayList<>();
			element = x;
		}

		public int compareTo(T o) {
			if (element == null) {
				return 1;
			}
			if (element == o) {
				return 0;
			} else if (element.compareTo(o) < 0) {
				return -1;
			} else
				return 1;
		}

	}

	class NextEntry {
		SkipListEntry ref;
		int span;

		public NextEntry(SkipListEntry temp) {
			ref = temp;
			span = 0;
		}

		public void addSpan() {
			span++;
		}

		public void subSpan() {
			span--;
		}
	}

	/**
	 * Head of the Skiplist.
	 */
	SkipListEntry head;
	/**
	 * Tail of the Skiplist.
	 */
	SkipListEntry tail;
	/**
	 * Number of elements in the Skiplist.
	 */
	int size;

	/**
	 * MaxLevel of the Skiplist.
	 */
	int maxLevel;

	/**
	 * Constructor to initialise the Skiplist.
	 */
	public SkipList() {
		head = null;
		tail = null;
		size = 0;
		maxLevel = 5;
	}

	/**
	 * Helper function to find element in the SkipList.
	 * 
	 * @param x
	 *            - Element to be found.
	 * @return - Entry that contains the elment if the element exists else the
	 *         previous Entry.
	 */
	private ArrayList<SkipList<T>.SkipListEntry> find(T x) {
		SkipListEntry temp;
		temp = head;
		ArrayList<SkipListEntry> prev = new ArrayList<>();
		if (temp != null) {
			for (int i = maxLevel; i >= 0; i--) {
				while (temp.next.get(i).ref != tail && temp.next.get(i).ref.element.compareTo(x) < 0) {
					temp = temp.next.get(i).ref;
				}
				prev.add(temp);

			}
		}
		return prev;
	}

	/**
	 * Fucntion to add element to the Skiplist.
	 * 
	 * @param x
	 *            - Element to be added.
	 * @return - True if added to the list else false.
	 */
	public boolean add(T x) {
		ArrayList<SkipListEntry> prev = find(x);
		SkipListEntry temp = null;
		if (prev.size() > 0) {
			temp = prev.get(prev.size() - 1);
		}
		if (temp == null) {
			head = new SkipListEntry();
			tail = new SkipListEntry();
			SkipListEntry newEntry = new SkipListEntry(x);
			NextEntry nextTemp = new NextEntry(null);
			nextTemp.addSpan();
			head.next.add(0, new NextEntry(newEntry));
			newEntry.next.add(0, new NextEntry(tail));
			tail.next.add(0, nextTemp);
			for (int i = 1; i <= maxLevel; i++) {
				nextTemp = new NextEntry(null);
				nextTemp.addSpan();
				head.next.add(i, new NextEntry(tail));
				tail.next.add(i, nextTemp);
			}
			size++;

		} else if (temp.next.get(0).ref.element == x) {
			temp.next.get(0).ref.element = x;
			return false;
		} else {
			int level = chooseLevel(),tempSpan;
			SkipListEntry newEntry = new SkipListEntry(x);
			for (int i = 0; i <= level; i++) {
				if (prev.get(maxLevel - i) != null) {
					newEntry.next.add(i, new NextEntry(prev.get(maxLevel - i).next.get(i).ref));
					prev.get(maxLevel - i).next.get(i).ref = newEntry;
				}
			}
			for(int i=level+1;i<=maxLevel;i++) {
				prev.get(maxLevel-i).next.get(i).ref.next.get(i).addSpan();
			}
			SkipListEntry t;
			for (int i = 0; i <= level; i++) {
				t = prev.get(maxLevel-i);
				tempSpan=0;
				while(t.element!=null&&t.element.compareTo(newEntry.element)!=0) {
					t = t.next.get(0).ref;
					tempSpan++;
				}
				newEntry.next.get(i).span = tempSpan;
			}
			for (int i = 0; i <= level; i++) {
				t = newEntry.next.get(i).ref;
				t.next.get(i).addSpan();
				if(i+1==t.next.size()) {
					t=t.next.get(0).ref;
				}
			}
			size++;
		}
		return true;
	}

	/**
	 * Helper function to randomise the level of the new Entry.
	 * 
	 * @param height
	 *            - Level of the previous Entry.
	 * @return - Level of the new Entry.
	 */
	private int chooseLevel() {
		int i = 0;
		Random r = new Random();
		while (i < maxLevel) {
			if (r.nextBoolean()) {
				i++;
			} else {
				break;
			}

		}
		return i;
	}

	/**
	 * Find smallest element that is greater or equal to x.
	 * 
	 * @param x
	 *            - Given element.
	 * @return - ceil(x)
	 */
	public T ceiling(T x) {
		ArrayList<SkipListEntry> prev = find(x);
		SkipListEntry temp = prev.get(prev.size() - 1);
		if (temp == null) {
			return null;
		} else if (temp.element != null && x.compareTo(temp.element) == 0) {
			return x;
		} else {
			return temp.next.get(0).ref.element;
		}
	}

	/**
	 * Does list contain x?
	 * 
	 * @param x
	 *            - Given Element
	 * @return - True if skiplists contains element else false.
	 */
	public boolean contains(T x) {
		ArrayList<SkipListEntry> prev = find(x);
		SkipListEntry temp = prev.get(prev.size() - 1);
		if (temp != null) {
			return temp.next.get(0).ref.element == x;
		} else {
			return false;
		}
	}

	/**
	 * Get the first element of Skiplist.
	 * 
	 * @return - Element if not ewmpty else null.
	 */
	public T first() {
		if (size > 0) {
			return head.next.get(0).ref.element;
		} else {
			return null;
		}
	}

	/**
	 * Find largest element that is less than or equal to x.
	 * 
	 * @param x
	 *            - Given element.
	 * @return - floor(x)
	 */
	public T floor(T x) {
		ArrayList<SkipListEntry> prev = find(x);
		SkipListEntry temp = prev.get(prev.size() - 1);
		if(temp.next.get(0).ref.element.compareTo(x)<=0) {
			return temp.next.get(0).ref.element;
		} else {
			return temp.element;
		}
	}

	/**
	 * Get element at index.
	 * 
	 * @param n
	 *            - Given index.
	 * @return - Element at index.
	 */
	public T get(int n) {
		if(n>=size) {
			return null;
		}
		int i=0;
		SkipListEntry temp = head;
		while(i<=n) {
			temp=temp.next.get(0).ref;
			i++;
		}
		return temp.element;
	}

	/**
	 * To check if the Skiplist is empty.
	 * 
	 * @return - True if empty else false.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Iterator for the Skiplist.
	 * 
	 * @return - Skiplist iterator.
	 */
	public Iterator<T> iterator() {
		return new SkipListIterator(head);
	}

	/**
	 * @author Rajkumar PanneerSelvam - rxp162130 <br>
	 *         Avinash Venkatesh - axv165330 <br>
	 *         Rakesh Balasubramani - rxb162130 <br>
	 *         HariPriyaa Manian - hum160030
	 *
	 * @Desc Class used to iterate over the SkipLists
	 * @param <T>
	 *            - Type of element stored in the SkipLists.
	 */
	class SkipListIterator implements Iterator<T> {
		/**
		 * Temp SkipListEntry used to iterate.
		 */
		SkipListEntry temp;

		/**
		 * Contructor to initialise the head of the iterator.
		 * 
		 * @param head
		 *            - HEad of the Skiplist.
		 */
		public SkipListIterator(SkipList<T>.SkipListEntry head) {
			temp = head;
		}

		public boolean hasNext() {
			return temp.next.get(0) != null;
		}

		public T next() {
			T element = temp.element;
			temp.next.get(0);
			return element;

		}
	}

	/**
	 * Get the last element of the skiplist.
	 * 
	 * @return - Last Element.
	 */
	public T last() {
		return get(size - 1);
	}

	/**
	 * Reorganize the elements of the list into a perfect skip list.
	 */
	public void rebuild() {

	}

	/**
	 * Fucntion to remove the element from the list.
	 * 
	 * @param x
	 *            - Given element.
	 * @return - Element if removed else null.
	 */
	public T remove(T x) {
		ArrayList<SkipListEntry> prev = find(x);
		NextEntry entry = prev.get(prev.size() - 1).next.get(0);
		if (entry.ref.element != x) {
			return null;
		} else {
			for (int i = 0; i <= maxLevel; i++) {
				if (prev.get(maxLevel - i).next.get(i).ref.element.compareTo(entry.ref.element) == 0) {
					prev.get(maxLevel - i).next.set(i, entry.ref.next.get(i));
				} else {
					break;
				}
			}
			size--;
			return entry.ref.element;
		}
	}

	/**
	 * Get the number of elements in the skiplist.
	 * 
	 * @return - size
	 */
	public int size() {
		return size;
	}

	/**
	 * Print the skiplist.
	 */
	private void printSkipList() {
		SkipListEntry temp = head;
		while (temp != tail) {
			System.out.println(temp);
			temp = temp.next.get(0).ref;
		}
		System.out.println(temp);

	}

	public static void main(String[] args) {
		SkipList<Integer> t = new SkipList<>();
		Scanner in = new Scanner(System.in);
		int ch, x;
		do {
			System.out.println(
					"1.Add,2.Ceiling,3.Contains,4.First,5.Floor,6.Get,7.isEmpty,8.Last,9.Print,10.Remove,11.Size");
			ch = in.nextInt();
			switch (ch) {
			case 1:
				System.out.println("Enter element to add");
				x = in.nextInt();
				System.out.println(t.add(x));
				break;
			case 2:
				System.out.println("Ceiling");
				x = in.nextInt();
				System.out.println(t.ceiling(x));
				break;
			case 3:
				System.out.println("Contains");
				x = in.nextInt();
				System.out.println(t.contains(x));
				break;
			case 4:
				System.out.println("First: " + t.first());
				break;
			case 5:
				System.out.println("Floor");
				x = in.nextInt();
				System.out.println(t.floor(x));
				break;
			case 6:
				System.out.println("Enter index");
				x = in.nextInt();
				System.out.println(t.get(x));
				break;
			case 7:
				System.out.println("isEmpty: " + t.isEmpty());
				break;
			case 8:
				System.out.println("Last: " + t.last());
				break;
			case 9:
				t.printSkipList();
				break;
			case 10:
				System.out.println("Enter element to be removed");
				x = in.nextInt();
				System.out.println(t.remove(x));
				break;
			case 11:
				System.out.println("Size: " + t.size());
				break;
			}
			System.out.println("Again??(1/0)");
			ch = in.nextInt();
		} while (ch == 1);
		in.close();
	}

}
