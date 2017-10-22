/** Starter code for Splay Tree
 */
package cs6301.g38;

import java.util.Comparator;
import java.util.Scanner;

import cs6301.g38.BST.Entry;

public class SplayTree<T extends Comparable<? super T>> extends BST<T> {
	SplayTree() {
		super();
	}

	public boolean add(T x) {
		if (super.add(x)) {
			if (root.element != x) {
				Entry<T> pt = root;
				Entry<T> t = root.left;
				if (x.compareTo(root.element) > 0) {
					t = root.right;
				}
				if (!stack.isEmpty()) {
					Entry<T> gt = (Entry<T>) stack.peek();
					if (x.compareTo(gt.element) < 0 && x.compareTo(gt.left.element) < 0) {
						pt = (Entry<T>) gt.left;
						t = (Entry<T>) pt.left;
					} else if (x.compareTo(gt.element) < 0 && x.compareTo(gt.left.element) > 0) {
						pt = (Entry<T>) gt.left;
						t = (Entry<T>) pt.right;
					} else if (x.compareTo(gt.element) > 0 && x.compareTo(gt.right.element) < 0) {
						pt = (Entry<T>) gt.right;
						t = (Entry<T>) pt.left;
					} else {
						pt = (Entry<T>) gt.right;
						t = (Entry<T>) pt.right;
					}
				}
				splay(t);
			}
			return true;
		}
		return false;
	}

	public boolean contains(T x) {
		Entry<T> t = find(x);
		splay(t);
		return (t != null && t.element == x);
	}

	private void splay(Entry<T> t) {

		while (t != root) {
			if (root.left == t) {
				rightRotate(root);
			} else if (root.right == t) {
				leftRotate(root);
			} else {
				Entry<T> gt = (Entry<T>) stack.peek();
				Entry<T> pt = null;
				if (t.element.compareTo(gt.element) < 0 && t.element.compareTo(gt.left.element) < 0) {
					pt = gt.left;
				} else if (t.element.compareTo(gt.element) < 0 && t.element.compareTo(gt.left.element) > 0) {
					pt = gt.left;
				} else if (t.element.compareTo(gt.element) > 0 && t.element.compareTo(gt.right.element) < 0) {
					pt = gt.right;
				} else {
					pt = gt.right;
				}
				if (gt.left == pt && pt.left == t) {
					stack.pop();
					rightRotate(gt);
					rightRotate(pt);
					if (!stack.isEmpty()) {
						stack.pop();
					}
				} else if (gt.right == pt && pt.right == t) {
					stack.pop();
					leftRotate(gt);
					leftRotate(pt);
					if (!stack.isEmpty()) {
						stack.pop();
					}
				} else if (gt.right == pt && pt.left == t) {
					rightRotate(pt);
					stack.pop();
					leftRotate(gt);
					if (!stack.isEmpty()) {
						stack.pop();
					}
				} else {
					leftRotate(pt);
					stack.pop();
					rightRotate(gt);
					if (!stack.isEmpty()) {
						stack.pop();
					}
				}
			}
		}
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
	}

	public T get(T x) {
		Entry<T> temp = find(x);
		if (temp != null && temp.element == x) {
			splay(temp);
			return temp.element;
		} else {
			return null;
		}
	}

	public T remove(T x) {
		T result = super.remove(x);
		if (root == null) {
			return result;
		} else {
			Entry<T> t = stack.pop();
			if (!stack.isEmpty()) {
				stack.pop();
			}
			splay(t);
		}
		return result;
	}

	public static void main(String[] args) {
		SplayTree<Integer> t = new SplayTree<>();
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
}
