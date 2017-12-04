package cs6301.g38;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to find PostMan tour.
 */
public class PostManGraph extends Graph {

	private PVertex source;

	private PVertex sink;

	private Graph graph;

	private List<Edge> eulerTour;

	private long eulerLength;

	private java.util.HashMap<Edge, Integer> capacity;
	private java.util.HashMap<Edge, Integer> cost;

	private Vertex startVertex;

	static class PVertex extends Vertex

	{
		int excess = 0;
		List<Edge> adj, revAdj;

		public PVertex(Vertex u) {
			super(u);
			adj = new LinkedList<Graph.Edge>();
			revAdj = new LinkedList<Graph.Edge>();

		}

		@Override
		public Iterator<Edge> iterator() {
			return adj.iterator();
		}

	}

	private PVertex[] pVertices;

	public PostManGraph(Graph g, Vertex startVertex) {
		super(g);
		this.graph = g;
		this.startVertex = startVertex;
		pVertices = new PVertex[g.size() + 2];

		for (Vertex u : g) {
			pVertices[u.getName()] = new PVertex(u);
		}

		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				PVertex x1 = getVertex(u);
				PVertex x2 = getVertex(v);
				x1.adj.add(e);
				x2.revAdj.add(e);

			}
		}

	}

	PVertex getVertex(Vertex u) {
		return Vertex.getVertex(pVertices, u);
	}

	// Get a postman tour
	public List<Edge> getTour() {

		findTour();

		Euler euler = new Euler(this.graph, startVertex);

		eulerTour = euler.findEulerTour();
		return eulerTour;
	}

	// Find length of postman tour
	public long postmanTour() {



		findTour();

		for (Vertex v : this.graph) {
			for (Edge e : v) {
				eulerLength = eulerLength + e.weight;
			}
		}
		return eulerLength;

	}

	@Override
	public int size() {
		return pVertices.length;
	}

	private void findTour() {
		if (findImbalancedVertices()) {
			MinCostGraph cg = new MinCostGraph(this, source, sink, capacity, cost);
			cg.cycleCancellation(Integer.MAX_VALUE);
			Map<Edge, Integer> edges = new HashMap<Edge, Integer>();
			int flow = 0;
			for (Vertex v : this.graph) {
				for (Edge e : v) {
					flow = cg.flow(e);
					if (flow > 0) {
						edges.put(e, flow);
					}
				}

			}

			for (Entry<Edge, Integer> entry : edges.entrySet()) {
				flow = entry.getValue();
				Edge e = entry.getKey();
				while (flow > 0) {
					this.addEdge(e.from, e.to, e.weight, this.edgeSize() + 1);
					flow--;

				}
			}
		}

	}

	private boolean findImbalancedVertices() {

		boolean isExist = false;
		for (PVertex pv : pVertices) {
			if (pv != null) {
				pv.excess = pv.revAdj.size() - pv.adj.size();
				if (!isExist && pv.excess != 0) {
					isExist = true;
				}
			}
		}

		if (isExist) {
			capacity = new java.util.HashMap<>();
			cost = new java.util.HashMap<>();

			for (PVertex pv : pVertices) {
				if (pv != null) {
					for (Edge e : pv) {
						capacity.put(e, Integer.MAX_VALUE);
						cost.put(e, e.weight);
					}
				}
			}

			source = new PVertex(new Vertex(graph.size()));
			sink = new PVertex(new Vertex(graph.size() + 1));

			for (PVertex pv : pVertices) {
				if (pv != null) {
					if (pv.excess > 0) {
						Edge e = new Edge(source, pv, 0, ++m);
						source.adj.add(e);
						pv.revAdj.add(e);
						capacity.put(e, pv.excess);
						cost.put(e, 0);

					} else if (pv.excess < 0) {
						Edge e = new Edge(pv, sink, 0, ++m);
						pv.adj.add(e);
						sink.revAdj.add(e);
						capacity.put(e, pv.excess * -1);
						cost.put(e, 0);

					}
				}
			}

			pVertices[source.name] = source;

			pVertices[sink.name] = sink;
		}

		return isExist;
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new ArrayIterator<Vertex>(pVertices);
	}
}
