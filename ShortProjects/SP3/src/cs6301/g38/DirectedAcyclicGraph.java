package cs6301.g38;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import cs6301.g38.Graph.Vertex;

public class DirectedAcyclicGraph {

	// Class to store information about a vertex in this algorithm
	static class DFSVertex {
		Graph.Vertex element;
		boolean seen;

		DFSVertex(Graph.Vertex u) {
			element = u;
			seen = false;
		}
	}

	private DFSVertex[] dfsVertex;
	
	private List<Vertex> ancestorVertex = new ArrayList <>();
	public DirectedAcyclicGraph(Graph g) {
		dfsVertex = new DFSVertex[g.size()];
		for (Graph.Vertex u : g) {
			dfsVertex[u.name] = new DFSVertex(u);
		}
	}

	private boolean dfs(Graph g) {

		boolean isBackEdgeFound =true;
		for (Graph.Vertex u : g) {
			if (!seen(u)) {
				isBackEdgeFound = dfsVisit(u);
				if (!isBackEdgeFound) {
					break;
				}
			}
		}
		return isBackEdgeFound;
	}

	private boolean dfsVisit(Vertex u) {

		boolean DAG = true;
		visit(u);
		ancestorVertex.add(u);
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v) ) {
				DAG= dfsVisit(v);
			} else if ( isRecursionStack(v)) {
				DAG = false; // back edge
				break;
			}
		}
		ancestorVertex.remove(u);
		


		return DAG;

	}

	private boolean isRecursionStack(Vertex v) {
		return ancestorVertex.contains(v);
	}

	private boolean seen(Graph.Vertex u) {
		DFSVertex dfs = getDFSVertex(u);
		return dfs.seen;
	}

	// Visit a node by marking it as seen
	private void visit(Graph.Vertex u) {
		DFSVertex dfs = getDFSVertex(u);
		dfs.seen = true;
		
	}

	public DFSVertex getDFSVertex(Graph.Vertex u) {
		return dfsVertex[u.name];
	}

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
