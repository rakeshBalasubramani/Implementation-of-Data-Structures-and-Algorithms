package cs6301.g38;

import java.util.Scanner;
import cs6301.g38.Graph.Vertex;

public class DirectedAcyclicGraph {

	// Class to store information about a vertex in this algorithm
	class DFSVertex {
		Graph.Vertex element;
		boolean seen;

		DFSVertex(Graph.Vertex u) {
			element = u;
			seen = false;
		}
	}

	DFSVertex[] dfsVertex;
	Graph g;
	static boolean checked;

	public DirectedAcyclicGraph(Graph g) {
		this.g = g;
		dfsVertex = new DFSVertex[g.size()];
		for (Graph.Vertex u : g) {
			dfsVertex[u.name] = new DFSVertex(u);
		}
	}

	private boolean DFS(Graph g) {

		boolean d =true;
		for (Graph.Vertex u : g) {
			if (!seen(u)) {
				d = dfsVisit(u);
				if (!d) {
					break;
				}
			}
		}
		return d;
	}

	private boolean dfsVisit(Vertex u) {

		boolean DAG = true;
		visit(u);
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v)) {
				dfsVisit(v);
			} else {
				DAG = false; // back edge
				checked = true;
				break;
			}
		}
		
		if (checked) {
			DAG = false;
		}

		return DAG;

	}

	boolean seen(Graph.Vertex u) {
		DFSVertex dfs = getDFSVertex(u);
		return dfs.seen;
	}

	// Visit a node by marking it as seen
	void visit(Graph.Vertex u) {
		DFSVertex dfs = getDFSVertex(u);
		dfs.seen = true;
	}

	DFSVertex getDFSVertex(Graph.Vertex u) {
		return dfsVertex[u.name];
	}

	public boolean isDAG(DirectedAcyclicGraph graph) {

		boolean isAcyclic = DFS(graph.g);
		return isAcyclic;
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);	
		Graph g = Graph.readDirectedGraph(in);
		DirectedAcyclicGraph dag = new DirectedAcyclicGraph(g);
		boolean isDirectedAcyclicGraph = dag.isDAG(dag);
		if (isDirectedAcyclicGraph) {
			System.out.println("Given directed graph is a DAG");
		} else {
			System.out.println("Given directed graph has cycles");
		}
	}

}
