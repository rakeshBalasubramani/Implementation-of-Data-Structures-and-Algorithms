
// Starter code for LP4
// Do not rename this file or move it away from cs6301/g??

// change following line to your group number
package cs6301.g38;
import cs6301.g38.Graph.Vertex;
import cs6301.g38.Graph.Edge;

import java.util.List;
import java.util.HashMap;

public class LP4 {
    Graph g;
    Vertex s;
    static final int Infinity = Integer.MAX_VALUE;
	
     
    // common constructor for all parts of LP4: g is graph, s is source vertex
    public LP4(Graph g, Vertex s) {
	this.g = g;
	this.s = s;
    }
    
    public class CSPVertex extends Vertex{

    	int[] d;
    	Vertex parent;
    	int distance;
		public CSPVertex(Vertex u) {
			super(u);
			d = new int[g.edgeSize()];	
			parent = null;
			distance = 0;
			
		}
    	
    }
    
    
    
 
    // Part a. Return number of topological orders of g
    public long countTopologicalOrders() {
	// To do
	return 0;
    }


    // Part b. Print all topological orders of g, one per line, and 
    //	return number of topological orders of g
    public long enumerateTopologicalOrders() {
	// To do
        return 0;
    }


    // Part c. Return the number of shortest paths from s to t
    // 	Return -1 if the graph has a negative or zero cycle
    public long countShortestPaths(Vertex t) {
	// To do
    	
	return 0;
    }

    
    // Part d. Print all shortest paths from s to t, one per line, and 
    //	return number of shortest paths from s to t.
    //	Return -1 if the graph has a negative or zero cycle.
    public long enumerateShortestPaths(Vertex t) {
        // To do
        return 0;
    }


    // Part e. Return weight of shortest path from s to t using at most k edges
    public int constrainedShortestPath(Vertex t, int k) {
    	// To do
    	CSPVertex[] cspVertex = new CSPVertex[g.size()];
    	boolean nochange;
    	
    	// adding additional properties of each vertex
    	for(Vertex u: g.vertex){
    		cspVertex[u.getName()] = new CSPVertex(u);  		
    	}
    	// initialize d[0] and parent for all vertices
    	for(CSPVertex u : cspVertex){
    		u.d[0] = Infinity;
    		u.parent = null;    			
    	}
    	cspVertex[s.name].d[0] = 0; //set distance of source to be zero
    	for(int i=1; i<=k ; i++){
    		nochange = true;
    		for(Vertex v : g.vertex){
    			CSPVertex u = cspVertex[v.getName()];
    			u.d[i] = u.d[i-1];
    			//int min = 0;
				for(Edge e : u.revAdj){
    				CSPVertex p = cspVertex[e.otherEnd(u).name];
    				System.out.println("\nparent of  "+ u.name + " is "+ p.name);
      				System.out.println("u.d[i] "+ u.d[i] + " >  " + p.d[i-1] +" weight " +  e.weight);
      			//	if(p.d[i-1] == Infinity) break;
      				//u.d[i] = Infinity;
    				if(u.d[i] > p.d[i-1] + e.weight ){
    					u.d[i] = p.d[i-1] + e.weight;
    					//if(u.d[i] < 0) u.d[i] = Infinity;
    	    			
    					//min = u.d[i];
    					u.parent = p;
    					System.out.println("\t updated parent of  "+ u.name + " is "+ p.name);
    					nochange = false;
    				}
    				System.out.println("\ti value " + i + " u- " + u.name + " distance " + u.d[i]);					
    			}
    		}
    		if(nochange){
    			for(CSPVertex u : cspVertex){
    				u.distance = u.d[i];
    				System.out.println("i value " + i + " u- " + u.name + " distance " + u.distance);
    			}
    			System.out.println("Distance to target " + cspVertex[t.name].d[k]);
    			return cspVertex[t.name].d[i];
       		}
    		System.out.println("Distance to target " + cspVertex[t.name].d[k]);			
   	   	}
       	return 0;
    }


    // Part f. Reward collection problem
    // Reward for vertices is passed as a parameter in a hash map
    // tour is empty list passed as a parameter, for output tour
    // Return total reward for tour
    public int reward(HashMap<Vertex,Integer> vertexRewardMap, List<Vertex> tour) {
	// To do
        return 0;
    }

    // Do not modify this function
    static void printGraph(Graph g, HashMap<Vertex,Integer> map, Vertex s, Vertex t, int limit) {
	System.out.println("Input graph:");
	for(Vertex u: g) {
	    if(map != null) { 
		System.out.print(u + "($" + map.get(u) + ")\t: ");
	    } else {
		System.out.print(u + "\t: ");
	    }
	    for(Edge e: u) {
		System.out.print(e + "[" + e.weight + "] ");
	    }
	    System.out.println();
	}
	if(s != null) { System.out.println("Source: " + s); }
	if(t != null) { System.out.println("Target: " + t); }
	if(limit > 0) { System.out.println("Limit: " + limit + " edges"); }
	System.out.println("___________________________________");
    }
}
