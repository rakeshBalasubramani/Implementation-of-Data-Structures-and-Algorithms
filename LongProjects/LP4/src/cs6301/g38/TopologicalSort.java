package cs6301.g38;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class TopologicalSort extends Graph {

	TVertex[] tv; // vertices of graph
	private TVertex[] list;

	private long count;

	private long enumCount;

	public static class TVertex extends Vertex {
		boolean visited;
		List<TEdge> xadj, revAdj;
		int inDegree, outDegree;

		TVertex(Vertex u) {
			super(u);
			visited = false;
			xadj = new LinkedList<>();
			revAdj = new LinkedList<>();
		}

		boolean isVisited() {
			return visited;
		}

		void visit() {
			visited = true;
		}

		@Override
		public Iterator<Edge> iterator() {
			return new XVertexIterator(this);
		}

		class XVertexIterator implements Iterator<Edge> {
			TEdge cur;
			Iterator<TEdge> it;
			boolean ready;

			XVertexIterator(TVertex u) {
				this.it = u.xadj.iterator();
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
				while (cur.isDisabled() && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !cur.isDisabled();
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

		public void addEdge(TVertex x2, int weight) {
			xadj.add(new TEdge(this, x2, weight));
			outDegree++;
		}

		public void addRevEdge(TVertex x2, int weight) {
			revAdj.add(new TEdge(x2, this, weight));
			inDegree++;

		}

		public void unVisit() {
			visited = false;
		}
	}

	static class TEdge extends Edge {
		boolean disabled;

		TEdge(TVertex from, TVertex to, int weight) {
			super(from, to, weight);
			disabled = false;
		}

		boolean isDisabled() {
			TVertex xfrom = (TVertex) from;
			TVertex xto = (TVertex) to;
			return disabled || xfrom.isVisited() || xto.isVisited();
		}
	}

	public TopologicalSort(Graph g) {
		super(g);
		tv = new TVertex[g.size()];
		list = new TVertex[g.size()];
		for (Vertex u : g) {
			tv[u.getName()] = new TVertex(u);
		}

		// Make copy of edges
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				TVertex x1 = getVertex(u);
				TVertex x2 = getVertex(v);
				x1.addEdge(x2, e.weight);

			}
		}

		for (Vertex u : g) {
			Iterator<Edge> rev = u.reverseIterator();
			while (rev.hasNext()) {
				Edge e = rev.next();
				Vertex v1 = e.otherEnd(u);
				TVertex tv = getVertex(u);
				TVertex tv1 = getVertex(v1);
				tv.addRevEdge(tv1, e.weight);
			}

		}

	}

	@Override
	public Iterator<Vertex> iterator() {
		return new XGraphIterator(this);
	}

	class XGraphIterator implements Iterator<Vertex> {
		Iterator<TVertex> it;
		TVertex tcur;
		boolean ready;

		XGraphIterator(TopologicalSort xg) {
			this.it = new ArrayIterator<TVertex>(xg.tv, 0, xg.size() - 1);
		}

		public boolean hasNext() {

			if (ready) {
				return true;
			}

			if (!it.hasNext()) {
				return false;
			}
			tcur = it.next();
			while ((tcur.isVisited() && it.hasNext())
					|| (tcur.inDegree != 0 && it.hasNext())) {
				tcur = it.next();
			}
			ready = true;

			if (tcur.inDegree != 0) {
				return false;
			}
			return !tcur.isVisited();
		}

		public Vertex next() {

			if (!ready) {
				if (!hasNext()) {
					throw new java.util.NoSuchElementException();
				}
			}
			ready = false;

			return tcur;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();

		}

	}

	@Override
	public Vertex getVertex(int n) {
		return tv[n - 1];
	}

	TVertex getVertex(Vertex u) {
		return Vertex.getVertex(tv, u);
	}

	void visit(int i) {
		TVertex u = (TVertex) getVertex(i);
		u.visit();
	}

	public void countTopologicalSorts() {

		boolean isAllNodeVisited = false;

		for (Vertex v : this) {
			TVertex vertex = (TVertex) v;

			reduceInDegreeForAdj(vertex);

			vertex.visit();

			countTopologicalSorts();

			vertex.unVisit();
			resetInDegreeForAdj(vertex);

			isAllNodeVisited = true;

		}

		if (!isAllNodeVisited) {
			count++;
		}

	}

	public void enumerateTopologicalSorts(int curSize) {

		boolean isAllNodeVisited = false;

		for (Vertex v : this) {
			TVertex vertex = (TVertex) v;

			reduceInDegreeForAdj(vertex);

			vertex.visit();

			list[curSize++] = vertex;
			enumerateTopologicalSorts(curSize);

			vertex.unVisit();
			curSize--;
			resetInDegreeForAdj(vertex);

			isAllNodeVisited = true;

		}

		if (!isAllNodeVisited) {
			enumCount++;
			for (TVertex v : list) {
				System.out.print(v + " ");
			}
			System.out.println();
		}

	}

	private void resetInDegreeForAdj(TVertex vertex) {
		Iterator<TEdge> iter1 = vertex.xadj.listIterator();
		while (iter1.hasNext()) {
			TEdge edge = iter1.next();
			TVertex ver = (TVertex) edge.toVertex();
			ver.inDegree++;
		}
	}

	private void reduceInDegreeForAdj(TVertex vertex) {
		Iterator<TEdge> iter = vertex.xadj.listIterator();
		while (iter.hasNext()) {
			TEdge edge = iter.next();
			TVertex ver = (TVertex) edge.toVertex();
			ver.inDegree--;

		}
	}

	public long getCountPath() {
		return count;
	}

	public long getEnumCountPath() {
		return enumCount;
	}

	public static void main(String[] args) {
		Graph g = Graph.readDirectedGraph(new Scanner(System.in));
		TopologicalSort xg = new TopologicalSort(g);
		xg.countTopologicalSorts();
		xg.enumerateTopologicalSorts(0);
		System.out.println(xg.count + " -- " + xg.enumCount);
	}

}
