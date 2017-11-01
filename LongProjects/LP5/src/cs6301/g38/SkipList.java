package cs6301.g38;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class SkipList<T extends Comparable<? super T>> {

	class SkipListEntry {
		T element;
		ArrayList<SkipListEntry> next;

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

	SkipListEntry head, tail;
	int size;

	// Constructor
	public SkipList() {
		head = null;
		tail = null;
		size = 0;
	}

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

	// Add x to list. If x already exists, replace it. Returns true if new node is
	// added to list
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

	// Find smallest element that is greater or equal to x
	public T ceiling(T x) {
		return null;
	}

	// Does list contain x?
	public boolean contains(T x) {
		SkipListEntry temp = find(x);
		return temp.next.get(0).element == x;
	}

	// Return first element of list
	public T first() {
		return null;
	}

	// Find largest element that is less than or equal to x
	public T floor(T x) {
		return null;
	}

	// Return element at index n of list. First element is at index 0.
	public T get(int n) {
		return null;
	}

	// Is the list empty?
	public boolean isEmpty() {
		return false;
	}

	// Iterate through the elements of list in sorted order
	public Iterator<T> iterator() {
		return null;
	}

	// Return last element of list
	public T last() {
		return null;
	}

	// Reorganize the elements of the list into a perfect skip list
	public void rebuild() {

	}

	// Remove x from list. Removed element is returned. Return null if x not in list
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

	// Return the number of elements in the list
	public int size() {
		return size;
	}

	public static void main(String[] args) {
		SkipList<Integer> t = new SkipList<>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				t.add(x);
				t.printSkipList();
			} else if (x < 0) {
				t.remove(-x);
				t.printSkipList();
			} else {
				break;
			}
		}

		in.close();
	}

	private void printSkipList() {
		SkipListEntry temp = head;
		while (temp != tail) {
			System.out.println(temp);
			temp = temp.next.get(0);
		}
		System.out.println(temp);

	}
}
