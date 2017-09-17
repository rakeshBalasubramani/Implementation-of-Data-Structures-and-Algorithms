package cs6301.g38;

import java.util.Scanner;
import cs6301.g38.Graph.Vertex;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Class used to check whether given graph is a Directed Acyclic
 *              Graph.
 */
public class DirectedAcyclicGraph {

	/**
	 * @Description Class used to store vertex information.
	 */
	private static class DFSVertex {
		Graph.Vertex element;
		boolean seen;

		/**
		 * Constructor to initialize DFSVertex properties.
		 * 
		 * @param u
		 *            - Vertex of the graph.
		 */
		DFSVertex(Graph.Vertex u) {
			element = u;
			seen = false;
		}
	}

	private DFSVertex[] dfsVertex;// To store DFSVertex.
	private boolean[] ancestorVertex; // To store information about ancestors whether they are in the current recursion stack.

	/**
	 * Constructor to initialize the DirectedAcyclicGraph properties.
	 * 
	 * @param g
	 *            - Input Graph.
	 */
	public DirectedAcyclicGraph(Graph g) {
		dfsVertex = new DFSVertex[g.size()];
		ancestorVertex = new boolean[g.size()];
		for (Graph.Vertex u : g) {
			dfsVertex[u.name] = new DFSVertex(u);
		}
	}

	/**
	 * Function to initiate DepthFirstSearch.
	 * 
	 * @param g
	 *            - input Graph.
	 * @return boolean stating whether graph has cycle.
	 */
	private boolean dfs(Graph g) {

		boolean isBackEdgeFound = true;
		for (Graph.Vertex u : g) {
			if (!seen(u)) {
				isBackEdgeFound = dfsVisit(u);
				if (!isBackEdgeFound) { // Graph has cycle
					break;
				}
			}
		}
		return isBackEdgeFound;
	}

	/**
	 * Recursive function to implement DFS.
	 * 
	 * @param u
	 *            - Vertex of the graph.
	 * @return boolean stating whether graph has cycle.
	 */
	private boolean dfsVisit(Vertex u) {

		boolean DAG = true;
		visit(u);
		ancestorVertex[u.name] = true;
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v)) {
				DAG = dfsVisit(v);
			} else if (isAncestor(v)) { // Check whether edge goes back to
										// ancestor.
				DAG = false;
				break;
			}
		}
		ancestorVertex[u.name] = false; // indicates that vertex u has seen all
										// its children.
		return DAG;

	}

	/**
	 * Function to find whether an vertex is an ancestor for the current vertex.
	 * 
	 * @param v
	 *            - Vertex to be checked whether it is an ancestor.
	 * @return boolean whether the vertex is an ancestor.
	 */
	private boolean isAncestor(Vertex v) {
		return ancestorVertex[v.name];
	}

	/**
	 * Function to check whether a vertex is seen or not.
	 * 
	 * @param u
	 *            - Vertex to be checked whether it is seen.
	 * @return boolean stating whether a vertex is seen or not.
	 */
	private boolean seen(Graph.Vertex u) {
		DFSVertex dfs = getDFSVertex(u);
		return dfs.seen;
	}

	/**
	 * Function to visit a node by marking it as seen.
	 * 
	 * @param u
	 *            - Vertex to be seen.
	 */
	private void visit(Graph.Vertex u) {
		DFSVertex dfs = getDFSVertex(u);
		dfs.seen = true;

	}

	private DFSVertex getDFSVertex(Graph.Vertex u) {
		return dfsVertex[u.name];
	}

	/**
	 * Function to check whether input graph is a DAG.
	 * 
	 * @param graph
	 *            - Input graph to be checked for DAG.
	 * @return boolean stating whether graph has cycle.
	 */
	public static boolean isDAG(Graph graph) {
		DirectedAcyclicGraph dag = new DirectedAcyclicGraph(graph);
		return dag.dfs(graph);
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);

		boolean isDirectedAcyclicGraph = DirectedAcyclicGraph.isDAG(g);
		if (isDirectedAcyclicGraph) {
			System.out.println("Given directed graph is a DAG");
		} else {
			System.out.println("Given directed graph has cycles");
		}
	}

}
