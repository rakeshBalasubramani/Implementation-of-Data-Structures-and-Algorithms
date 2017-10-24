package cs6301.g38;

import java.util.Scanner;


/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to implement Red Black Tree
 * @param <T> - Type of element stored in the tree.
 */
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
			if (root == null) {
				return result;
			}
			fix(x);
			return result;
		}
		return null;
	}

	private void fix(T x) {
		Entry<T> pt, t, st;
		pt = (Entry<T>) stack.pop();
		if (x.compareTo(pt.element) < 0) {
			t = (Entry<T>) pt.left;
			st = (Entry<T>) pt.right;
		} else {
			t = (Entry<T>) pt.right;
			st = (Entry<T>) pt.left;
		}
		while (st == null || st.isBlack()) {
			if(t==null) {
				return;
			}
			if (t.isRed()) {
				t.setBlack();
				return;
			} else if (st != null) {
				if ((st.isBlack())
						&& ((st.left != null && st.right != null) || (st.left != null && ((Entry<T>) st.left).isBlack())
								|| (st.right != null && ((Entry<T>) st.right).isBlack())
								|| (((Entry<T>) st.left).isBlack() && ((Entry<T>) st.right).isBlack()))) {
					st.setRed();
					t = pt;
					if (!stack.isEmpty()) {
						pt = (Entry<T>) stack.pop();
						if (t == pt.left) {
							st = (Entry<T>) pt.right;
						} else {
							st = (Entry<T>) pt.left;
						}
					}
				}
			} else if (st == null) {
				t = pt;
				if (!stack.isEmpty()) {
					pt = (Entry<T>) stack.pop();
					if (t == pt.left) {
						st = (Entry<T>) pt.right;
					} else {
						st = (Entry<T>) pt.left;
					}
				}
			}
		}
		while (st.isBlack()) {
			if (st.isBlack()) {
				if (st.left != null && ((Entry<T>) st.left).isRed() && pt.left == st) {
					rightRotate(pt);
					pt.setBlack();
					st.setRed();
					((Entry<T>) st.left).setBlack();
					return;
				} else if (st.right != null && ((Entry<T>) st.right).isRed() && pt.right == st) {
					leftRotate(pt);
					pt.setBlack();
					st.setRed();
					((Entry<T>) st.right).setBlack();
					return;
				} else if (st.left != null && ((Entry<T>) st.left).isRed() && pt.right == st) {
					stack.push(pt);
					rightRotate(st);
					st.setRed();
					((Entry<T>) st.left).setBlack();
					stack.pop();
					leftRotate(pt);
					pt.setBlack();
					((Entry<T>) st.left).setRed();
					st.setBlack();
					return;
				} else {
					stack.push(pt);
					leftRotate(st);
					st.setRed();
					((Entry<T>) st.right).setBlack();
					stack.pop();
					rightRotate(pt);
					pt.setBlack();
					((Entry<T>) st.right).setRed();
					st.setBlack();
					return;
				}
			} else {
				if (pt.left == t) {
					leftRotate(pt);
					pt.setRed();
					st.setBlack();
					st = (Entry<T>) pt.right;
				} else {
					rightRotate(pt);
					pt.setRed();
					st.setBlack();
					st = (Entry<T>) pt.left;
				}
			}
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
