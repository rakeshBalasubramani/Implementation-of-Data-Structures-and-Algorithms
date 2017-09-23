package cs6301.g38;

import java.util.List;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Desc Class used to check whether given graph is an Eulerian Graph
 */
public class Euler {
	private int VERBOSE;
	private Graph g;
	private List<Graph.Edge> tour; // output tour
	private EulerVertex start; // start vertex
	private ArrayList<EulerVertex> ev = new ArrayList<EulerVertex>();// Parallel list to store information about vertices

	/**
	 * @Desc Class used to store vertex information.
	 *
	 */
	private class EulerVertex implements Iterable<EulerEdge> {
		List<EulerEdge> unexploredEdges = new LinkedList<EulerEdge>();// List to store Unexplored edges 
		List<EulerEdge> mytour = new LinkedList<EulerEdge>(); // List to store tour for this vertex
						
		Graph.Vertex myVertex;

		boolean isTourExplored; // to check whether tour from this vertex is explored

		EulerVertex(Graph.Vertex v) {
			myVertex = v;
			v.getName();

		}

		/**
		 * Function to store unexplored edges for each vertex.
		 * 
		 */
		private void assignAdjList() {

			for (Edge e : this.myVertex) {
				EulerVertex fromVertex = ev.get(e.from.getName());
				EulerVertex toVertex = ev.get(e.to.getName());
				EulerEdge eulerEdge = new EulerEdge(fromVertex, toVertex, e);
				unexploredEdges.add(eulerEdge);
			}

		}

		@Override
		public Iterator<EulerEdge> iterator() {

			return unexploredEdges.iterator();
		}
	}

	/**
	 * @Desc Class used to store edge information.
	 *
	 */
	private class EulerEdge {
		Graph.Edge edge;
		EulerVertex from; // head vertex
		EulerVertex to;

		EulerEdge(EulerVertex from, EulerVertex to, Graph.Edge e) {
			edge = e;
			this.from = from;
			this.to = to;
		}

		public EulerVertex otherEnd(EulerVertex u) {
			assert from == u || to == u;
			// if the vertex u is the head of the arc, then return the tail else
			// return the head
			if (from == u) {
				return to;
			} else {
				return from;
			}
		}

	}

	// Constructor
	public Euler(Graph g, Graph.Vertex start) {
		this.g = g;
		VERBOSE = 1;
		tour = new LinkedList<Graph.Edge>();
		assignEulerVertex(g);
		assignEulerEdge();

		this.start = ev.get(start.getName());

	}

	/**
	 * Helper function to store unexplored edges.
	 * 
	 */
	private void assignEulerEdge() {

		for (EulerVertex vertex : ev) {
			vertex.assignAdjList();

		}
	}

	/**
	 * Function to populate Euler Vertices.
	 * 
	 * @param g
	 *            - Input Graph.
	 */
	private void assignEulerVertex(Graph g) {

		for (Vertex v : g) {
			EulerVertex vertex = new EulerVertex(v);
			ev.add(vertex);
		}

	}

	/**
	 * Function to find an Euler Tour
	 * 
	 * @return Euler tour.
	 */
	public List<Graph.Edge> findEulerTour() {
		findTours();

		if (VERBOSE > 9) {
			printTours();
		}
		stitchTours();
		return tour;
	}

	/**
	 * Function to test if the graph is Eulerian.
	 * 
	 * @return True if graph is Eulerian else false.
	 */
	public boolean isEulerian() {

		if (!isStronglyConnected()) {
			return false;
		}

		return degreeCheckOnVertices();

	}

	/**
	 * Function to check whether each vertex has the same number of indegree and
	 * outdegree.
	 * 
	 * @return true if number of outdegree is equal to indegree else false.
	 */
	private boolean degreeCheckOnVertices() {
		boolean isDegreeMatches = true;

		for (Vertex v : g) {
			if (v.adj.size() != v.revAdj.size()) {
				System.out.println("Graph is not Eulerian");
				System.out.println("outDegree = " + v.adj.size() + " , inDegree = " + v.revAdj.size() + " at Vertex " + v);
				isDegreeMatches = false;
				break;
			}
		}
		return isDegreeMatches;

	}

	/**
	 * Function to check whether graph is strongly connected.
	 * 
	 * @return true if all the vertices have same component number else false.
	 */
	private boolean isStronglyConnected() {
		if (SCC.stronglyConnectedComponents(g) != 1) {
			System.out.println("Graph is not Eulerian");
			System.out.println("Reason: Graph is not strongly connected");
			return false;

		}
		return true;
	}

	/**
	 * Helper function to find tour from a given vertex.
	 * 
	 */
	private void findTours() {
		EulerVertex tmpStartVertex = start;

		findTour(tmpStartVertex, tmpStartVertex.mytour);
		while ((tmpStartVertex = getNodeWithUnexploredEdges()) != null) {
			findTour(tmpStartVertex, tmpStartVertex.mytour);
		}

	}

	/**
	 * Function to find vertices which has unexplored outgoing edges.
	 * 
	 * @return Vertex which has unexplored outgoing edges.
	 */
	private EulerVertex getNodeWithUnexploredEdges() {
		EulerVertex vertexUnExploreEdge = null;
		for (EulerVertex vertex : ev) {
			if (vertex.unexploredEdges.size() > 0) {
				vertexUnExploreEdge = vertex;
				break;
			}
		}
		return vertexUnExploreEdge;
	}

	/**
	 * Function to find sub tours from a given vertex.
	 * 
	 * @param u
	 *            - Vertex from which the tour is to be found.
	 * @param tour
	 *            - List to store edges of a tour.
	 */
	private void findTour(EulerVertex u, List<EulerEdge> tour) {
		EulerEdge unExploredEdge;
		while ((unExploredEdge = getUnexploredEdge(u)) != null) {

			tour.add(unExploredEdge);
			u = unExploredEdge.otherEnd(u);

		}
	}

	/**
	 * Function to find an unexplored outgoing edge from a given vertex.
	 * 
	 * @param u2
	 *            - Vertex from which an unexplored outgoing edge is to be
	 *            found.
	 * @return An unexplored outgoing edge.
	 */
	private EulerEdge getUnexploredEdge(EulerVertex u2) {

		Iterator<EulerEdge> iteratorEdge = u2.unexploredEdges.iterator();
		EulerEdge edge = null;
		while (iteratorEdge.hasNext()) {
			edge = iteratorEdge.next();

			break;
		}
		if (edge != null) {
			u2.unexploredEdges.remove(edge);// It takes O(1) since it is the first element in the LinkedList.

		}
		return edge;
	}

	/**
	 * Function to print the sub tours from a vertex.
	 * 
	 */
	void printTours() {

		for (EulerVertex v : ev) {
			if (v.mytour.size() > 0) {

				System.out.println("Tour from Vertex: " + v.myVertex);
				for (EulerEdge e : v.mytour) {
					System.out.print(e.edge);
				}
				System.out.println();
			}
		}

	}

	/**
	 * Helper function to stitch tours into a single tour.
	 * 
	 */
	private void stitchTours() {
		explore(start);
	}

	/**
	 * Function to stitch tours into a single tour.
	 * 
	 * @param v
	 *            - Vertex whose sub tour is added to the main output tour.
	 */
	private void explore(EulerVertex v) {
		if (v.isTourExplored) {
			return;
		}
		EulerVertex tmp = v;
		EulerVertex start = v;
	
		for (EulerEdge edgesInTour : tmp.mytour) {
			v.isTourExplored = true;
			tour.add(edgesInTour.edge);

			tmp = edgesInTour.otherEnd(tmp);
			if (tmp.mytour.size() > 0 && !start.equals(tmp)) {
				explore(tmp);
			}

		}

	}

	void setVerbose(int v) {
		VERBOSE = v;
	}
}
