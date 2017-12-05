package cs6301.g38;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import cs6301.g38.BFSFlowGraph.BFSVertex;
import cs6301.g38.MaxFlowGraph.FVertex;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to implement Dinitz and relabelToFront
 */
public class MaxFlowGraph extends Graph {
	private FVertex[] fVertices; // vertices of graph
	private FVertex source;
	private FVertex terminal;
	private int maxFlow;
	int choice;
	private LinkedList<FVertex> nodes;
	private BFSFlowGraph bfsHelper;
	private FEdge[] reverseEdge, forwardEdge;
	int maxHeight;

	private Set<Vertex> minCutS = new HashSet<>();
	private Set<Vertex> minCutT = new HashSet<>();

	static class FVertex extends Vertex {

		List<FEdge> adjEdge, revEdge;
		int height, excess;
		boolean visited;

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getExcess() {
			return excess;
		}

		public void setExcess(int excess) {
			this.excess = excess;
		}

		FVertex(Vertex u) {
			super(u);
			height = 0;
			excess = 0;
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

	public MaxFlowGraph(Graph g, Vertex source, Vertex terminal, HashMap<Edge, Integer> capacity, int choice) {
		super(g);
		this.choice = choice;
		maxHeight = 2 * g.size() - 2;
		nodes = new LinkedList<>();
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
				FEdge frwdE = new FEdge(x1, x2, e.weight, capacity.get(e), e.name);
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
		bfsHelper = new BFSFlowGraph(this, this.source);

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

	public void dinitzMaxFlow() {
		int flow;
		bfsHelper.bfs();
		while (bfsHelper.distance(terminal) != BFSFlowGraph.INFINITY) {
			flow = pushFlow(getVertex(source), BFSFlowGraph.INFINITY);
			while (flow > 0) {

				// Add path flow to overall flow
				this.maxFlow += flow;
				flow = pushFlow(getVertex(source), BFSFlowGraph.INFINITY);

			}
			bfsHelper.reinitialize(source);
			bfsHelper.bfs();

		}

		findCut();

	}

	private void findCut() {
		bfsHelper.reinitialize(source);
		bfsHelper.bfs();

		for (Vertex v : this) {
			BFSVertex bfsV = bfsHelper.getVertex(v);
			if (bfsV.distance != BFSFlowGraph.INFINITY) {
				minCutS.add(v);
			} else {
				minCutT.add(v);
			}
		}
	}

	public Set<Vertex> getMinCutS() {
		return minCutS;
	}

	public Set<Vertex> getMinCutT() {
		return minCutT;
	}

	public int flow(Edge e) {
		return forwardEdge[e.name - 1].flow;
	}

	public void relabelToFront() {
		initialize();
		while (!nodes.isEmpty()) {
			FVertex u = nodes.removeFirst();
			discharge(u);
		}
		maxFlow = terminal.getExcess();
		findCut();
	}

	private void discharge(FVertex u) {
		int counter = 0;
		while (u.getExcess() > 0 && u.getHeight() <= maxHeight) {
			for (FEdge e : u.adjEdge) {
				FVertex v = (FVertex) e.otherEnd(u);
				if (e.isFeasibleFlow() && u.getHeight() == 1 + v.getHeight()) {
					push(u, v, e);
					if (u.getExcess() == 0) {
						return;
					}
				}
			}
			if (counter++ < 3) {
				relabel(u);
			} else {
				System.out.println("Called BFS");
				bfs();
				for(FVertex v:fVertices) {
					if(!v.equals(source)&&!v.equals(terminal)&&!v.visited&&v.height<this.size()) {
						v.height = this.size()+1;
					}
				}
			}

		}

	}

	private void relabel(FVertex u) {
		int minHeight = Integer.MAX_VALUE;
		for (FEdge e : u.adjEdge) {
			if (e.isFeasibleFlow()) {
				FVertex temp = (FVertex) e.otherEnd(u);
				if (temp.getHeight() < minHeight) {
					minHeight = temp.getHeight();
				}
			}
		}
		if (minHeight != Integer.MAX_VALUE && u.getHeight() <= maxHeight) {
			u.setHeight(1 + minHeight);
		}
	}

	private void push(FVertex u, FVertex v, FEdge e) {
		int delta;
		delta = Math.min(u.getExcess(), e.getCapacity() - e.getFlow());
		e.setFlow(e.getFlow() + delta);
		e.getReverseEdge().flow -= delta;
		if (v.getExcess() == 0 && !v.equals(source) && !v.equals(terminal))
			nodes.add(v);
		u.setExcess(u.getExcess() - delta);
		v.setExcess(v.getExcess() + delta);
	}

	private void initialize() {
		source.height = this.size();
		for (FEdge e : source.adjEdge) {
			e.setFlow(e.getCapacity());
			source.setExcess(source.getExcess() - e.getCapacity());
			FVertex u = (FVertex) e.otherEnd(source);
			u.setExcess(u.getExcess() + e.getCapacity());
			e.getReverseEdge().flow -= e.getCapacity();
			if (!u.equals(terminal))
				nodes.add(u);

		}

	}

	public int getMaxFlow() {
		return this.maxFlow;
	}

	private void bfs() {
		Queue<FVertex> q = new LinkedList<>();
		q.add(terminal);
		getVertex(terminal).visited = true;
		while (!q.isEmpty()) {
			FVertex u = q.remove();
			for (Edge e : u) {
				FVertex v = (FVertex) e.otherEnd(u);
				if (!v.visited) {
					v.visited = true;
					v.height = u.height + 1;
					q.add(v);
				}
			}
		}
	}

}
