package cs6301.g38;

import java.util.List;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Euler {
	int VERBOSE;
	Graph g;
	List<Graph.Edge> tour = new LinkedList<>();
	EulerVertex start; // start vertex
	ArrayList<EulerVertex> vertexWithunExploredEdges = new ArrayList<EulerVertex>();
	ArrayList<EulerVertex> ev = new ArrayList<EulerVertex>();

	private class EulerVertex implements Iterable<EulerEdge> {
		int name;
		List<EulerEdge> unexploredEdges = new LinkedList<EulerEdge>();
		List<EulerEdge> mytour = new LinkedList<EulerEdge>();
		Graph.Vertex myVertex;

		EulerVertex(Graph.Vertex v) {
			myVertex = v;
			name = v.getName();

		}

		private void assignAdjList() {

			for (Edge e : this.myVertex) {
				EulerVertex fromVertex = vertexWithunExploredEdges.get(e.from
						.getName());
				EulerVertex toVertex = vertexWithunExploredEdges.get(e.to
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
	Euler(Graph g, Graph.Vertex start) {
		this.g = g;
		VERBOSE = 1;
		tour = new LinkedList<Graph.Edge>();
		assignEulerVertex(g);
		assignEulerEdge();

		this.start = vertexWithunExploredEdges.get(start.getName());

	}

	private void assignEulerEdge() {

		for (EulerVertex vertex : vertexWithunExploredEdges) {
			vertex.assignAdjList();

		}

	}

	private void assignEulerVertex(Graph g) {

		for (Vertex v : g) {
			EulerVertex vertex = new EulerVertex(v);
			vertexWithunExploredEdges.add(vertex);
			ev.add(vertex);

		}

	}

	// To do: function to find an Euler tour
	public List<Graph.Edge> findEulerTour() {
		findTours();

		if (VERBOSE > 9) {
			printTours();
		}
		stitchTours();
		return tour;
	}

	/*
	 * To do: test if the graph is Eulerian. If the graph is not Eulerian, it
	 * prints the message: "Graph is not Eulerian" and one reason why, such as
	 * "inDegree = 5, outDegree = 3 at Vertex 37" or
	 * "Graph is not strongly connected"
	 */
	public boolean isEulerian() {
		
		if(!isStronglyConnected())
		{
			return false;
		}
		
		return degreeCheckOnVertices();
		
		
	}
		
		
		

	private boolean degreeCheckOnVertices() {
		boolean isDegreeMatches = true;

		for(Vertex v : g)
		{
			if(v.adj.size()!=v.revAdj.size())
			{
				System.out.println("Graph is not Eulerian");
				System.out.println("outDegree = " + v.adj.size() + " , inDegree = " +  v.revAdj.size()  + " at Vertex " + v);
				isDegreeMatches=false;
				break;
			}
		}
		return isDegreeMatches;
		
	}

	private boolean isStronglyConnected() {
		if(SCC.stronglyConnectedComponents(g)!=1)
		{
		System.out.println("Graph is not Eulerian");
		System.out.println("Reason: Graph is not strongly connected");
		return false;
		
		}
		return true;
	}
	

	EulerVertex u = start;
	static List<Graph.Vertex> node;

	private void findTours() {
		EulerVertex tmpStartVertex = start;

		findTour(tmpStartVertex, tmpStartVertex.mytour);
		while ((tmpStartVertex = getNodeWithUnexploredEdges()) != null) {
			findTour(tmpStartVertex, tmpStartVertex.mytour);
		}

	}

	private EulerVertex getNodeWithUnexploredEdges() {
		EulerVertex vertexUnExploreEdge = null;
		if (vertexWithunExploredEdges.size() > 0) {
			vertexUnExploreEdge = vertexWithunExploredEdges.get(0);
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
			if (u2.unexploredEdges.size() == 0) {
				vertexWithunExploredEdges.remove(u2);
			}
		}
		return edge;
	}

	/*
	 * Print tours found by findTours() using following format: Start vertex of
	 * tour: list of edges with no separators Example: lp2-in1.txt, with start
	 * vertex 3, following tours may be found. 3:
	 * (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3) 4: (4,7)(7,8)(8,4) 5: (5,7)(7,9)(9,5)
	 * 
	 * Just use System.out.print(u) and System.out.print(e)
	 */
	void printTours() {
		
		for(EulerVertex v : ev)
		{
			if(v.mytour.size()>0)
			{

			    System.out.println("Tour from Vertex: " + v.myVertex );
		            for(EulerEdge e: v.mytour) {
		                System.out.print(e.edge);
		            }
		        System.out.println();
			}
		}
	}

	// Stitch tours into a single tour using the algorithm discussed in class
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
