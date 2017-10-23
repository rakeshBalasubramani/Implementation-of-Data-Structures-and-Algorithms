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

		public String toString() {
			return super.toString() + " Height: " + height;
		}

		public void setHeight() {
			height = height(this);
		}

		private int height(Entry<T> entry) {
			if (entry == null) {
				return -1;
			}
			int leftHeight = height((Entry<T>) entry.left);
			int rightHeight = height((Entry<T>) entry.right);
			if (leftHeight > rightHeight) {
				return 1 + leftHeight;
			} else {
				return 1 + rightHeight;
			}
		}
	}
	
	private int height(Entry<T> entry) {
		if (entry == null) {
			return -1;
		}
		int leftHeight = height((Entry<T>) entry.left);
		int rightHeight = height((Entry<T>) entry.right);
		if (leftHeight > rightHeight) {
			return 1 + leftHeight;
		} else {
			return 1 + rightHeight;
		}
	}

	AVLTree() {
		super();
	}

	private int balance(Entry<T> t) {
		return height((Entry<T>) t.left) - height((Entry<T>) t.right);
	}

	public boolean add(T x) {
		if (super.add(x)) {
			balanceTree(x);
			return true;
		}
		return false;
	}

	public T remove(T x) {
		T result = super.remove(x);
		if (result != null) {
			if(root==null) {
				return result;
			}
			balanceTree(x);
			return result;
		}
		return null;
	}

	private void balanceTree(T x) {
		Entry<T> currentNode = (Entry<T>) root;
		while (!stack.isEmpty()) {
			currentNode = (Entry<T>) stack.pop();
			int heightDiff = balance(currentNode);
			if (heightDiff > 1 && x.compareTo(currentNode.left.element) < 0) {
				rightRotate(currentNode);
			} else if (heightDiff < -1 && x.compareTo(currentNode.right.element) > 0) {
				leftRotate(currentNode);
			} else if (heightDiff > 1 && x.compareTo(currentNode.left.element) > 0) {
				stack.push(currentNode);
				leftRotate((Entry<T>) currentNode.left);
				stack.pop();
				rightRotate(currentNode);
			} else if (heightDiff < -1 && x.compareTo(currentNode.right.element) < 0) {
				stack.push(currentNode);
				rightRotate((Entry<T>) currentNode.right);
				stack.pop();
				leftRotate(currentNode);
			}
			adjustHeight(currentNode);
		}
		adjustHeight(currentNode);
	}
	private void adjustHeight(Entry<T> currentNode) {
		currentNode.setHeight();
		Entry<T> temp;
		if(currentNode.right!=null) {
			temp=(Entry<T>) currentNode.right;
			temp.setHeight();
		}
		if(currentNode.left!=null) {
			temp=(Entry<T>) currentNode.left;
			temp.setHeight();
		}
	}
	protected Entry<T> newEntry(T x) {
		return new Entry<T>(x, null, null);
	}

	private void leftRotate(Entry<T> node) {
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
		temp1.setHeight();
		if (temp2 != null) {
			temp2.setHeight();
		}
	}

	private void rightRotate(Entry<T> node) {
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
		temp1.setHeight();
		if (temp2 != null) {
			temp2.setHeight();
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
				System.out.println("Tree:");
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
}
