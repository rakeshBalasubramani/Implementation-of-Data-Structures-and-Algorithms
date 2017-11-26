package cs6301.g38;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class RelabelGraph extends Graph {

	public RelabelGraph(Graph g, Vertex source, Vertex terminal, HashMap<Edge, Integer> capacity) {
		super(g);
		maxHeight = 2*g.size()-1;
		nodes = new LinkedList<>();
		fVertices = new FVertex[g.size()];
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
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				FVertex temp1 = getVertex(u);
				FVertex temp2 = getVertex(v);
				temp1.adjEdge.add(new FEdge(temp1, temp2, e.weight, capacity.get(e), e.name));

			}
		}
	}

	FVertex getVertex(Vertex u) {
		return Vertex.getVertex(fVertices, u);
	}

	public static class FVertex extends Vertex {
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
		List<FEdge> adjEdge;
		int height, excess;

		public FVertex(Vertex u) {
			super(u);
			height = 0;
			excess = 0;
			adjEdge = new LinkedList<>();
		}

	}

	public static class FEdge extends Edge {
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

		int flow, capacity;

		public FEdge(FVertex from, FVertex to, int weight, int capacity, int name) {
			super(from, to, weight, name);
			flow = 0;
			this.capacity = capacity;

		}

	}

	FVertex source, terminal;
	LinkedList<FVertex> nodes;
	FVertex fVertices[];
	int maxHeight;

	public int getMaxFlow() {
		return terminal.getExcess();
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
				if (!isEdgeFull(e)) {
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

	private boolean isEdgeFull(FEdge e) {
		return e.getCapacity() - e.getFlow() == 0;
	}

	private void relabel(FVertex u) {
		
		int minHeight = Integer.MAX_VALUE;
		for (FEdge e : u.adjEdge) {
			if (!isEdgeFull(e)) {
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
