
/** @author rbk
 *  Ver 1.0: 2017/09/29
 *  Example to extend Graph/Vertex/Edge classes to implement algorithms in which nodes and edges
 *  need to be disabled during execution.  Design goal: be able to call other graph algorithms
 *  without changing their codes to account for disabled elements.
 *
 *  Ver 1.1: 2017/10/09
 *  Updated iterator with boolean field ready. Previously, if hasNext() is called multiple
 *  times, then cursor keeps moving forward, even though the elements were not accessed
 *  by next().  Also, if program calls next() multiple times, without calling hasNext()
 *  in between, same element is returned.  Added UnsupportedOperationException to remove.
 **/

package cs6301.g38;
import cs6301.g38.Graph.Vertex;
import cs6301.g38.Graph.Edge;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class BellManFordGraphOld extends Graph {
	
	private  static boolean  isTightEdgeOnly=false;
	
    public static class BVertex extends Vertex {
	boolean seen;
	List<BEdge> badj;
	long distance = Long.MAX_VALUE;
	List<BEdge> tightEdge;
	int count;

	BVertex(Vertex u) {
	    super(u);
	    seen = false;
	    badj = new LinkedList<>();
	    tightEdge = new LinkedList<>();

	}

	boolean isSeen() { return seen; }

	void seen() { seen = true; }

	@Override
	public Iterator<Edge> iterator() { return new BVertexIterator(this); }

	class BVertexIterator implements Iterator<Edge> {
	    BEdge cur;
	    Iterator<BEdge> it;
	    boolean isReady;
	

	    BVertexIterator(BVertex u) {
		this.it = u.badj.iterator();
		isReady = false;
	    }

	    public boolean hasNext() {
	    	
	    	if(!isTightEdgeOnly)
	    	{
	    		return it.hasNext();
	    	}
		if(isReady) { return true; }
		if(!it.hasNext()) { return false; }
		cur = it.next();
		while(!cur.isTightEdge() && it.hasNext()) {
		    cur = it.next();
		}
		isReady = true;
		return cur.isTightEdge();
	    }

	    public Edge next() {
	    	
	    	if(!isTightEdgeOnly)
	    	{
	    		return it.next();
	    	}
	    	
		if(!isReady) {
		    if(!hasNext()) {
			throw new java.util.NoSuchElementException();
		    }
		}
		isReady = false;
		return cur;
	    }

	    public void remove() {
		throw new java.lang.UnsupportedOperationException();
	    }
	}
    }

    static class BEdge extends Edge {
	boolean isTightEdge;

	BEdge(BVertex from, BVertex to, int weight) {
	    super(from, to, weight);
	    isTightEdge = false;
	}

	boolean isTightEdge() {
		
		return isTightEdge ;
	    }
    }

    BVertex[] xv; // vertices of graph

    public BellManFordGraphOld(Graph g) {
	super(g);
	xv = new BVertex[g.size()];  // Extra space is allocated in array for nodes to be added later
        for(Vertex u: g) {
            xv[u.getName()] = new BVertex(u);
        }

	// Make copy of edges
	for(Vertex u: g) {
	    for(Edge e: u) {
		Vertex v = e.otherEnd(u);
		BVertex x1 = getVertex(u);
		BVertex x2 = getVertex(v);
		x1.badj.add(new BEdge(x1, x2, e.weight));
	    }
	}
    }

   

    @Override
    public Vertex getVertex(int n) {
        return xv[n-1];
    }

    BVertex getVertex(Vertex u) {
	return Vertex.getVertex(xv, u);
    }

    void seen(int i) {
	BVertex u = (BVertex) getVertex(i);
	u.seen();
    }
    
    boolean bellmanFord(Vertex s)
    {
    	Queue<BVertex> queue = new LinkedList<BellManFordGraphOld.BVertex>();
    	BVertex source = getVertex(s);
    	source.seen=true;
    	source.distance=0;
    	queue.offer(source);
    	
    	while(!queue.isEmpty())
    	{
    		BVertex u = queue.poll();
    		u.seen();
    		u.count++;
    		if(u.count>= xv.length-1){
    			return false;
    		}
    		
    		for(Edge e : u)
    		{
    			BEdge be = (BEdge)e;
    			BVertex v = getVertex(be.otherEnd(u));
    			if(v.distance>=u.distance + e.weight)
    			{
    				if(v.distance == u.distance+e.weight)
    				{
    					reassignTightEdge(be, v,true);
    				}
    				
					reassignTightEdge(be, v,false);

    				v.distance = u.distance+e.weight;
    				
    				if(!v.isSeen())
    				{
    					queue.offer(v);
    					v.seen();
    				}
    			}
    		}
    	}
    	
    	printTightEdges();
		return true;
    	
    	
    	
    }



	private void printTightEdges() {
 isTightEdgeOnly= true;
 
 for(Vertex v:this)
 {
	 BVertex bv = (BVertex)getVertex(v);
for(Edge e : bv)	 
{
	System.out.println(e);
}
 }
	}



	private void reassignTightEdge(BEdge be, BVertex v, boolean isAdd) {
		if(v.tightEdge.size()!=0)
		{
			if(isAdd)
			{
				v.tightEdge.add(be);
				be.isTightEdge=true;
			}
			else
			{
				for(Edge e : v.tightEdge)
				{
					((BEdge)e).isTightEdge=false;
				}
				v.tightEdge.clear();
				v.tightEdge.add(be);
			}
		}
		else
		{
		v.tightEdge.add(be);
		be.isTightEdge=true;
		}
	}
    
    public static void main(String[] args) {
        Graph g = Graph.readGraph(new Scanner(System.in));
	BellManFordGraphOld xg = new BellManFordGraphOld(g);
	Vertex src = xg.getVertex(1);

	
    }

    void printGraph(BFS b) {
	for(Vertex u: this) {
	    System.out.print("  " + u + "  :   " + b.distance(u) + "  : ");
	    for(Edge e: u) {
		System.out.print(e);
	    }
	    System.out.println();
	}

    }

}

