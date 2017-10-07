// Ver 1.0:  Starter code for bounded size Binary Heap implementation

package cs6301.g38;
import java.util.Comparator;

public class BinaryHeap<T> {
    T[] pq;
    Comparator<T> c;
    int size;
    /** Build a priority queue with a given array q, using q[0..n-1].
     *  It is not necessary that n == q.length.
     *  Extra space available can be used to add new elements.
     */
    public BinaryHeap(T[] q, Comparator<T> comp, int n) {
	pq = q;
	c = comp;
	size=n-1;
    }

    public void insert(T x) throws Exception {
	add(x);
    }

    public T deleteMin() {
	return remove();
    }

    public T min() { 
	return peek();
    }

    public void add(T x) throws Exception { /* TO DO. Throw exception if q is full. */
    	if(size==pq.length)
    	{
    		throw new Exception( " Queue is Full");
    	}
    	
    	pq[size] = x ;
    	 percolateUp(size);
    	 size++;
    }

    public T remove() { /* TO DO. Throw exception if q is empty. */
    	
    	if(size < 0)
    	{
    		return null;
    	}
    	T min = pq[0];
    	if (size ==0)
    	{
    		size--;
    	}
    	else
    	{
        move(0,pq[size--]);//	pq[0]= pq[size--];
        	percolateDown(0);
    	}
	return min;
    }

    public T peek() { /* TO DO. Throw exception if q is empty. */
    	
    	// handle empty case
    	if(size < 0)
    	{
    		return null;
    	}
    	
    	return pq[0];
    
	
    }

    public void replace(T x) {
	/* TO DO.  Replaces root of binary heap by x if x has higher priority
	     (smaller) than root, and restore heap order.  Otherwise do nothing. 
	   This operation is used in finding largest k elements in a stream.
	 */
    	if(size < 0)
    	{
    		pq[0]=x;
    		size++;
    	}
    	
    	if(c.compare(x, peek())<0)
    	{
    		pq[0]=x;
    		percolateDown(0);
    	}
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) { /* to be implemented */
    	
    	T x = pq[i];
    	
    	while(i>0 && c.compare(x, pq[parent(i)])<0)
    	{
    		move(i,pq[parent(i)]);//pq[i] = pq[parent(i)];
    		i = parent(i);
    	}
    	
    move(i,x);//	pq[i] = x;
    	
    	
    }

    /** pq[i] may violate heap order with children */
    void percolateDown(int i) { /* to be implemented */
    	
    	T x = pq[i];
    	int child = 2*i+1;
    	while(child<=size-1)
    	{
    		if(child<size-1 && c.compare(pq[child], pq[child+1])>0)
    		{
    			child++;
    		}
    		if(c.compare(x, pq[child])<=0)
    		{
    	break;
    		}
    		move(i,pq[child]);//	pq[i]= pq[child];
			i = child;
			child= 2*i+1;
    		
    	}
    
    	move(i,x);//pq[i]=x;
    	}

    /** Create a heap.  Precondition: none. */
    void buildHeap() {
    	
    	for(int i = parent(size-1); i>=0; i--)
    	{
    		percolateDown(i);
    	}
    }
    
     int parent( int child)
    {
    	return (child-1)/2;
    }
     
     void move(int i, T x)
     {
    	
    	 pq[i]=x;
     }

    /* sort array A[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static<T> void heapSort(T[] A, Comparator<T> comp) { /* to be implemented */
    	
    	BinaryHeap<T> heap = new BinaryHeap<T>(A, comp, A.length);
    	
    	heap.buildHeap();
    	int len = A.length-1;
    	while(heap.peek()!=null)
    	{
    		A[len]=(heap.remove());
    		len--;
    	}
    	
    
    	
    	
    }
    
    
    public static void main(String args[])
    {
    	Integer []a ={7,3,2,8,4,6,9,1,5};
    	
    	Comparator<Integer> c = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return -1*Integer.compare(o1, o2);
			}
    		
		};
    	
    	BinaryHeap.heapSort(a, c);
    }
}
