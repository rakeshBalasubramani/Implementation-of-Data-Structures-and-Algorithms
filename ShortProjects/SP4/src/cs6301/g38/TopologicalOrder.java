package cs6301.g38;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

/**
 * @author RAKESH BALASUBRAMANI - rxb162130 HARIPRIYAA MANIAN – hum160030
 *         RAJKUMAR PANNEER SELVAM - rxp162130 AVINASH VENKATESH – axv165330
 * @Description This class is used to find the topological order of a graph
 *              using the inDegree of a vertex and also using DFS.
 *
 */
public class TopologicalOrder {

	private LinkedList<Vertex> order2 = new LinkedList<>(); // To get the
															// topological order
															// of the given
															// graph using DFS.
	private boolean[] seen; // To mark the vertices as visited.
	private boolean isDAG = true; // To check if the given graph is a DAG.
	private boolean[] recursionStack; // To store the vertices state in the
										// current recursion stack.

	/**
	 * To get the topological order of the given graph using inDegree (Algorithm
	 * 1).
	 * 
	 * @param g
	 *            - The given graph
	 * @return - The topological order of the graph,if given graph is DAG, else null.
	 */
	public List<Vertex> topoLogicalOrder1(Graph g) {

		int topNum = 0;
		ArrayDeque<Vertex> queue = new ArrayDeque<>();
		LinkedList<Vertex> topList = new LinkedList<>();
		int[] inDegree = new int[g.size()];
		Iterator<Vertex> v = g.iterator();
		while (v.hasNext()) { // To get the inDegree of vertices
			Vertex temp = v.next();
			inDegree[temp.getName()] = temp.revAdj.size();
			if (inDegree[temp.getName()] == 0) {
				queue.add(temp); // add to the queue if inDegree is zero.
			}
		}
		while (!queue.isEmpty()) { // Iterate to the queue to get the topological order.
			Vertex temp = queue.remove();
			topNum++;
			topList.add(temp);
			for (Edge x : temp.adj) {
				inDegree[x.otherEnd(temp).getName()]--;
				if (inDegree[x.otherEnd(temp).getName()] == 0) {
					queue.add(x.otherEnd(temp));
				}
			}
		}
		if (topNum == g.size())
			return topList;
		else
			return null; // if the given graph is not DAG.
	}

	/**
	 * To get the topological order of the given graph using DFS (Algorithm 2).
	 * 
	 * @param g
	 *            - The given graph.
	 * @return - The topological order of the given graph, if given graph is DAG, else null.
	 */
	public List<Vertex> topologicalOrder2(Graph g) {

		seen = new boolean[g.size()];
		recursionStack = new boolean[g.size()];
		Iterator<Vertex> v = g.iterator();
		while (v.hasNext()) {
			Vertex temp = v.next();
			if (!isSeen(temp)) {
				dfsVisit(temp);
			}
		}
		if (isDAG) {
			return order2;
		} else {
			return null;
		}
	}

	/**
	 * To get the visited state of the given vertex.
	 * 
	 * @param temp
	 *            - The given vertex to find if it is visited.
	 * @return - True if already visited else false.
	 */
	private boolean isSeen(Vertex temp) {
		return seen[temp.getName()];
	}

	/**
	 * To implement DFS and get the list of vertices in decreasing Finish time
	 * order.
	 * 
	 * @param temp
	 *            - Vertex passed during DFS
	 * @return - The order of Vertices visited.
	 */
	private LinkedList<Vertex> dfsVisit(Vertex temp) {
		seen[temp.getName()] = true;
		recursionStack[temp.getName()] = true;
		for (Edge e : temp) {
			Vertex v = e.otherEnd(temp);
			if (!isSeen(v)) {
				dfsVisit(v);
			} else if (recursionStack[v.getName()]) {
				isDAG = false;
			}
		}
		order2.addFirst(temp);
		recursionStack[temp.getName()] = false;
		return order2;
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);
		TopologicalOrder tp = new TopologicalOrder();
		System.out.println("Topological Order for algorithm 1 : " + tp.topoLogicalOrder1(g)); // Topological
																	// order
																	// with
																	// algorithm
																	// 1
		System.out.println("Topological Order for algorithm 2 : " + tp.topologicalOrder2(g)); // Topological
																	// order
																	// with
																	// algorithm
																	// 2
	}

}
