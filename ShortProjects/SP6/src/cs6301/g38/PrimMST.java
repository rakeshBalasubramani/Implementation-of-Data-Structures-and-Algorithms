
/* Ver 1.0: Starter code for Prim's MST algorithm */

package cs6301.g38;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Desc Class used to implement Prim's Algorithm
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import cs6301.g38.Graph.Edge;
import java.io.File;

public class PrimMST {
	static final int Infinity = Integer.MAX_VALUE;

	/**
	 * @Desc Class used to store the properties for Prim Vertex
	 *
	 */
	class PrimVertex implements Comparator<PrimVertex>, Index {
		int d, index;
		public boolean seen;
		Graph.Vertex vertex;
		Graph.Vertex parent;

		public PrimVertex(Graph.Vertex u) {
			parent = null;
			vertex = u;
			d = Infinity;
			seen = false;
		}
		
		public void resetEntries()	{
			parent = null;
			d = Infinity;
			seen = false;
		}

		public int compare(PrimVertex u, PrimVertex v) {
			if (u.d < v.d) {
				return -1;
			} else if (u.d == v.d) {
				return 0;
			} else {
				return 1;
			}
		}

		public void putIndex(int i) {
			index = i;
		}

		public int getIndex() {
			return index;
		}

		public String toString() {
			return vertex.name + " " + "distance" + " " + d + " index " + index + "\n";

		}
	}
	/**
	 * @Desc Class used to store the properties for Prim Edge
	 *
	 */
	class PrimEdge implements Comparator<PrimEdge>{
		Edge edge;
		public int compare(PrimEdge e1, PrimEdge e2) {
			if (e1.edge.weight < e2.edge.weight) {
				return -1;
			} else if (e1.edge.weight == e2.edge.weight) {
				return 0;
			} else {
				return 1;
			}
		}
		private PrimEdge(Edge e) {
			edge=e;
		}
		
		public String toString()
		{
			return edge.stringWithSpaces();
		}
	}
	private PrimVertex[] primVertex;

	/**
	 * Constructor of PrimMST
	 * @param g - input graph
	 */
	public PrimMST(Graph g) {
		primVertex = new PrimVertex[g.size()];
		for (Graph.Vertex u : g) {
			primVertex[u.name] = new PrimVertex(u);
			primVertex[u.name].index = u.name;
		}

	}
	
	/** 
	 * Method to reset the properties of PrimVertex
	 */
	private void resetPrimVertex() {
		if(primVertex!=null)
		{
			for(PrimVertex pv: primVertex){
				pv.resetEntries();
			}
		}
	}

	/**
	 * Method implementing Prim1
	 * @param s - source vertex of the graph
	 * @return - returns the calculated wmst
	 */
	public int prim1(Graph.Vertex s) throws Exception {
		int wmst = 0;
		// SP6.Q4: Prim's algorithm using PriorityQueue<Edge>:
		PrimVertex src = primVertex[s.name];
		PrimEdge[] pE = new PrimEdge[primVertex.length*(primVertex.length-1)];
		int i=0;
		for(Edge e:src.vertex) {
			pE[i++]=new PrimEdge(e);
		}
		src.seen=true;
		BinaryHeap<PrimEdge> binaryHeap=new BinaryHeap<PrimEdge>(Arrays.copyOf(pE, pE.length),pE[0],i);
		while (binaryHeap.peek() != null) {
			PrimEdge e = binaryHeap.remove();
			PrimVertex pV;
			PrimVertex pV1 = primVertex[e.edge.otherEnd(e.edge.from).name];
			PrimVertex pV2 = primVertex[e.edge.otherEnd(e.edge.to).name];
			if(pV1.seen&&pV2.seen) {
				continue;
			}
			else if(pV1.seen) {
				pV=pV2;
			}
			else
			{
				pV=pV1;
			}
			pV.seen=true;
			pV.parent=e.edge.from;
			wmst=wmst+e.edge.weight;
			for(Edge e2:pV.vertex) {
				if(!primVertex[e2.otherEnd(pV.vertex).name].seen) {
					binaryHeap.add(new PrimEdge(e2));
				}
			}
			
		}
		resetPrimVertex();

		return wmst;
	}

	/**
	 * Method implementing Prim2
	 * @param s - source vertex of the graph
	 * @return - returns the calculated wmst
	 */
	public int prim2(Graph.Vertex s) {
		// SP6.Q6: Prim's algorithm using IndexedHeap<PrimVertex>:

		int wmst = 0;
		PrimVertex src = primVertex[s.name];
		src.d = 0;
		
		//build IndexedHeap using the Prim vertices of the graph
		IndexedHeap<PrimVertex> indexedHeap = new IndexedHeap<PrimVertex>(Arrays.copyOf(primVertex, primVertex.length),
				primVertex[0], primVertex.length);
		
		while (indexedHeap.peek() != null) {
			PrimVertex u = indexedHeap.remove();
			u.seen = true;
			wmst = wmst + u.d;
			for (Graph.Edge ee : u.vertex) {
				Graph.Vertex v = ee.otherEnd(u.vertex);
				if (!primVertex[v.name].seen && ee.weight < primVertex[v.name].d) {
					primVertex[v.name].d = ee.weight;
					primVertex[v.name].parent = u.vertex;
					indexedHeap.percolateUp(primVertex[v.name].getIndex());
				}

			}
		}
		resetPrimVertex();
		return wmst;
	}



	public static void main(String[] args) throws Exception {
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
		
		// Calcualtion of wmst using Prim1
		PrimMST mst = new PrimMST(g);
		int wmst = mst.prim1(s);
		timer.end();
		System.out.println(wmst);
		System.out.println(timer);

		// Calculation of wmst using Prim2
		timer.start();
		 wmst = mst.prim2(s);
		timer.end();
		System.out.println(wmst);
		System.out.println(timer);

	}
}
