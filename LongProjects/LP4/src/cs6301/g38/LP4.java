
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
    private static final long Infinity = Long.MAX_VALUE;
	
     
    // common constructor for all parts of LP4: g is graph, s is source vertex
    public LP4(Graph g, Vertex s) {
	this.g = g;
	this.s = s;
    }
    
    public class CSPVertex extends Vertex{
    	long[] d;
    	Vertex parent;
    	long distance= Infinity;
    	boolean seen = false; //seen flag for each vertex- initally false  
    	boolean isInfinity = true; // initialize all vertices to be at distance of infinity 
		public CSPVertex(Vertex u) {
			super(u);
			d = new long[g.size()];	
			parent = null;
			d[0]=Infinity;			
		}    	
    }
     
    // Part a. Return number of topological orders of g
    public long countTopologicalOrders() {
    	TopologicalSort xg = new TopologicalSort(g);
		xg.countTopologicalSorts();
	return xg.getCountPath();
    }

    // Part b. Print all topological orders of g, one per line, and 
    //	return number of topological orders of g
    public long enumerateTopologicalOrders() {
    	TopologicalSort xg = new TopologicalSort(g);
		xg.enumerateTopologicalSorts(0);
        return xg.getEnumCountPath();
    }

    // Part c. Return the number of shortest paths from s to t
    // 	Return -1 if the graph has a negative or zero cycle
    public long countShortestPaths(Vertex t) {
    	  AllShortestPaths bg = new AllShortestPaths(g);
           
	return  bg.countAllSPs(s, t);
    }
    
    // Part d. Print all shortest paths from s to t, one per line, and 
    //	return number of shortest paths from s to t.
    //	Return -1 if the graph has a negative or zero cycle.
    public long enumerateShortestPaths(Vertex t) {
    	AllShortestPaths bg = new AllShortestPaths(g);
        
    	return  bg.enumerateAllSPs(s, t);
    }


    // Part e. Return weight of shortest path from s to t using at most k edges
    public long constrainedShortestPath(Vertex t, int k) {
    	CSPVertex[] cspVertex = new CSPVertex[g.size()];
    	boolean nochange;
    	boolean isReached=false; // flag to check whether the target vertex is reached 
    
    	// adding additional properties of each vertex
    	for(Vertex u: g.vertex){
    		cspVertex[u.getName()] = new CSPVertex(u);  		
    	}

    	//intializing the properties for source vertex
    	cspVertex[s.name].d[0] = 0; 
    	cspVertex[s.name].seen = true;
    	cspVertex[s.name].isInfinity = false;
    	cspVertex[s.name].distance=0;
    	
    	for(int i=1; i<=k ; i++){
    		nochange = true;
    		for(CSPVertex u : cspVertex){
    			u.d[i] = u.d[i-1];
    			for(Edge e : u.revAdj){
    				CSPVertex p = cspVertex[e.otherEnd(u).name];
      			    if(p.isInfinity && !u.seen){ // if we reach the vertex for the first time and its parent was not reached before
      			    	u.d[i] = Infinity;
      			    	continue;
      			    }else if(p.isInfinity){ // if the vertex's current parent was not reached, but vertex was already reached through other parent before
      			       	continue;
      			    } else{ // if we reached the vertex for the first time and its parent was reached before
	      				if(u.d[i] > p.d[i-1] + e.weight ){ //relax condition
	    					u.d[i] = p.d[i-1] + e.weight; 
	    					u.parent = p;
	    					nochange = false;
	    					u.seen = true;
	    					if(u.equals(t)){ //check if the current vertex is the target
	    						isReached=true;
	    					}	    				
	    				}
	    			}
      			 }
    		}
    		for(CSPVertex v : cspVertex){
    			if(v.seen){
    				v.isInfinity = false; //for all the vertices reached in current iteration, update the distance flag
    			}    			
    		}
    		if(nochange){
    			for(CSPVertex u : cspVertex){
    				u.distance = u.d[i];
    			}
    			return cspVertex[t.name].d[i];
       		}
    	}
       	if(isReached){
    		return cspVertex[t.name].d[k]; //if target was reached, return its distance, else return infinity
    	}
       	return Infinity;
    }

    // Part f. Reward collection problem
    // Reward for vertices is passed as a parameter in a hash map
    // tour is empty list passed as a parameter, for output tour
    // Return total reward for tour
    public int reward(HashMap<Vertex,Integer> vertexRewardMap, List<Vertex> tour) {
    	RewardCollection r = new RewardCollection(g, s, vertexRewardMap);
   int reward= 	r.findMaxRewardPath();
    	
    	if(reward>0)
    	{
    		r.copyRewardPath(tour);
    	}
    	
    	
        return reward;
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
