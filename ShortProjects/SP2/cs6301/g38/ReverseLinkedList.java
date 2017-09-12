package cs6301.g38;

import java.util.Scanner;
import java.util.Stack;

import cs6301.g38.SinglyLinkedList.Entry;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Code used to reverse the order of elements of the
 *              SinglyLinkedList and to print the elements of SinglyLinkedList
 *              in reverse order using recursive and non-recursive functions.
 */

public class ReverseLinkedList {

	/**
	 * Non-recursive function to print the elements of the SinglyLinkedList in
	 * reverse order.
	 * 
	 * @param list
	 *            - A SinglyLinkedList whose elements need to be printed in
	 *            reverse order.
	 */
	public static <T> void printListRevIter(SinglyLinkedList<T> list) {

		Stack<T> revlist = new Stack<T>();

		for (T item : list) {
			revlist.push(item);
		}

		while (!revlist.isEmpty()) {
			System.out.print(revlist.pop() + " ");
		}
		System.out.println();

	}

	/**
	 * Recursive function to print the elements of the SinglyLinkedList in
	 * reverse order.
	 * 
	 * @param head
	 *            - denotes the head of the SinglyLinkedList.
	 */
	public static <T> void printListRevRec(Entry<T> head) {

		if (head == null) {
			return;
		}

		printListRevRec(head.next);

		System.out.print(head.element + " ");

	}

	/**
	 * Function to reverse the order of elements of a SinglyLinkedList.
	 * 
	 * @param list
	 *            - SinglyLinkedList whose elements have to be reversed.
	 * @return SinglyLinkedList whose elements are in the reverse order.
	 */
	public static <T> SinglyLinkedList<T> reverseLinkedListRecursive(SinglyLinkedList<T> list) {

		Entry<T> cur, prev;
		prev = null; // null for previous element of head.
		cur = list.head.next;

		list.head.next = reverse(cur, prev, list);
		return list;
	}

	/**
	 * Recursive function implementation to reverse the elements of a
	 * SinglyLinkedList.
	 * 
	 * @param cur
	 *            - Indicates the current element of the list.
	 * @param prev
	 *            - Indicates the previous element of the current element.
	 * @param list
	 *            - SinglyLinkedList whose elements need to be reversed.
	 * @return Head of the SinglyLinkedList.
	 */
	private static <T> Entry<T> reverse(Entry<T> cur, Entry<T> prev, SinglyLinkedList<T> list) {

		Entry<T> temp;
		if (cur.next == null) { // Check for end of the list
			list.head.next = cur;

			cur.next = prev;
			return null;
		}

		temp = cur.next;
		cur.next = prev;

		reverse(temp, cur, list);
		return list.head.next;
	}

	/**
	 * Non-recursive function to reverse the order of elements of a
	 * SinglyLinkedList.
	 * 
	 * @param list
	 *            - SinglyLinkedList whose elements have to be reversed.
	 * @return SinglyLinkedList whose elements are in the reverse order.
	 */
	public static <T> SinglyLinkedList<T> reverseLinkedList(SinglyLinkedList<T> list) {
		Entry<T> cur, prev, next;

		prev = null; // null for previous element of head.
		cur = list.head.next;
		list.tail = cur;

		// Invariant: cur is the current element to be processed.
		// next denotes the next element of current element.
		// prev denotes the previous element of the current element.
		while (cur != null) {
			next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		list.head.next = prev;
		return list;
	}

	/**
	 * Function to print the elements of the SinglyLinkedList.
	 * 
	 * @param list
	 *            - SinglyLinkedList whose elements are printed.
	 */
	private static <T> void print(SinglyLinkedList<T> list) {
		for (T item : list) {
			System.out.print(item + " ");
		}

		System.out.println();
	}

	public static void main(String[] args) {
		int n;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the size of the LinkedList");
		n = s.nextInt();

		SinglyLinkedList<Integer> newList = new SinglyLinkedList<>();
		SinglyLinkedList<Integer> newListItr = new SinglyLinkedList<>();
		SinglyLinkedList<Integer> newListRec = new SinglyLinkedList<>();

		// Populating the SinglyLinkedList with elements
		for (int i = 1; i <= n; i++) {
			newList.add(new Integer(i));
			newListItr.add(new Integer(i));
			newListRec.add(new Integer(i));
		}

		System.out.println("Elements of the LinkedList");
		print(newList);

		System.out.println("Elements of the reversed LinkedList using Non-recursive function");
		newListItr = reverseLinkedList(newListItr);
		print(newListItr);

		System.out.println("Elements of the reversed LinkedList using recursive function");
		newListRec = reverseLinkedListRecursive(newListRec);
		print(newListRec);

		System.out.println("Elements of the LinkedList printed in reverse order using Non-recursive function");
		printListRevIter(newList);

		System.out.println("Elements of the LinkedList printed in reverse order using recursive function");
		printListRevRec(newList.head.next);

		s.close();
	}

}
