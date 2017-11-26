package cs6301.g38;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;




public class DinitzGraph extends Graph {
    public static class FVertex extends Vertex {
	List<FEdge> adjEdge,residualEdge;

	FVertex(Vertex u) {
	    super(u);
	    adjEdge = new LinkedList<>();
	    residualEdge = new LinkedList<>();
	}

	
	@Override
	public Iterator<Edge> iterator() { return new XVertexIterator(this); }

	class XVertexIterator implements Iterator<Edge> {
	    FEdge cur;
	    Iterator<FEdge> it;
	    boolean ready;

	    XVertexIterator(FVertex u) {
		this.it = u.adjEdge.iterator();
		ready = false;
	    }

	    public boolean hasNext() {
		if(ready) { return true; }
		if(!it.hasNext()) { return false; }
		cur = it.next();
		while(!cur.isFeasibleFlow() && it.hasNext()) {
		    cur = it.next();
		}
		ready = true;
		return cur.isFeasibleFlow();
	    }

	    public Edge next() {
		if(!ready) {
		    if(!hasNext()) {
			throw new java.util.NoSuchElementException();
		    }
		}
		ready = false;
		return cur;
	    }

	    public void remove() {
		throw new java.lang.UnsupportedOperationException();
	    }
	}
    }

    static class FEdge extends Edge {
    int capacity=0;
    int flow=0;
    
    
	FEdge(FVertex from, FVertex to, int weight, int capacity, int name) {
	    super(from, to, weight,name);
	    this.capacity = capacity;
	}

	boolean isFeasibleFlow() {
		//FVertex xfrom = (FVertex) from;
	//	FVertex xto = (FVertex) to;
		return (flow < capacity) ;
	    }
    }

    FVertex[] fv; // vertices of graph
	private Vertex src;
	private Vertex terminal;
	 int maxFlow;
	private BFS bfsHelper;
	private FEdge[] reverseEdge;
    public DinitzGraph(Graph g,Vertex source, Vertex terminal, HashMap<Edge, Integer> capacity) {
	super(g);
	this.src = source;
	this.terminal = terminal;
	fv = new FVertex[g.size()];  // Extra space is allocated in array for nodes to be added later
	reverseEdge = new FEdge[this.edgeSize()+1];
        for(Vertex u: g) {
            fv[u.getName()] = new FVertex(u);
        }

	// Make copy of edges 
	for(Vertex u: g) {
	    for(Edge e: u) {
		Vertex v = e.otherEnd(u);
		FVertex x1 = getVertex(u);
		FVertex x2 = getVertex(v);
		x1.adjEdge.add(new FEdge(x1, x2, e.weight, capacity.get(e),e.name));
		FEdge revE = new FEdge(x2, x1, e.weight,capacity.get(e),e.name);
		x2.residualEdge.add(revE);
		reverseEdge[e.name]= revE;

	    }
	}
	
	for(Vertex u: g) {		
		u.adj.addAll(u.revAdj);
	}
	bfsHelper = new BFS(this,this.src);

    }

    @Override
    public Iterator<Vertex> iterator() { return super.iterator();}
    		//new DinitzGraphIterator(this); }

    class DinitzGraphIterator implements Iterator<Vertex> {
	Iterator<FVertex> it;
	FVertex fcur;
	
	DinitzGraphIterator(DinitzGraph xg) {
	    this.it = new ArrayIterator<FVertex>(xg.fv, 0, xg.size()-1);  // Iterate over existing elements only
	}
	

	public boolean hasNext() {
	    if(!it.hasNext()) { return false; }
	    fcur = it.next();
	    while(it.hasNext()) {
		fcur = it.next();
	    }
	    return !it.hasNext();
	}

	public Vertex next() {
	    return fcur;
	}

	public void remove() {
	}
	    
    }


    @Override
    public Vertex getVertex(int n) {
        return fv[n-1];
    }

    FVertex getVertex(Vertex u) {
	return Vertex.getVertex(fv, u);
    }

    void disable(int i) {
	FVertex u = (FVertex) getVertex(i);
	//u.visit();
    }
    
    
  private int sendFlow(Vertex vertex, int flow)
    {
        // Sink reached
        if (vertex.equals(terminal))
            return flow;
     
        // Traverse all adjacent edges one -by - one.
        for (Edge e : vertex)
        {
           Vertex otherVertex =  e.otherEnd(vertex);
            FEdge fe = (FEdge) e  ;                          
            if (bfsHelper.distance(otherVertex) == (bfsHelper.distance(vertex)+1))
            {
                // find minimum flow from u to t
                int curr_flow = findMin(flow, fe.capacity - fe.flow);
     
                int temp_flow = sendFlow(getVertex(otherVertex), curr_flow);
     
                // flow is greater than zero
                if (temp_flow > 0)
                {
                    // add flow  to current edge
                    fe.flow += temp_flow;
     
                    // subtract flow from reverse edge
                    // of current edge
                    reverseEdge[e.name].flow =fe.capacity- temp_flow;
                    return temp_flow;
                }
            }
        }
     
        return 0;
    }
    
    private int findMin(int flow, int i) {
    	return (Integer.compare(flow, i)>0)?i:flow;
    	}

	public void dinitz()
    {
		int flow;
    	bfsHelper.bfs();
    	while(bfsHelper.distance(terminal)!=BFS.INFINITY)
    	{
    		flow = sendFlow(getVertex(src), BFS.INFINITY);
    		while (flow>0)
    		{
    			 
                // Add path flow to overall flow
                this.maxFlow += flow;
        		flow = sendFlow(getVertex(src), BFS.INFINITY);

    		}
    		bfsHelper.reinitialize(src);
        	bfsHelper.bfs();

    	}
    }
    
 
    public static void main(String[] args) {
        Graph g = Graph.readDirectedGraph(new Scanner(System.in));

	
    }

}