package cs6301.g38;

import java.util.List;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Euler {
	private int VERBOSE;
	private Graph g;
	private List<Graph.Edge> tour = new LinkedList<>(); // output tour
	private EulerVertex start; // start vertex
	private ArrayList<EulerVertex> ev = new ArrayList<EulerVertex>(); 
	

	private class EulerVertex implements Iterable<EulerEdge> {
		int name;
		List<EulerEdge> unexploredEdges = new LinkedList<EulerEdge>();// Unexplored
																		// edges
		List<EulerEdge> mytour = new LinkedList<EulerEdge>(); // Tour for this
																// vertex
		Graph.Vertex myVertex;

		EulerVertex(Graph.Vertex v) {
			myVertex = v;
			name = v.getName();

		}

		private void assignAdjList() {

			for (Edge e : this.myVertex) {
				EulerVertex fromVertex = ev.get(e.from
						.getName());
				EulerVertex toVertex = ev.get(e.to
						.getName());
				EulerEdge eulerEdge = new EulerEdge(fromVertex, toVertex, e);
				// eAdj.add(eulerEdge);
				unexploredEdges.add(eulerEdge);
			}

		}

		@Override
		public Iterator<EulerEdge> iterator() {

			return unexploredEdges.iterator();
		}
	}

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

	private void assignEulerEdge() {

		for (EulerVertex vertex : ev) {
			vertex.assignAdjList();

		}

	}

	private void assignEulerVertex(Graph g) {

		for (Vertex v : g) {
			EulerVertex vertex = new EulerVertex(v);
			ev.add(vertex);
			

		}

	}

	// function to find an Euler tour
	public List<Graph.Edge> findEulerTour() {
		findTours();

		if (VERBOSE > 9) {
			printTours();
		}
		stitchTours();
		return tour;
	}

	/*
	 * test if the graph is Eulerian. If the graph is not Eulerian, it prints
	 * the message: "Graph is not Eulerian" and one reason why, such as
	 * "inDegree = 5, outDegree = 3 at Vertex 37" or
	 * "Graph is not strongly connected"
	 */
	public boolean isEulerian() {

//		if (!isStronglyConnected()) {
//			return false;
	//	}

		return degreeCheckOnVertices();

	}

	private boolean degreeCheckOnVertices() {
		boolean isDegreeMatches = true;

		for (Vertex v : g) {
			if (v.adj.size() != v.revAdj.size()) {
				System.out.println("Graph is not Eulerian");
				System.out.println("outDegree = " + v.adj.size()
						+ " , inDegree = " + v.revAdj.size() + " at Vertex "
						+ v);
				isDegreeMatches = false;
				break;
			}
		}
		return isDegreeMatches;

	}

	private boolean isStronglyConnected() {
		if (SCC.stronglyConnectedComponents(g) != 1) {
			System.out.println("Graph is not Eulerian");
			System.out.println("Reason: Graph is not strongly connected");
			return false;

		}
		return true;
	}

	private void findTours() {
		EulerVertex tmpStartVertex = start;

		findTour(tmpStartVertex, tmpStartVertex.mytour);
		while ((tmpStartVertex = getNodeWithUnexploredEdges()) != null) {
			findTour(tmpStartVertex, tmpStartVertex.mytour);
		}

	}

	private EulerVertex getNodeWithUnexploredEdges() {
		EulerVertex vertexUnExploreEdge = null;
	for(EulerVertex vertex : ev)
	{
		if(vertex.unexploredEdges.size()>0)
		{
			vertexUnExploreEdge = vertex;
			break;
		}
	}
		return vertexUnExploreEdge;
	}

	private void findTour(EulerVertex u, List<EulerEdge> tour) {
		EulerEdge unExploredEdge;
		while ((unExploredEdge = getUnexploredEdge(u)) != null) {

			tour.add(unExploredEdge);
			u = unExploredEdge.otherEnd(u);

		}
	}

	private EulerEdge getUnexploredEdge(EulerVertex u2) {

		Iterator<EulerEdge> iteratorEdge = u2.unexploredEdges.iterator();
		EulerEdge edge = null;
		while (iteratorEdge.hasNext()) {
			edge = iteratorEdge.next();

			break;
		}
		if (edge != null) {
			u2.unexploredEdges.remove(edge);// Actually it takes O(1) since it
											// is the first element in the
											// LinkedList.
			
		}
		return edge;
	}

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

	// Stitch tours into a single tour
	private void stitchTours() {
		explore(start);
	}

	private void explore(EulerVertex v) {

		EulerVertex tmp = v;
		EulerVertex start = v;

		for (EulerEdge edgesInTour : tmp.mytour) {
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
