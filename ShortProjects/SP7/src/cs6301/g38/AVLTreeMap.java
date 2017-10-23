package cs6301.g38;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Desc TreeMap implementation using AVL Tree.
 */


public class AVLTreeMap<K extends Comparable<? super K>, V> implements
		Iterable<K> {

	private Entry<K, V> root;
	private int size;
	private Stack<Entry<K, V>> stack;

	public AVLTreeMap() {
		root = null;
		size = 0;
		stack = new Stack<Entry<K, V>>();
	}

	static final class Entry<K, V> implements Comparable<K> {
		K key;
		V value;
		Entry<K, V> left = null;
		Entry<K, V> right = null;
		int height;

		Entry(K key, V value) {
			this.key = key;
			this.value = value;
			this.height = 0;
		}

		public Entry(Entry<K, V> t) {
			this.key = t.key;
			this.value = t.value;
			left = t.left;
			right = t.right;
			height = t.height;
		}

		public int compareTo(K o) {
			if (key == o) {
				return 0;
			} else if (((Comparable<K>) key).compareTo(o) < 0) {
				return -1;
			} else
				return 1;
		}

		public void setHeight() {
			height = height(this);
		}

		private int height(Entry<K, V> entry) {
			if (entry == null) {
				return -1;
			}
			int leftHeight = height((Entry<K, V>) entry.left);
			int rightHeight = height((Entry<K, V>) entry.right);
			if (leftHeight > rightHeight) {
				return 1 + leftHeight;
			} else {
				return 1 + rightHeight;
			}
		}

		public String toString() {
			if (left != null && right != null) {
				return "Left: " + left.key + " Element: " + key + " Right: "
						+ right.key + " Height: " + height;
			} else if (right != null) {
				return "Element: " + key + " Right: " + right.key + " Height: "
						+ height;
			} else if (left != null) {
				return "Left: " + left.key + " Element: " + key + " Height: "
						+ height;
			} else
				return "Element: " + key + " Height: " + height;
		}

	}

	public boolean containsKey(K x) {
		Entry<K, V> t = find(x);
		return (t != null && t.key == x);
	}

	public V get(K key) {
		Entry<K, V> t = find(key);
		if (t != null && t.key == key) {
			return t.value;
		}
		return null;

	}

	private Entry<K, V> find(K x) {
		stack.clear();
		return find(root, x);
	}

	private Entry<K, V> find(Entry<K, V> t, K x) {
		if (t == null || t.key == x) {
			return t;
		}
		while (true) {
			if (x.compareTo(t.key) < 0) {
				if (t.left == null) {
					break;
				} else {
					stack.push(t);
					t = t.left;
				}
			} else if (x == t.key) {
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

	public boolean put(K key, V value) {
		if (root == null) {
			root = new cs6301.g38.AVLTreeMap.Entry<K, V>(key, value);
			size = 1;
			return true;
		}
		Entry<K, V> temp = find(key);
		if (key == temp.key) {
			temp.key = key;
			return false;
		} else if (key.compareTo(temp.key) < 0) {
			temp.left = new cs6301.g38.AVLTreeMap.Entry<K, V>(key, value);
		} else {
			temp.right = new cs6301.g38.AVLTreeMap.Entry<K, V>(key, value);
		}
		size++;
		balanceTree(key);
		return true;
	}

	public V remove(K key) {
		if (root == null) {
			return null;
		}
		Entry<K, V> temp = find(key);
		if (temp.key != key) {
			return null;
		}
		V result = temp.value;
		if (temp.left == null || temp.right == null) {
			bypass(temp);
		} else {
			stack.push(temp);
			Entry<K, V> minRight = find(temp.right, temp.key);
			temp.key = minRight.key;
			temp.value = minRight.value;
			bypass(minRight);
		}
		size--;

		if (root == null) {
			return result;
		}

		balanceTree(key);
		return result;
	}

	private int height(Entry<K, V> right) {
		if (right == null) {
			return -1;
		}
		int leftHeight = height((Entry<K, V>) right.left);
		int rightHeight = height((Entry<K, V>) right.right);
		if (leftHeight > rightHeight) {
			return 1 + leftHeight;
		} else {
			return 1 + rightHeight;
		}
	}

	private int balance(Entry<K, V> t) {
		return height((Entry<K, V>) t.left) - height((Entry<K, V>) t.right);
	}

	private void bypass(Entry<K, V> temp) {
		Entry<K, V> pt = null;
		if (!stack.isEmpty()) {
			pt = stack.peek();
		}
		Entry<K, V> c;
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

	private void leftRotate(Entry<K, V> node) {
		Entry<K, V> temp1 = (Entry<K, V>) node.right;
		Entry<K, V> temp2 = (Entry<K, V>) temp1.left;
		temp1.left = node;
		node.right = temp2;
		if (!stack.isEmpty()) {
			if (stack.peek().key.compareTo(temp1.key) < 0) {
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
		node.setHeight();
	}

	private void rightRotate(Entry<K, V> node) {
		Entry<K, V> temp1 = (Entry<K, V>) node.left;
		Entry<K, V> temp2 = (Entry<K, V>) temp1.right;
		temp1.right = node;
		node.left = temp2;
		if (!stack.isEmpty()) {
			if (stack.peek().key.compareTo(temp1.key) < 0) {
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
		
		node.setHeight();
	}

	private void balanceTree(K x) {
		Entry<K, V> currentNode = (Entry<K, V>) root;
		while (!stack.isEmpty()) {
			currentNode = (Entry<K, V>) stack.pop();
			int heightDiff = balance(currentNode);
			if (heightDiff > 1 && x.compareTo(currentNode.left.key) < 0) {
				rightRotate(currentNode);
			} else if (heightDiff < -1
					&& x.compareTo(currentNode.right.key) > 0) {
				leftRotate(currentNode);
			} else if (heightDiff > 1 && x.compareTo(currentNode.left.key) > 0) {
				stack.push(currentNode);
				leftRotate((Entry<K, V>) currentNode.left);
				stack.pop();
				rightRotate(currentNode);
			} else if (heightDiff < -1
					&& x.compareTo(currentNode.right.key) < 0) {
				stack.push(currentNode);
				rightRotate((Entry<K, V>) currentNode.right);
				stack.pop();
				leftRotate(currentNode);
			}
			currentNode.setHeight();
			
			
			adjustHeight(currentNode);

		}
		adjustHeight(currentNode);

	}
	
	private void adjustHeight(Entry<K,V> currentNode) {
		currentNode.setHeight();
		Entry<K,V> temp;
		if(currentNode.right!=null) {
			temp=(Entry<K,V>) currentNode.right;
			temp.setHeight();
		}
		if(currentNode.left!=null) {
			temp=(Entry<K,V>) currentNode.left;
			temp.setHeight();
		}
	}

	public K firstKey() {
		if (root == null) {
			return null;
		}
		Entry<K, V> temp = root;
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp.key;
	}

	public K lastKey() {
		if (root == null) {
			return null;
		}
		Entry<K, V> temp = root;
		while (temp.right != null) {
			temp = temp.right;
		}
		return temp.key;
	}

	public void printTree() {
		printTree(root);
		System.out.println();
	}

	// Inorder traversal of tree
	private void printTree(Entry<K, V> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(node.key+ " ");
			printTree(node.right);
		}
	}

	@Override
	public Iterator<K> iterator() {
		return new TreeMapIterator(root);
	}
	
	class TreeMapIterator implements Iterator<K> {
		Stack<Entry<K,V>> stack;
		 
		public TreeMapIterator(Entry<K,V> root) {
			stack = new Stack<Entry<K,V>>();
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
		}
	 
		public boolean hasNext() {
			return !stack.isEmpty();
		}
	 
		public K next() {
			Entry<K,V> entry = stack.pop();
			K result = entry.key;
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

	public static void main(String args[]) {

		AVLTreeMap<Integer, Integer> map = new AVLTreeMap<Integer, Integer>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int key = in.nextInt();

			if (key > 0) {
				int value = in.nextInt();
				System.out.print("Add " + key + " : " + value);
				map.put(key, value);
				map.printTree();
			} else if (key < 0) {
				System.out.print("Remove " + key + " : ");
				map.remove(-key);
				map.printTree();
			} else {
				System.out.println("Print: ");
				map.printTree();
				break;
			}
		}
		System.out.println();
System.out.println("Final : ");
	
	for(int i : map)
	{
		System.out.println("key " + i  + " value " + map.get(i));
	}

		
		in.close();

	}
}
