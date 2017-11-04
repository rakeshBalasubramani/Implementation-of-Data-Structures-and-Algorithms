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
		ArrayList<SkipListEntry> next;

		/**
		 * Constructor for the Skiplist Entry.
		 */
		SkipListEntry() {
			next = new ArrayList<>();
		}

		public String toString() {
			String print;
			if (this == head) {
				print = "H  ";
				for (SkipListEntry n : next) {
					if (n != tail) {
						print += n.element + " ";
					} else {
						print += "T ";
					}
				}
			} else if (this == tail) {
				print = "T";
			} else {
				print = element + "  ";
				for (SkipListEntry n : next) {
					if (n != tail) {
						print += n.element + " ";
					} else {
						print += "T ";
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
	 * Constructor to initialise the Skiplist.
	 */
	public SkipList() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Helper function to find element in the SkipList.
	 * 
	 * @param x
	 *            - Element to be found.
	 * @return - Entry that contains the elment if the element exists else the
	 *         previous Entry.
	 */
	private SkipListEntry find(T x) {
		SkipListEntry temp;
		temp = head;
		if (temp != null) {
			for (int i = temp.next.size() - 1; i >= 0; i--) {
				while (temp.next.get(i) != tail && temp.next.get(i).element.compareTo(x) < 0) {
					temp = temp.next.get(i);
				}

			}
		}
		return temp;
	}

	/**
	 * Fucntion to add element to the Skiplist.
	 * 
	 * @param x
	 *            - Element to be added.
	 * @return - True if added to the list else false.
	 */
	public boolean add(T x) {
		SkipListEntry temp = find(x);
		if (temp == null) {
			head = new SkipListEntry();
			tail = new SkipListEntry();
			SkipListEntry newEntry = new SkipListEntry(x);
			head.next.add(0, newEntry);
			head.next.add(1, tail);
			newEntry.next.add(0, tail);
			size++;

		} else if (temp.next.get(0).element == x) {
			temp.next.get(0).element = x;
			return false;
		} else {
			int level = chooseLevel(temp.next.size());
			SkipListEntry newEntry = new SkipListEntry(x);
			for (int i = 0; i <= level; i++) {
				newEntry.next.add(i, temp.next.get(i));
				temp.next.add(i, newEntry);
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
	private int chooseLevel(int height) {
		int i = 0;
		boolean b;
		Random r = new Random();
		while (i < height) {
			b = r.nextBoolean();
			if (b) {
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
		SkipListEntry temp = find(x);
		if (temp == null) {
			return null;
		} else if (x.compareTo(temp.element) == 0) {
			return x;
		} else {
			return temp.next.get(0).element;
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
		SkipListEntry temp = find(x);
		return temp.next.get(0).element == x;
	}

	/**
	 * Get the first element of Skiplist.
	 * 
	 * @return - Element if not ewmpty else null.
	 */
	public T first() {
		if (size > 0) {
			return head.next.get(0).element;
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
		return find(x).element;
	}

	/**
	 * Get element at index.
	 * 
	 * @param n
	 *            - Given index.
	 * @return - Element at index.
	 */
	public T get(int n) {
		return null;
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
		return null;
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
	 * @return - True if removed else false.
	 */
	public T remove(T x) {
		SkipListEntry temp = find(x);
		SkipListEntry entry = temp.next.get(0);
		if (entry.element != x) {
			return null;
		} else {
			for (int i = 0; i < entry.next.size(); i++) {
				if (temp.next.get(i) == entry) {
					temp.next.set(i, entry.next.get(i));
				} else {
					break;
				}
			}
			size--;
			return entry.element;
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
			temp = temp.next.get(0);
		}
		System.out.println(temp);

	}

	public static void main(String[] args) {
		SkipList<Integer> t = new SkipList<>();
		Scanner in = new Scanner(System.in);
		int ch, x;
		do {
			System.out.println(
					"1.Add,2.Ceiling,3.Contains,4.First,5.Floor,6.Get,7.isEmpty,8.Last,9.Rebuild,10.Remove,11.Size");
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
				System.out.println("First");
				System.out.println(t.first());
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
				System.out.println("isEmpty");
				System.out.println(t.isEmpty());
				break;
			case 8:
				System.out.println("Last");
				System.out.println(t.last());
				break;
			case 9:
				System.out.println("Rebuild");
				t.rebuild();
				break;
			case 10:
				System.out.println("Remove");
				x = in.nextInt();
				System.out.println(t.remove(x));
				break;
			case 11:
				System.out.println("Size");
				System.out.println(t.size());
				break;
			}
			t.printSkipList();
			System.out.println("Again??(1/0)");
			ch = in.nextInt();
		} while (ch == 1);
		in.close();
	}

}
