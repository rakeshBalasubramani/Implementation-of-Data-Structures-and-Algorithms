// Ver 1.0:  Starter code for Indexed heaps

package cs6301.g38;
import java.util.Arrays;
import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
	
    /** Build a priority queue with a given array q */
    public IndexedHeap(T[] q, Comparator<T> comp, int n) {
		super(q, comp, n);
	
    }

   
   public void move(int i, T x)
    {
    	super.move(i, x);
    	x.putIndex(i);   	
    }
     
    
    /** restore heap order property after the priority of x has decreased */
    public void decreaseKey(T x) {
	percolateUp(x.getIndex());
    }
}
