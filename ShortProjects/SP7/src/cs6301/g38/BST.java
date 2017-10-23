package cs6301.g38;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to implement Binary Search Tree
 * @param <T> - Type of element stored in the tree.
 */
public class BST<T extends Comparable<? super T>> implements Iterable<T> {
	/**
	 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
	 *
	 * @Desc Class implementing nodes in the tree.
	 * @param <T> - Type of the element.
	 */
	static class Entry<T> implements Comparable<T> {
		T element;
		Entry<T> left, right;

		Entry(T x, Entry<T> left, Entry<T> right) {
			this.element = x;
			this.left = left;
			this.right = right;
		}

		public Entry(Entry<T> t) {
			element = t.element;
			left = t.left;
			right = t.right;
		}

		public int compareTo(T o) {
			if (element == o) {
				return 0;
			} else if (((Comparable<T>) element).compareTo(o) < 0) {
				return -1;
			} else
				return 1;
		}

		public String toString() {
			if (left != null && right != null) {
				return "Left: " + left.element + " Element: " + element + " Right: " + right.element;
			} else if (right != null) {
				return "Element: " + element + " Right: " + right.element;
			} else if(left != null){
				return "Left: " + left.element + " Element: " + element;
			}
			else
				return "Element: "+element;
		}
	}

	Entry<T> root;
	int size;
	Stack<Entry<T>> stack;

	public BST() {
		root = null;
		size = 0;
		stack = new Stack<Entry<T>>();
	}
	/**
	 * Rotate the tree towards the left around node
	 * @param node - Rotating around node.
	 */
	protected void leftRotate(Entry<T> node) {
		Entry<T> temp1 = (Entry<T>) node.right;
		Entry<T> temp2 = (Entry<T>) temp1.left;
		temp1.left = node;
		node.right = temp2;
		if (!stack.isEmpty()) {
			if (stack.peek().element.compareTo(temp1.element) < 0) {
				stack.peek().right = temp1;
			} else {
				stack.peek().left = temp1;
			}

		} else {
			root = temp1;
		}
	}

	/**
	 * Rotate the tree towards the right around node
	 * @param node - Rotating around node.
	 */
	protected void rightRotate(Entry<T> node) {
		Entry<T> temp1 = (Entry<T>) node.left;
		Entry<T> temp2 = (Entry<T>) temp1.right;
		temp1.right = node;
		node.left = temp2;
		if (!stack.isEmpty()) {
			if (stack.peek().element.compareTo(temp1.element) < 0) {
				stack.peek().right = temp1;
			} else {
				stack.peek().left = temp1;
			}
		} else {
			root = temp1;
		}
	}
	/**
	 * Function to see if the tree contains.
	 * @param x - to see if tree contains x
	 * @return True if contains else false
	 */
	public boolean contains(T x) {
		Entry<T> t = find(x);
		return (t != null && t.element == x);
	}

	/**
	 * Get element x from tree.
	 * @param x - Given element
	 * @return Element if found else null.
	 */
	public T get(T x) {
		Entry<T> temp = find(x);
		if (temp != null && temp.element == x) {
			return temp.element;
		} else {
			return null;
		}
	}

	/**Helper function to find x in tree.
	 * @param x - Given element
	 * @return - Node in tree.
	 */
	protected Entry<T> find(T x) {
		stack.clear();
		return find(root, x);
	}

	/**
	 * Function to find x
	 * @param t - Starting node.
	 * @param x - Element.
	 * @return
	 */
	protected Entry<T> find(Entry<T> t, T x) {
		if (t == null || t.element == x) {
			return t;
		}
		while (true) {
			if (x.compareTo(t.element) < 0) {
				if (t.left == null) {
					break;
				} else {
					stack.push(t);
					t = t.left;
				}
			} else if (x == t.element) {
				break;
			} else {
				if (t.right == null) {
					break;
				} else {
					stack.push(t);
					t = t.right;
				}
			}
		}
		return t;
	}

	/**
	 * Function to add element to tree.
	 * @param x - Element to be added.
	 * @return True if addition was successfull else false.
	 */
	public boolean add(T x) {
		if (root == null) {
			root = newEntry(x);
			size = 1;
			return true;
		}
		Entry<T> temp = find(x);
		if (x == temp.element) {
			temp.element = x;
			return false;
		} else if (x.compareTo(temp.element) < 0) {
			temp.left = newEntry(x);
		} else {
			temp.right = newEntry(x);
		}
		size++;
		return true;
	}

	/**
	 * Create new node in tree.
	 * @param x - Element
	 * @return	- new node.
	 */
	protected Entry<T> newEntry(T x) {
		return new Entry<T>(x, null, null);
	}

	/**
	 * Function to remove an element from tree.
	 * @param x - Element to be removed
	 * @return - True if removal was successfull else false.
	 */
	public T remove(T x) {
		if (root == null) {
			return null;
		}
		Entry<T> temp = find(x);
		if (temp.element != x) {
			return null;
		}
		T result = temp.element;
		if (temp.left == null || temp.right == null) {
			bypass(temp);
		} else {
			stack.push(temp);
			Entry<T> minRight = find(temp.right, temp.element);
			temp.element = minRight.element;
			bypass(minRight);
		}
		size--;
		return result;
	}

	/**
	 * To bypass the removed node.
	 * @param temp - The node to be bypassed.
	 */
	private void bypass(Entry<T> temp) {
		Entry<T> pt=null;
		if(!stack.isEmpty()) {
			pt = stack.peek();
		}
		Entry<T> c;
		if (temp.left == null) {
			c = temp.right;
		} else {
			c = temp.left;
		}
		if (pt == null) {
			root = c;
		} else if (pt.left == temp) {
			pt.left = c;
		} else {
			pt.right = c;
		}
	}

	/**
	 * Find min in tree
	 * @return - min of tree
	 */
	Entry<T> min() {
		if (root == null) {
			return null;
		}
		Entry<T> temp = root;
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp;
	}

	/**
	 * Find max in tree
	 * @return - max of tree.
	 */
	Entry<T> max() {
		if (root == null) {
			return null;
		}
		Entry<T> temp = root;
		while (temp.right != null) {
			temp = temp.right;
		}
		return temp;
	}

	public Iterator<T> iterator() {
		return new TreeIterator(root);
	}
	
	/**
	 * @author Rajkumar PanneerSelvam - rxp162130 <br>
	 *         Avinash Venkatesh - axv165330 <br>
	 *         Rakesh Balasubramani - rxb162130 <br>
	 *         HariPriyaa Manian - hum160030
	 * @Desc Class to implement Iterator         
	 *
	 */
	class TreeIterator implements Iterator<T> {
		Stack<Entry<T>> stack;
		 
		public TreeIterator(Entry<T> root) {
			stack = new Stack<Entry<T>>();
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
		}
	 
		public boolean hasNext() {
			return !stack.isEmpty();
		}
	 
		public T next() {
			Entry<T> entry = stack.pop();
			T result = entry.element;
			if (entry.right != null) {
				entry = entry.right;
				while (entry != null) {
					stack.push(entry);
					entry = entry.left;
				}
			}
			return result;
		}
		
	}

	public static void main(String[] args) {
		BST<Integer> t = new BST<>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int i = 0; i < t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				return;
			}
		}
		
		for(int i : t)
		{
			System.out.println(i);
		}
		in.close();
	}

	/**Function to get inorder Traversal of tree.
	 * @return - inOrder Array
	 */
	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		ArrayList<T> temp = new ArrayList<T>();
		inOrder(temp,root);
		int j=0;
		for(T i:temp) {
			arr[j++]=i;
		}
		return arr;
	}

	private void inOrder(ArrayList<T>temp, Entry<T> root2) {
		if (root2 == null) {
			return;
		}
		inOrder(temp, root2.left);
		temp.add(root2.element);
		inOrder(temp, root2.right);
	}

	/**
	 * Function to print tree.
	 */
	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	void printTree(Entry<T> node) {
		if (node != null) {
			printTree(node.left);
			System.out.println(node.element);
			printTree(node.right);
		}
	}

}
/*
 * Sample input: 1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0
 * 
 * Output: Add 1 : [1] 1 Add 3 : [2] 1 3 Add 5 : [3] 1 3 5 Add 7 : [4] 1 3 5 7
 * Add 9 : [5] 1 3 5 7 9 Add 2 : [6] 1 2 3 5 7 9 Add 4 : [7] 1 2 3 4 5 7 9 Add 6
 * : [8] 1 2 3 4 5 6 7 9 Add 8 : [9] 1 2 3 4 5 6 7 8 9 Add 10 : [10] 1 2 3 4 5 6
 * 7 8 9 10 Remove -3 : [9] 1 2 4 5 6 7 8 9 10 Remove -6 : [8] 1 2 4 5 7 8 9 10
 * Remove -3 : [8] 1 2 4 5 7 8 9 10 Final: 1 2 4 5 7 8 9 10
 * 
 */
