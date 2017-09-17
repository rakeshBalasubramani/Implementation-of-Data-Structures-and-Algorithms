package cs6301.g38;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

/**
 * @author 
 	RAKESH BALASUBRAMANI - rxb162130
	HARIPRIYAA MANIAN – hum160030
	RAJKUMAR PANNEER SELVAM - rxp162130
	AVINASH VENKATESH – axv165330
 * @Description
 * 	This class is used to find the topological order of a graph using the  inDegree of a vertex and also using DFS.
 *
 */
public class TopologicalOrdering {

	LinkedList<Graph.Vertex> order2 = new LinkedList<>();	// To get the order of the given graph using DFS.
	boolean[] seen;	// To mark the vertices as visited.
	boolean isDAG = true;	// To check if the given graph is a DAG.
	List<Vertex> recursionStack = new ArrayList<Vertex>();	//To store the visited vertices. 

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);
		TopologicalOrdering tp = new TopologicalOrdering();
		System.out.println("Order1 : " + topLogicalOrder1(g));
		System.out.println("Order2 : " + tp.toplogicalOrder2(g));
	}

	/**
	 * @param g - The given graph
	 * @return - The topological order of the graph.
	 */
	private static LinkedList<Vertex> topLogicalOrder1(Graph g) {

		int topNum = 0;
		ArrayDeque<Vertex> q = new ArrayDeque<>();
		LinkedList<Vertex> topList = new LinkedList<>();
		int[] inDegree = new int[g.size()];
		Iterator<Vertex> v = g.iterator();
		while (v.hasNext()) {
			Vertex temp = v.next();
			inDegree[temp.getName()] = temp.revAdj.size();
			if (inDegree[temp.getName()] == 0) {
				q.add(temp);
			}
		}
		while (!q.isEmpty()) {
			Vertex temp = q.remove();
			topNum++;
			topList.add(temp);
			for (Edge x : temp.adj) {
				inDegree[x.otherEnd(temp).getName()]--;
				if (inDegree[x.otherEnd(temp).getName()] == 0) {
					q.add(x.otherEnd(temp));
				}
			}
		}
		if (topNum == g.size())
			return topList;
		else
			return null;
	}

	/**
	 * @param g - The given graph.
	 * @return - The topological order of the given graph.
	 */
	private LinkedList<Vertex> toplogicalOrder2(Graph g) {

		seen = new boolean[g.size()];
		Iterator<Vertex> v = g.iterator();
		while (v.hasNext()) {
			Vertex temp = v.next();
			if (isSeen(temp)) {
				DFSVisit(temp);
			}
		}
		if (isDAG) {
			return order2;
		} else {
			return null;
		}
	}

	/**
	 * @param temp - The given vertex to find if it is visited.
	 * @return - True if already visited else false.
	 */
	private boolean isSeen(Vertex temp) {
		return seen[temp.getName()];
	}

	/**
	 * @param temp - Vertex passed during DFS
	 * @return - The order of Vertices visited.
	 */
	private LinkedList<Vertex> DFSVisit(Vertex temp) {
		seen[temp.getName()] = true;
		recursionStack.add(temp);
		for (Edge e : temp) {
			Vertex v = e.otherEnd(temp);
			if (isSeen(v)) {
				DFSVisit(v);
			} else if (recursionStack.contains(v)) {
				isDAG = false;
			}
		}
		order2.addFirst(temp);
		recursionStack.remove(temp);
		return order2;
	}

}
