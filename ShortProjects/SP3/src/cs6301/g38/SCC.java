package cs6301.g38;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import cs6301.g38.CC.CCVertex;
import cs6301.g38.Graph.Vertex;

// code to find the strongly connected component 

public class SCC {

	 // Class to store information about a vertex in this algorithm
    class CCVertex {
	Graph.Vertex element;
	boolean seen;
	int cno;
	CCVertex(Graph.Vertex u) {
	    element = u;
	    seen = false;
	    cno = -1;
	}
    }
    // Algorithm uses a parallel array for storing information about vertices
    CCVertex[] ccVertex;
    Graph g;
    static List<Graph.Vertex> dfsFinishOrder = new ArrayList<Graph.Vertex>();
    static List<Graph.Vertex> dfsFinishOrder1 = new ArrayList<Graph.Vertex>();
    
    

    public SCC(Graph g) {
	this.g = g;
	ccVertex = new CCVertex[g.size()];
	for(Graph.Vertex u: g) { ccVertex[u.name] = new CCVertex(u); }
    }

	void runDFS() {
		 // Main algorithm for finding the number of connected components of g using DFS
		int cno = 0;
		for(Graph.Vertex u: g) {
		    if(!seen(u)) {
			cno++;
			dfsVisit(u, cno);
		    }
		}
		//return cno;
	}
	
	
    void dfsVisit(Graph.Vertex u, int cno) {
		visit(u, cno);
		//System.out.println("vistied "+ u);
		
		for(Graph.Edge e: u.adj) {
			
		    Graph.Vertex v = e.otherEnd(u);
		    System.out.println("Visited " + u +" Other end "+ v);
		    if(!seen(v)) {
			dfsVisit(v, cno);
			}
		}
		dfsFinishOrder.add(u);
    }

    void dfsVisit1(Graph.Vertex u, int cno1) {
		visit(u, cno1);
		//System.out.println("vistied "+ u);
		
		for(Graph.Edge e: u.revAdj) {
			
		    Graph.Vertex v = e.otherEnd(u);
		    System.out.println("Visited " + u +" Other end "+ v);
		    if(!seen(v)) {
			dfsVisit1(v, cno1);
			}
		}
		dfsFinishOrder1.add(u);
		return;
    }
    
    
    boolean seen(Graph.Vertex u) {
	CCVertex ccu = getCCVertex(u);
	return ccu.seen;
    }

    // Visit a node by marking it as seen and assigning it a component no
    void visit(Graph.Vertex u, int cno) {
	CCVertex ccu = getCCVertex(u);
	ccu.seen = true;
	ccu.cno = cno;
    }
	
    //unvisit a node 
    void unvisit(Graph.Vertex u) {
	CCVertex ccu = getCCVertex(u);
	ccu.seen = false;

    }
    // From Vertex to CCVertex (ugly)
    CCVertex getCCVertex(Graph.Vertex u) {
	return ccVertex[u.name];
    }

    // From CCVertex to Vertex
    Graph.Vertex getVertex(CCVertex c) {
	return c.element;
    }

	
	public void print(){
		System.out.println(dfsFinishOrder);
	}
	
	public void reverse(){
		
		int cno1 = 0;
		for(Graph.Vertex v : this.g){
			unvisit(v);
		}
			
		Iterator<Graph.Vertex> it = dfsFinishOrder.iterator();
		while(it.hasNext()){
			// CCVertex cc1 = new CCVertex(it.next());
			Graph.Vertex vertex = it.next();
			
			System.out.println("Vertex" + vertex);
			if(!seen(vertex)){
				cno1++;
				dfsVisit1(vertex, cno1);				
			}
		}
		System.out.println("No of strongly connected components " + cno1);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readDirectedGraph(in);
        SCC cc = new SCC(g); 
        cc.runDFS();
        System.out.println("Finish time order");
        cc.print();

        System.out.println(" Printing reversed graph");
        cc.reverse();
        
        //System.out.println("Input Graph has " + nc + " components:");
    	System.out.println(" order "+ dfsFinishOrder1);
	}

}

/*

sample input --- this is  the graph he showed 
11 16
1 11 1 
11 4 1 
11 6 1
11 3 1
9 11 1
4 9 1
4 1 1
5 4 1 
5 8 1
5 7 1
2 7 1
8 2 1
2 3 1
3 10 1
6 3 1
10 6 1
*/