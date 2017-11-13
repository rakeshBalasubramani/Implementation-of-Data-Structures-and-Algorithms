package cs6301.g38;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Avinash Venkatesh - axv165330 <br>
 *         HariPriyaa - hum160030 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         Raj Kumar Panneer Selvam - rxp162130
 *
 * @description This class is used to find all topological orders in the given graph.
 */
public class TopologicalSort extends Graph {

	TVertex[] tv; // vertices of graph
	private TVertex[] list;

	private long count; // no of topological paths.

	private long enumCount;// no of topological paths, when doing enumeration.

	/**
	 * @description Class used to store inDegree and outDegree of the given vertex.
	 *
	 */
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

	/**
	 * @description, Vertex iterator class used to return vertices whose inDegree is 0 and not seen yet.
	 *
	 */
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

	
	/**Method used to count all topological orders in the given graph.
	 * 
	 */
	public void countTopologicalSorts() {

		boolean isAllNodeVisited = true; // if all nodes of inDegree=0 is visited, then a topological order exist.

		for (Vertex v : this) {
			TVertex vertex = (TVertex) v;

			reduceInDegreeForAdj(vertex);

			vertex.visit();

			countTopologicalSorts();

			vertex.unVisit();
			resetInDegreeForAdj(vertex);

			isAllNodeVisited = false;

		}

		if (isAllNodeVisited) {
			count++;
		}

	}

	/**Method used to count and enumerate all topological orders in the given graph.
	 * 
	 */
	public void enumerateTopologicalSorts(int curSize) {

		boolean isAllNodeVisited = true;// if all nodes of inDegree=0 is visited, then a topological order exist.

		for (Vertex v : this) {
			TVertex vertex = (TVertex) v;

			reduceInDegreeForAdj(vertex);

			vertex.visit();

			list[curSize++] = vertex;
			enumerateTopologicalSorts(curSize);

			vertex.unVisit();
			curSize--;
			resetInDegreeForAdj(vertex);

			isAllNodeVisited = false;

		}

		if (isAllNodeVisited) {
			enumCount++;
			for (TVertex v : list) {
				System.out.print(v + " ");
			}
			System.out.println();
		}

	}

	/**Increment the inDegree of the given vertex by 1
	 * @param vertex - Given vertex.
	 */
	private void resetInDegreeForAdj(TVertex vertex) {
		Iterator<TEdge> iter1 = vertex.xadj.listIterator();
		while (iter1.hasNext()) {
			TEdge edge = iter1.next();
			TVertex ver = (TVertex) edge.toVertex();
			ver.inDegree++;
		}
	}

	/**Decrement the inDegree of the given vertex by 1
	 * @param vertex - Given vertex.
	 */
	private void reduceInDegreeForAdj(TVertex vertex) {
		Iterator<TEdge> iter = vertex.xadj.listIterator();
		while (iter.hasNext()) {
			TEdge edge = iter.next();
			TVertex ver = (TVertex) edge.toVertex();
			ver.inDegree--;

		}
	}

	/**Returns no of topological paths in the given graph.
	 * @return - no of topological paths
	 */
	public long getCountPath() {
		return count;
	}

	/**Returns no of topological paths in the given graph.
	 * @return - no of topological paths
	 */
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
