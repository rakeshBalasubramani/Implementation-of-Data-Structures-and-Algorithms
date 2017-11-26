package cs6301.g38;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import cs6301.g38.BFS.BFSVertex;


public class MaxFlowGraph extends Graph {
	FVertex[] fVertices; // vertices of graph
	private FVertex source;
	private FVertex terminal;
	int maxFlow;
	LinkedList<FVertex> nodes;
	private BFS bfsHelper;
	private FEdge[] reverseEdge, forwardEdge;
	int maxHeight;

	private Set<Vertex> minCutS = new HashSet<>();
	private Set<Vertex> minCutT = new HashSet<>();

	static class FVertex extends Vertex {

		List<FEdge> adjEdge, residualEdge;
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

		FVertex parent;
		int height, excess;
		FVertex(Vertex u) {
			super(u);
			height = 0;
			excess = 0;
			adjEdge = new LinkedList<>();
			residualEdge = new LinkedList<>();
		}

		@Override
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
			flow=0;
			this.capacity = capacity;
		}

		boolean isFeasibleFlow() {
			return (flow < capacity);
		}

	}

	public MaxFlowGraph(Graph g, Vertex source, Vertex terminal, HashMap<Edge, Integer> capacity) {
		super(g);
		maxHeight = 2*g.size()-1;
		nodes = new LinkedList<>();
		fVertices = new FVertex[g.size()]; // Extra space is allocated in array for nodes to be added later
		reverseEdge = new FEdge[this.edgeSize()];
		forwardEdge = new FEdge[this.edgeSize()];
		for (Vertex u : g) {
			fVertices[u.getName()] = new FVertex(u);
		}
		this.source = getVertex(source);
		this.terminal = getVertex(terminal);
		for (Vertex u : g) {
			if (!(u.equals(source) || u.equals(terminal))) {
				nodes.add(getVertex(u));
			}
		}
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
				x2.residualEdge.add(revE);
				reverseEdge[e.name - 1] = revE;

			}
		}

		for (FVertex u : fVertices) {
			u.adjEdge.addAll(u.residualEdge);
		}
		bfsHelper = new BFS(this, this.source);

	}

	FVertex getVertex(Vertex u) {
		return Vertex.getVertex(fVertices, u);
	}

	private int sendFlow(Vertex vertex, int flow) {
		if (vertex.equals(terminal))
			return flow;

		for (Edge e : vertex) {
			Vertex otherVertex = e.otherEnd(vertex);
			FEdge fe = (FEdge) e;
			if (bfsHelper.distance(otherVertex) == (bfsHelper.distance(vertex) + 1)) {
				int curr_flow = findMin(flow, fe.capacity - fe.flow);

				int temp_flow = sendFlow(getVertex(otherVertex), curr_flow);

				if (temp_flow > 0) {
					fe.flow += temp_flow;
					reverseEdge[e.name - 1].flow -= temp_flow;
					return temp_flow;
				}
			}
		}

		return 0;
	}

	private int findMin(int flow, int i) {
		return (Integer.compare(flow, i) > 0) ? i : flow;
	}

	public void dinitz() {
		int flow;
		bfsHelper.bfs();
		while (bfsHelper.distance(terminal) != BFS.INFINITY) {
			flow = sendFlow(getVertex(source), BFS.INFINITY);
			while (flow > 0) {

				// Add path flow to overall flow
				this.maxFlow += flow;
				flow = sendFlow(getVertex(source), BFS.INFINITY);

			}
			bfsHelper.reinitialize(source);
			bfsHelper.bfs();

		}

	findCut();

	}
	
	
	private void findCut()
	{
		bfsHelper.reinitialize(source);
		bfsHelper.bfs();
		
		for (Vertex v : this) {
			BFSVertex bfsV = bfsHelper.getVertex(v);
			if (bfsV.distance != BFS.INFINITY) {
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
		boolean done = false;
		int oldHeight;
		while (!done) {
			Iterator<FVertex> it = nodes.iterator();
			done = true;
			FVertex temp = null;
			while (it.hasNext()) {
				FVertex u = it.next();
				if (u.getExcess() == 0) {
					continue;
				}
				oldHeight = u.getHeight();
				discharge(u);
				if (u.getHeight() != oldHeight) {
					done = false;
					temp = u;
					break;
				}
			}
			if (!done) {
				it.remove();
				nodes.addFirst(temp);
			}
		}
		maxFlow = terminal.getExcess();

findCut();
	}
	
	private void discharge(FVertex u) {
		boolean flag = true;
		while (u.getExcess() > 0 && flag && u.getHeight()<=maxHeight) {
			for (FEdge e : u.adjEdge) {
				FVertex v = (FVertex) e.otherEnd(u);
				if (inResidualGraph(u, e) && u.getHeight() == 1 + v.getHeight()) {
					push(u, v, e);
					if (u.getExcess() == 0) {
						return;
					}
				}
			}
			relabel(u);
			flag = false;
			for (FEdge e : u.adjEdge) {
				if (e.isFeasibleFlow()) {
					flag = true;
					break;
				}
			}
		}
		if (u.getExcess() > 0) {
			FEdge parentEdge = null;
			for (FEdge e : u.parent.adjEdge) {
				FVertex v = (FVertex) e.otherEnd(u.parent);
				if (v.equals(u)) {
					parentEdge = e;
				}
			}
			u.setHeight(u.parent.getHeight() + 1);
			push(u, u.parent, parentEdge);
		}
	}
	
	private void relabel(FVertex u) {
		
		int minHeight = Integer.MAX_VALUE;
		for (FEdge e : u.adjEdge) {
			if (e.isFeasibleFlow()) {
				FVertex temp = (FVertex) e.otherEnd(u);
				if (temp.getHeight() < minHeight) {
					u.setHeight(1 + temp.getHeight());
					minHeight = temp.getHeight();
				}
			}
		}
		if (minHeight != Integer.MAX_VALUE && u.getHeight()<=maxHeight) {
			u.setHeight(1 + minHeight);
		}
	}

	private void push(FVertex u, FVertex v, FEdge e) {
		int delta;
		if (e.from.equals(u)) {
			delta = Math.min(u.getExcess(), e.getCapacity()-e.getFlow());
			e.setFlow(e.getFlow() + delta);
			v.parent = u;
		} else {
			delta = Math.min(u.getExcess(), e.getCapacity()-e.getFlow()+u.getExcess());
			e.setFlow(e.getFlow() - delta);
		}
		u.setExcess(u.getExcess() - delta);
		v.setExcess(v.getExcess() + delta);

	}

	private boolean inResidualGraph(FVertex u, FEdge e) {
		return (boolean) (e.from.equals(u) ? e.getFlow() < e.getCapacity() : e.getFlow() > 0);
	}

	private void initialize() {
		source.height = this.size();
		for (FEdge e : source.adjEdge) {
			e.setFlow(e.getCapacity());
			source.setExcess(source.getExcess() - e.getCapacity());
			FVertex u = (FVertex) e.otherEnd(source);
			u.setExcess(u.getExcess() + e.getCapacity());
			u.parent = source;

		}

	}
	
}
