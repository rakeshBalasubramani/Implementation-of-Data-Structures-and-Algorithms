package cs6301.g38;

import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

import cs6301.g38.BST.Entry;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
	static class Entry<T> extends BST.Entry<T> {
		int height;

		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			height = 0;
		}

		public Entry(BST.Entry<T> t) {
			super(t);
		}

		public String toString() {
			return super.toString() + " Height: " + height;
		}
	}
	//Entry<T> root;
	AVLTree() {
		super();
	}

	private int height(BST.Entry<T> node) {
		if (node == null) {
			return -1;
		}
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		if (leftHeight > rightHeight) {
			return 1 + leftHeight;
		} else {
			return 1 + rightHeight;
		}
	}

	private boolean isBalanced(Entry<T> t) {
		int lh = height(t.left);
		int rh = height(t.right);
		if (lh - rh >= 2 || rh - lh >= 2) {
			return false;
		} else
			return true;
	}

	public boolean add(T x) {
		if (super.add(x)) {
			while (!stack.isEmpty()) {
				BST.Entry<T> temp = stack.pop();
				if (temp != null) {
					temp = new Entry<T>(temp);
					Entry<T> temp1 = (Entry<T>) temp;
					temp1.height = height(temp);
					if (!isBalanced(temp1)) {
						if (height(temp1.left) > height(temp1.right)) {
							temp = new Entry<T>(rightRotate(temp1));
							temp1 = (Entry<T>) temp;
						} else {
							temp = new Entry<T>(leftRotate(temp1));
							temp1 = (Entry<T>) temp;
						}
					}
					System.out.println(temp1);
				}
			}
		}
		return false;
	}
	private BST.Entry<T> newEntry(T x) {
		return new Entry<T>(x, null, null);
	}
	
	private BST.Entry<T> leftRotate(Entry<T> node) {
		BST.Entry<T> temp1 = node.right;
		BST.Entry<T> temp2 = temp1.left;
		temp1.left = node;
		node.right = temp2;
		return temp1;
	}

	private BST.Entry<T> rightRotate(Entry<T> node) {
		BST.Entry<T> temp1 = node.left;
		BST.Entry<T> temp2 = temp1.right;
		temp1.right = node;
		node.left = temp2;
		return temp1;
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		BST.Entry<T> r = new Entry<T>(root);
		Entry<T> t = (Entry<T>) r;
		t.height = height(r);
		printTree(t);
		System.out.println();
	}

	// Inorder traversal of tree
	void printTree(Entry<T> node) {
		if (node != null) {
			if (node.left != null) {
				BST.Entry<T> l = new Entry<T>(node.left);
				Entry<T> tempL = (Entry<T>) l;
				tempL.height = height(l);
				printTree(tempL);
			}
			System.out.print(" " + node.element + " " + node.height);
			if (node.right != null) {
				BST.Entry<T> l = new Entry<T>(node.right);
				Entry<T> tempL = (Entry<T>) l;
				tempL.height = height(l);
				printTree(tempL);
			}
		}
	}

	public static void main(String[] args) {
		AVLTree<Integer> t = new AVLTree<>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				//t.printTree();
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
}
