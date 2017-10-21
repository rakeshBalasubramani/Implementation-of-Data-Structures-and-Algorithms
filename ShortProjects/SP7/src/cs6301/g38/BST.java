package cs6301.g38;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements Iterable<T> {
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
			} else {
				return "Left: " + left.element + " Element: " + element;
			}
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

	public boolean contains(T x) {
		Entry<T> t = find(x);
		return (t != null && t.element == x);
	}

	/**
	 * TO DO: Is there an element that is equal to x in the tree? Element in tree
	 * that is equal to x is returned, null otherwise.
	 */
	public T get(T x) {
		Entry<T> temp = find(x);
		if (temp != null && temp.element == x) {
			return temp.element;
		} else {
			return null;
		}
	}

	protected Entry<T> find(T x) {
		stack.clear();
		stack.push(null);
		return find(root, x);
	}

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
	 * TO DO: Add x to tree. If tree contains a node with same key, replace element
	 * by x. Returns true if x is a new element added to tree.
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

	private Entry<T> newEntry(T x) {
		return new Entry<T>(x, null, null);
	}

	/**
	 * TO DO: Remove x from tree. Return x if found, otherwise return null
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

	private void bypass(Entry<T> temp) {
		Entry<T> pt = stack.peek();
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

	T min() {
		if (root == null) {
			return null;
		}
		Entry<T> temp = root;
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp.element;
	}

	T max() {
		if (root == null) {
			return null;
		}
		Entry<T> temp = root;
		while (temp.right != null) {
			temp = temp.right;
		}
		return temp.element;
	}

	/**
	 * TO DO: Iterate elements in sorted order of keys
	 */
	public Iterator<T> iterator() {
		return null;
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
		in.close();
	}

	// TODO: Create an array with the elements using in-order traversal of tree
	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		int i = 0;
		inOrder(arr, i, root);
		return arr;
	}

	private void inOrder(Comparable[] arr, int i, Entry<T> root2) {
		if (root2 == null) {
			return;
		}
		inOrder(arr, i, root2.left);
		arr[i++] = root2.element;
		inOrder(arr, i, root2.right);
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	// Inorder traversal of tree
	void printTree(Entry<T> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
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
