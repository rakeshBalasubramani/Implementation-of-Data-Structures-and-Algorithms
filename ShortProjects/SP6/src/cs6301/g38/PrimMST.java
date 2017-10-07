
/* Ver 1.0: Starter code for Prim's MST algorithm */

package cs6301.g38;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;



import java.io.FileNotFoundException;
import java.io.File;

public class PrimMST {
    static final int Infinity = Integer.MAX_VALUE;
    
    class PrimVertex implements Comparator<PrimVertex>, Index {
    	int d, index;
		public boolean seen;
		Graph.Vertex vertex;
		Graph.Vertex parent;
		
		public PrimVertex(Graph.Vertex u){
			parent = null;
			vertex = u;
			d = Infinity;
			seen = false;			
		}
    	public int compare( PrimVertex u, PrimVertex v ) {
    		if ( u.d < v.d ){
    			return -1;
    		} else if ( u.d == v.d ){
    			return 0;
    		}else{
    			return 1; 
    		}
    	}

    	public void putIndex( int i ) {
    		index = i;
    	}
    	public int getIndex() {
    		return index;
    	}
    	
    	public String toString()
    	{
			return vertex.name + " " + "distance" + " " + d + " index " + index + "\n";
    		
    	}
    } 

	private PrimVertex[] primVertex;
    public PrimMST(Graph g) {
    	primVertex = new PrimVertex[g.size()];
    	for(Graph.Vertex u : g){
       		primVertex[u.name] = new PrimVertex(u); 
       		primVertex[u.name].index = u.name;
    	}
    	
    	
    }
	
   public int prim1(Graph.Vertex s) {
        int wmst = 0;

        // SP6.Q4: Prim's algorithm using PriorityQueue<Edge>:

        return wmst;
    }

    public int prim2(Graph.Vertex s) {
        int wmst = 0;
        
        PrimVertex src = primVertex[s.name];
        src.d=0;
        
        // SP6.Q6: Prim's algorithm using IndexedHeap<PrimVertex>:
     

        
        IndexedHeap<PrimVertex> indexedHeap = new IndexedHeap<PrimVertex>(Arrays.copyOf(primVertex, primVertex.length) , primVertex[0] , primVertex.length);
        indexedHeap.buildHeap();
        indexedHeap.print();
        
        while(indexedHeap.peek() != null){
        	PrimVertex u = indexedHeap.remove();
        	u.seen = true;
           	wmst = wmst + u.d;
            indexedHeap.print();
        	for(Graph.Edge ee : u.vertex){
        		Graph.Vertex v = ee.otherEnd(u.vertex);
        		if(!primVertex[v.name].seen && ee.weight < primVertex[v.name].d){
        			primVertex[v.name].d = ee.weight;
        			primVertex[v.name].parent = u.vertex;
        			indexedHeap.percolateUp(primVertex[v.name].getIndex());
        	        indexedHeap.print();

        		}
        		
        	}
        }
        return wmst;
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

	Graph g = Graph.readGraph(in);
        Graph.Vertex s = g.getVertex(1);
        
   	Timer timer = new Timer();
	PrimMST mst = new PrimMST(g);
	int wmst = mst.prim2(s);
	timer.end();
        System.out.println(wmst);
    }
}
