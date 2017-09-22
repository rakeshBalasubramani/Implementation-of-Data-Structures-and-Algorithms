
package cs6301.g38;

import java.util.List;

import cs6301.g38.Graph.Edge;

import java.util.Iterator;
import java.util.LinkedList;

public class Euler {
    int VERBOSE;
    List<Graph.Edge> tour;
    EulerVertex start; // start vertex
    EulerVertex[] ev;
    
    private static class EulerVertex
    {
    	int name;
    	List<EulerEdge> eAdj,eRevAdj;
    	
    	EulerVertex(Graph.Vertex v){
    		name = v.getName();
    	    eAdj = new LinkedList<EulerEdge>();
    	    eRevAdj = new LinkedList<EulerEdge>();
    	}
    }
    
    
    private static class EulerEdge
    {
    	Graph.Edge edge;
    	boolean explored;
    	
    	EulerEdge(Graph.Edge e)
    	{
    		edge=e;
    		explored=false;
    	}

    }
   
    
    // Constructor
    Euler(Graph g, Graph.Vertex start) {
	VERBOSE = 1;
	tour = new LinkedList<>();
	this.start=start;
	
    }

    // To do: function to find an Euler tour
    public List<Graph.Edge> findEulerTour() {
	findTours();
	
	if(VERBOSE > 9) { printTours(); }
	stitchTours();
	return tour;
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
   public boolean isEulerian() {
	System.out.println("Graph is not Eulerian");
	System.out.println("Reason: Graph is not strongly connected");
	return false;
    }

    EulerVertex u=start;
    static List<Graph.Vertex> node;
    
    private void findTours() {
    	
	   for(EulerEdge e: u.eAdj) // Edge (with explored att)
	   {
		   if(!e.explored)
		   {
			   tour.add(e);
			   u=e.edge.otherEnd(u);
		   }
	   }
    }   	   
    /* Print tours found by findTours() using following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     *
     * Just use System.out.print(u) and System.out.print(e)
     */
    void printTours() {
    	
    	
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    private void stitchTours() {
    	explore(start);
    }

    private void explore(EulerVertex v) {
		
    	EulerVertex tmp=v;
    	// findTours();
    	
    	for(Graph.Edge edgesInTour:tour)
    	{
    		tour.add(edgesInTour);
    		tmp=edgesInTour.otherEnd(tmp);
    		//check for unexplored tours from tmp
    	}
    	
		
	}

	void setVerbose(int v) {
	VERBOSE = v;
    }
}
