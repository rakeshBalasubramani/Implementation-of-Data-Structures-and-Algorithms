/** Sample driver program using the graph class
 *  Reads input graph, prints it out, and assigns each node a component number (cno).
 *  @author rbk
 *  Version 1.0: 2017/08/18
 */

package cs6301.g38;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

import cs6301.g38.DMSTGraph.DMSTEdge;
import cs6301.g38.DMSTGraph.DMSTVertex;
import cs6301.g38.Graph.Edge;
//import cs6301.g38.Graph.Vertex;

public class CC{
	
	int max=Integer.MAX_VALUE;
	
	 DMSTVertex dm[];
	// List<Edge> minEdgeComponent =new ArrayList<Edge>();
	// Edge minEdge;
	 //boolean setMin;
	 
    // Class to store information about a vertex in this algorithm
//    class CCVertex {
//	V element;
//	boolean seen;
//	int cno;
//	CCVertex(V u) {
//	    element = u;
//	    seen = false;
//	    cno = -1;
//	}
//    }
    // Algorithm uses a parallel array for storing information about vertices
 //   CCVertex[] ccVertex;
   // Graph g;

//    public CC(Graph g) {
//	this.g = g;
//	ccVertex = new CCVertex[g.size()];
//	for(Graph.Vertex u: g) { ccVertex[u.name] = new CCVertex(u); }
//    }

    // Main algorithm for finding the number of connected components of g using DFS
    int findCC(DMSTVertex[] dmst) {
    	dm=dmst;
	int cno = 0;
	
	for(DMSTVertex u: dm) {
		
		if(u==null)
		{
			break;
		}
		
	    if(!seen(u)) {
		cno++;
		//setMin=true;
		dfsVisit(u, cno);
	//	minEdgeComponent.add(minEdge);
	    }
	}

//	System.out.println("MST edges:");
//	for(Edge mst: minEdgeComponent)
//	{
//		if(mst==null)
//		{
//			continue;
//		}
//		mst.stringWithSpaces();
//	}
	return cno;
    }

    
    void dfsVisit(DMSTVertex u, int cno) {
	visit(u, cno);
	
	//findMinimumEdgeToDifferentComponent(u);
	
	DMSTGraph.findZeroEdge=true;

	for(Edge e: u) {
		DMSTEdge dmstEdge = (DMSTEdge) e;
		DMSTVertex v = (DMSTVertex) dmstEdge.otherEnd(u);
	    if(!seen(v)) {
		dfsVisit(v, cno);
	//	DMSTGraph.findZeroEdge=false;
		
//		findMinimumEdgeToDifferentComponent(u);
	    }
	}
	
	DMSTGraph.findZeroEdge=false;
	
    }

//    private void findMinimumEdgeToDifferentComponent(DMSTVertex u) {
//	
//    	DMSTGraph.isRevItr=true;
//    	
//    	for(Edge in: u)
//    	{
//    		if(setMin)
//    		{
//    			Edge e= new Edge(in.to,in.from,in.getWeight());
//    			minEdge=e;
//    			setMin=false;
//    		}
//    		
//    		DMSTVertex diffComponent=(DMSTVertex)in.otherEnd(u);
//    		
//    		if((u.getComponentNumber()!=diffComponent.getComponentNumber())&&(diffComponent.getComponentNumber()!=-1)&&(in.getWeight()<=minEdge.getWeight())){
//    			
//    			Edge ee= new Edge(in.to,in.from,in.getWeight());
//    			minEdge=ee;
//    		}			
//    	}
//    	
//    	DMSTGraph.isRevItr=false;
//    	
//		
//	}


	boolean seen(DMSTVertex u) {
    //DMSTVertex ccu = getDMSTVertex(u);
	return u.seen;
    }

    // Visit a node by marking it as seen and assigning it a component no
    void visit(DMSTVertex u, int cno) {
	//CCVertex ccu = getCCVertex(u);
	u.seen = true;
	u.cno = cno;
    }

    
    
    
    // From Vertex to CCVertex (ugly)
//    V getCCVertex(V u) {
//	return dm[u.name];
//    }

    // From CCVertex to Vertex
//    Graph.Vertex getVertex(CCVertex c) {
//	return c.element;
//    }

//    public static void main(String[] args) throws FileNotFoundException {
//	//int evens = 0;
//	Scanner in;
//        if (args.length > 0) {
//            File inputFile = new File(args[0]);
//            in = new Scanner(inputFile);
//        } else {
//            in = new Scanner(System.in);
//        }
//	Graph g = Graph.readGraph(in);
//	CC cc = new CC(g);
//	int nc = cc.findCC();
//
//	System.out.println("Input Graph has " + nc + " components:");
//	for(Graph.Vertex u: g) {
//	    System.out.print(u + " [ " + cc.getCCVertex(u).cno + " ] :");
//	    for(Graph.Edge e: u.adj) {
//	//	Graph.Vertex v = e.otherEnd(u);
//		System.out.print(e + " ");
//	    }
//	    System.out.println();
//	}
//    }
}

/******************************
$ java cs6301.g00.CC
7 6
1 3 1
4 3 1
4 1 1
2 6 1
6 7 1
7 2 1

Output:
Input Graph has 3 components:
1 [ 1 ] :(1,3) (4,1) 
2 [ 2 ] :(2,6) (7,2) 
3 [ 1 ] :(1,3) (4,3) 
4 [ 1 ] :(4,3) (4,1) 
5 [ 3 ] :
6 [ 2 ] :(2,6) (6,7) 
7 [ 2 ] :(6,7) (7,2)

*/
