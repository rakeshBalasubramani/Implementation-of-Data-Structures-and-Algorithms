package cs6301.g38;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cs6301.g38.BFSFlowGraph.BFSVertex;
import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to implement CycleCancellation algorithm
 */
public class CycleCancellationGraph extends Graph {
	private FVertex[] fVertices; // vertices of graph
	private FVertex source;
	private FVertex terminal;
	private int currentFlow;
	private FEdge[] reverseEdge, forwardEdge;
	private BFSFlowGraph bfsHelper;

	static class FVertex extends Vertex {

		List<FEdge> adjEdge, revEdge;

		boolean visited;

		FVertex(Vertex u) {
			super(u);

			adjEdge = new LinkedList<>();
			revEdge = new LinkedList<>();
		}

		public Iterator<Edge> iterator() {
			return new XVertexIterator(this);
		}

		class XVertexIterator implements Iterator<Edge> {
			FEdge cur;
			Iterator<FEdge> it;
			boolean ready;

			XVertexIterator(FVertex u) {
				this.it = u.adjEdge.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while (!cur.isFeasibleFlow() && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return cur.isFeasibleFlow();
			}

			public Edge next() {
				if (!ready) {
					if (!hasNext()) {
						throw new java.util.NoSuchElementException();
					}
				}
				ready = false;
				return cur;
			}

			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}
		}

	}

	static class FEdge extends Edge {

		int capacity = 0;
		int flow = 0;
		FEdge reverseEdge;

		public FEdge getReverseEdge() {
			return reverseEdge;
		}

		public void setReverseEdge(FEdge reverseEdge) {
			this.reverseEdge = reverseEdge;
		}

		public int getFlow() {
			return flow;
		}

		public void setFlow(int flow) {
			this.flow = flow;
		}

		public int getCapacity() {
			return capacity;
		}

		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		FEdge(FVertex from, FVertex to, int weight, int capacity, int name) {
			super(from, to, weight, name);
			flow = 0;
			this.capacity = capacity;
		}

		boolean isFeasibleFlow() {
			return (flow < capacity);
		}

	}

	public CycleCancellationGraph(Graph g, Vertex source, Vertex terminal,
			HashMap<Edge, Integer> capacity) {
		super(g);
		new LinkedList<>();
		fVertices = new FVertex[g.size()]; // Extra space is allocated in array
											// for nodes to be added later
		reverseEdge = new FEdge[this.edgeSize()];
		forwardEdge = new FEdge[this.edgeSize()];
		for (Vertex u : g) {
			fVertices[u.getName()] = new FVertex(u);
		}
		this.source = getVertex(source);
		this.terminal = getVertex(terminal);

		// Make copy of edges
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				FVertex x1 = getVertex(u);
				FVertex x2 = getVertex(v);
				FEdge frwdE = new FEdge(x1, x2, e.weight, capacity.get(e),
						e.name);
				forwardEdge[e.name - 1] = frwdE;
				x1.adjEdge.add(frwdE);
				FEdge revE = new FEdge(x2, x1, e.weight, 0, e.name);
				x2.revEdge.add(revE);
				frwdE.setReverseEdge(revE);
				revE.setReverseEdge(frwdE);
				reverseEdge[e.name - 1] = revE;

			}
		}

		for (FVertex u : fVertices) {
			u.adjEdge.addAll(u.revEdge);
		}
		bfsHelper = new BFSFlowGraph(g, source);
	}

	FVertex getVertex(Vertex u) {
		return Vertex.getVertex(fVertices, u);
	}


	private int pushFlow(Vertex vertex, int flow) {
		if (vertex.equals(terminal))
			return flow;

		int curr_flow;
		int temp_flow;
		for (Edge e : vertex) {
			Vertex otherVertex = e.otherEnd(vertex);
			FEdge fe = (FEdge) e;
			if (bfsHelper.distance(otherVertex) == (bfsHelper.distance(vertex) + 1)) {
				curr_flow = Math.min(flow, fe.capacity - fe.flow);

				temp_flow = pushFlow(getVertex(otherVertex), curr_flow);

				if (temp_flow > 0) {
					fe.flow += temp_flow;
					fe.getReverseEdge().flow -= temp_flow;
					return temp_flow;
				}
			}
		}

		return 0;
	}

	public void dinitzFlow(int flowNeeded) {
		int flow;
		bfsHelper.bfs();
		boolean isDone=false;
		while (bfsHelper.distance(terminal) != BFSFlowGraph.INFINITY || !isDone) {
			flow = pushFlow(getVertex(source), flowNeeded-currentFlow);
			while (flow > 0 || !isDone) {

				this.currentFlow += flow;
				if(flowNeeded!=currentFlow)
				{
				flow = pushFlow(getVertex(source), flowNeeded-currentFlow);
				}
				else
				{
					isDone=true;
				}

			}
			bfsHelper.reinitialize(source);
			bfsHelper.bfs();

		}


	}


}
