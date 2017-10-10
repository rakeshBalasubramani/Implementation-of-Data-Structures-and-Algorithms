
/** Starter code for AVL Tree
 */
package cs6301.g38;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        int height;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
	super();
    }
    private int height(BST.Entry<T> u) {
    	if(u==null) {
    		return -1;
    	}
    	int leftHeight = height(u.left);
    	int rightHeight = height(u.right);
    	if(leftHeight>rightHeight) {
    		return 1+leftHeight;
    	}
    	else {
    		return 1+rightHeight;
    	}
    }
    
    public boolean add(T x) {
    	super.add(x);
    	return false;
    }
}

