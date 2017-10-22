
/** Starter code for Red-Black Tree
 */
package cs6301.g38;

import java.util.Comparator;
import java.util.Scanner;

import cs6301.g38.AVLTree.Entry;

public class RedBlackTree<T extends Comparable<? super T>> extends BST<T> {
	static class Entry<T> extends BST.Entry<T> {
		boolean isRed;
		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			isRed = true;
		}

		public String toString() {
			if (isRed) {
				return super.toString() + " Color: Red";
			} else {
				return super.toString() + " Color: Black";
			}
		}

		void setBlack() {
			isRed = false;
		}

		void setRed() {
			isRed = true;
		}

		boolean isBlack() {
			return !isRed;
		}

		boolean isRed() {
			return isRed;
		}
	}

	RedBlackTree() {
		super();
	}

	public boolean add(T x) {
		if (super.add(x)) {
			repair(x);
			Entry<T> tempRoot = (Entry<T>) root;
			tempRoot.setBlack();
			return true;
		}
		return false;
	}
	
	public T remove(T x) {
		T result = super.remove(x);
		if (result != null) {
			fix(x);
			return result;
		}
		return null;
	}

	private void fix(T x) {
		if(((Entry<T>) stack.pop()).isRed()) {
			
		}
	}

	private void repair(T x) {
		if (!stack.isEmpty()) {
			Entry<T> t, pt, ut;
			Entry<T> gt = (Entry<T>) stack.pop();
			if (x.compareTo(gt.element) < 0 && x.compareTo(gt.left.element) < 0) {
				pt = (Entry<T>) gt.left;
				ut = (Entry<T>) gt.right;
				t = (Entry<T>) pt.left;
			} else if (x.compareTo(gt.element) < 0 && x.compareTo(gt.left.element) > 0) {
				pt = (Entry<T>) gt.left;
				ut = (Entry<T>) gt.right;
				t = (Entry<T>) pt.right;
			} else if (x.compareTo(gt.element) > 0 && x.compareTo(gt.right.element) < 0) {
				pt = (Entry<T>) gt.right;
				ut = (Entry<T>) gt.left;
				t = (Entry<T>) pt.left;
			} else {
				pt = (Entry<T>) gt.right;
				ut = (Entry<T>) gt.left;
				t = (Entry<T>) pt.right;
			}
			while (t.isRed()) {
				if (root == t || pt == root || pt.isBlack()) {
					return;
				}
				if (ut != null && ut.isRed()) {
					pt.setBlack();
					ut.setBlack();
					gt.setRed();
					t = gt;
					if (!stack.isEmpty()) {
						pt = (Entry<T>) stack.pop();
						if (!stack.isEmpty()) {
							gt = (Entry<T>) stack.pop();
							ut = (Entry<T>) gt.left;
							if (gt.left == pt) {
								ut = (Entry<T>) gt.right;
							}
						}
					}
					continue;
				} else if (ut == null || ut.isBlack()) {
					if (gt.left == pt && pt.left == t) {
						rightRotate(gt);
						pt.setBlack();
						gt.setRed();
						return;
					} else if (gt.right == pt && pt.right == t) {
						leftRotate(gt);
						pt.setBlack();
						gt.setRed();
						return;
					}
				} else if (ut == null || ut.isBlack()) {
					if (gt.left == pt && pt.right == t) {
						leftRotate(pt);
						rightRotate(gt);
						pt.setBlack();
						gt.setRed();
						return;
					} else if (gt.right == pt && pt.left == t) {
						rightRotate(pt);
						leftRotate(gt);
						pt.setBlack();
						gt.setRed();
						return;

					}
				}
			}
		}
	}

	protected Entry<T> newEntry(T x) {
		return new Entry<T>(x, null, null);
	}

	public static void main(String[] args) {
		RedBlackTree<Integer> t = new RedBlackTree<>();
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
