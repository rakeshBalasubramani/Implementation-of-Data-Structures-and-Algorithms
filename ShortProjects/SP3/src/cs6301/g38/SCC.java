package cs6301.g38;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import cs6301.g38.Graph;
import cs6301.g38.Graph.Vertex;

/**
 * @author Avinash Venkatesh - axv165330 <br>
 *         HariPriyaa - hum160030 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         Raj Kumar Panneer Selvam - rxp162130
 *
 * @description This class is used to find the number of strongly connected
 *              components in the given input graph
 */
public class SCC {

	// Class to store information about a vertex in this algorithm
	public static class CCVertex {
		Graph.Vertex element;
		boolean seen;//
		int cno;

		CCVertex(Graph.Vertex u) {
			element = u;
			seen = false;// Whether the given vertex seen during DFS.
			cno = -1; // Component number for the vertex.
		}
	}

	private CCVertex[] ccVertex;// Algorithm uses a parallel array for storing
								// information about vertices
	private Graph graph; // input graph
	private static List<Graph.Vertex> dfsFinishOrder = new ArrayList<Graph.Vertex>(); // DFS
																						// Finishing
																						// Order
																						// for
																						// graph
																						// G.
	private static List<Graph.Vertex> dfsFinishOrder1 = new ArrayList<Graph.Vertex>(); // DFS
																						// Finishing
																						// Order
																						// for
																						// Transpose
																						// graph
																						// of
																						// G.

	public SCC(Graph g) {
		this.graph = g;
		ccVertex = new CCVertex[g.size()];
		for (Graph.Vertex u : g) {
			ccVertex[u.getName()] = new CCVertex(u);
		}
	}

	/**
	 * To run DFS to find the finish time order.
	 */
	public void runDFS() {
		int cno = 0;
		for (Graph.Vertex u : graph) {
			if (!seen(u)) {
				cno++;
				dfsVisit(u, cno);
			}
		}
	}

	/**
	 * Recursive DFS implementation.
	 * 
	 * @param u
	 *            - vertex whose depth is to be explored
	 * @param cno
	 *            - component number
	 */
	private void dfsVisit(Graph.Vertex u, int cno) {
		visit(u, cno);
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v)) {
				dfsVisit(v, cno);
			}
		}
		dfsFinishOrder.add(u);
	}

	/**
	 * Method to run DFS after reversing graph edges
	 * 
	 * @param u - vertex whose depth is to be explored 
	 * @param cno1 - component number
	 */
	private void dfsVisitAfterEdgeReverse(Graph.Vertex u, int cno1) {
		visit(u, cno1);
		for (Graph.Edge e : u.revAdj) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v)) {
				dfsVisitAfterEdgeReverse(v, cno1);
			}
		}
		dfsFinishOrder1.add(u);
		return;
	}

	
	/**To check whether the vertex is already visited
	 * @param u - Vertex to be check.
	 * @return
	 */
	private boolean seen(Graph.Vertex u) {
		CCVertex ccu = getCCVertex(u);
		return ccu.seen;
	}

	
	/**To Visit a node by marking it as seen and assigning it a component number.
	 * @param u - Given vertex.
	 * @param cno - Component number.
	 */
	private void visit(Graph.Vertex u, int cno) {
		CCVertex ccu = getCCVertex(u);
		ccu.seen = true;
		ccu.cno = cno;
	}

	
	/** To unvisit a node.
	 * @param u - Vertex
	 */
	private void unvisit(Graph.Vertex u) {
		CCVertex ccu = getCCVertex(u);
		ccu.seen = false;

	}

	
	/**From Vertex to CCVertex (ugly)
	 * @param u - given vertex.
	 * @return
	 */
	public CCVertex getCCVertex(Graph.Vertex u) {
		return ccVertex[u.getName()];
	}

	
	/** method to run DFS for Graph G`
	 * @return - Number of Strongly Connected Components for the given graph.
	 */
	private int dfsForTransposeGraph() {
		int cno1 = 0;
		// unvisit every edge which was visited during the previous DFS run
		for (Graph.Vertex v : this.graph) {
			unvisit(v);
		}

		ListIterator<Vertex> it = dfsFinishOrder.listIterator(dfsFinishOrder
				.size());

		// run DFS in the reverse order of finish time order of previous DFS run
		while (it.hasPrevious()) {
			// CCVertex cc1 = new CCVertex(it.next());
			Graph.Vertex vertex = it.previous();
			if (!seen(vertex)) {
				cno1++;
				dfsVisitAfterEdgeReverse(vertex, cno1);
			}
		}

		return cno1;
	}


	 /** Method to find the number of Strongly Connected Components (SCC) in the given Graph.
	 * @param g - input graph
	 * @return - Number of SCC for the given graph.
	 */
	public static int stronglyConnectedComponents(Graph g) { 
		   SCC cc = new SCC(g);
			cc.runDFS();
			// System.out.println("Finish time order");
			// System.out.println(dfsFinishOrder);
			for (Graph.Vertex u : g) {
				System.out.print("Vertex: " + u + " Component no: "  + + cc.getCCVertex(u).cno );
				System.out.println();
			}
			System.out.println();
			return cc.dfsForTransposeGraph();
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
		
		// System.out.println("Finish time order after reversing graph "+
		// dfsFinishOrder1);
		
		System.out.println("Input graph has " + SCC.stronglyConnectedComponents(g)
				+ " strongly connected components");

		
	}
}

/*
 * 
 * sample input --- this is the graph he showed 11 17 1 11 1 11 4 1 11 6 1 11 3
 * 1 9 11 1 4 9 1 4 1 1 5 4 1 5 8 1 5 7 1 2 7 1 8 2 1 2 3 1 3 10 1 6 3 1 10 6 1
 * 7 8 1
 */