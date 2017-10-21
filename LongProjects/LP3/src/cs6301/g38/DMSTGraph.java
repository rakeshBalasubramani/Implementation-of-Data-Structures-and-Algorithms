package cs6301.g38;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DMSTGraph extends Graph {

	static int MAX = 100000;

	public static class DMSTVertex extends Vertex {

		boolean disabled;
		List<DMSTEdge> dmstAdj, dmstRevAdj,dmstZeroAdj,dmstZeroRevAdj;
		int cno;
		

		public DMSTVertex(Vertex u) {
			super(u);
			cno=-1;
			disabled = false;
			dmstAdj = new LinkedList<>();
			dmstRevAdj = new LinkedList<>();
			dmstZeroAdj= new LinkedList<>();
			dmstZeroRevAdj= new LinkedList<>();
			
		}

		boolean isDisabled() {
			return disabled;
		}

		void disable() {
			disabled = true;
		}

		@Override
		public Iterator<Edge> iterator() {
			return new DMSTVertexIterator(this);
		}

		@Override
		public Iterator<Edge> reverseIterator() {
			return new DMSTVertexReverseIterator(this);
		}

		class DMSTVertexIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			DMSTVertexIterator(DMSTVertex u) {
				this.it = u.dmstAdj.iterator();
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

		class DMSTVertexReverseIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> revIt;
			boolean ready;

			DMSTVertexReverseIterator(DMSTVertex u) {
				this.revIt = u.dmstRevAdj.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!revIt.hasNext()) {
					return false;
				}
				cur = revIt.next();
				while (cur.isDisabled() && revIt.hasNext()) {
					cur = revIt.next();
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

	}

	static class DMSTEdge extends Edge {
		boolean disabled;

		DMSTEdge(DMSTVertex from, DMSTVertex to, int weight) {
			super(from, to, weight);
			disabled = false;
		}

		boolean isDisabled() {
			DMSTVertex dfrom = (DMSTVertex) from;
			DMSTVertex dto = (DMSTVertex) to;
			return disabled || dfrom.isDisabled() || dto.isDisabled();
		}
	}

	DMSTVertex dmst[];

	public DMSTGraph(Graph g) {
		super(g);
		dmst = new DMSTVertex[2 * g.size()];
		for (Vertex v : g) {
			dmst[v.getName()] = new DMSTVertex(v);
		}

		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				DMSTVertex v1 = getVertex(u);
				DMSTVertex v2 = getVertex(v);
				v1.dmstAdj.add(new DMSTEdge(v1, v2, e.weight));
				v2.dmstRevAdj.add(new DMSTEdge(v2, v1, e.weight));
			}
		}
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new DMSTIterator(this);
	}

	class DMSTIterator implements Iterator<Vertex> {
		Iterator<DMSTVertex> it;
		DMSTVertex dmcur;

		DMSTIterator(DMSTGraph dm) {
			this.it = new ArrayIterator<DMSTVertex>(dm.dmst, 0, dm.size() - 1); // Iterate
																				// over
																				// existing
																				// elements
																				// only
		}

		public boolean hasNext() {
			if (!it.hasNext()) {
				return false;
			}
			dmcur = it.next();
			while (dmcur.isDisabled() && it.hasNext()) {
				dmcur = it.next();
			}
			return !dmcur.isDisabled();
		}

		public Vertex next() {
			return dmcur;
		}

		public void remove() {
		}

	}

	@Override
	public Vertex getVertex(int n) {
		return dmst[n - 1];
	}

	DMSTVertex getVertex(Vertex u) {
		return Vertex.getVertex(dmst, u);
	}

	void disable(int i) {
		DMSTVertex u = (DMSTVertex) getVertex(i);
		u.disable();
	}

	List<Integer> findShortestIncomingEdge(Vertex start) {
		List<Integer> shortestEdge = new ArrayList<Integer>();

		for (DMSTVertex dv : dmst) {
			int weight = MAX;

			if (dv == null) {
				break;
			}
			if (dv.getName() == start.getName() || dv.disabled) {
				continue;
			}

			for (DMSTEdge de : dv.dmstRevAdj) {
				if (de.getWeight() < weight) {
					weight = de.getWeight();
				}

			}

			shortestEdge.add(weight);
		}
		return shortestEdge;
	}

	void updateEdgeWeights(Graph g, Vertex start, List<Integer> minweights) {

		GraphHash<Edge, DMSTEdge> gh = new GraphHash<Edge, DMSTEdge>(g);
		int count = 0;

		for (DMSTVertex dv : dmst) {

			if (dv == null) {
				break;
			}

			if (dv.getName() == start.getName() || dv.disabled) {
				continue;
			}

			int min = minweights.get(count);
			count++;

			for (DMSTEdge de : dv.dmstRevAdj) {
				int newWeight = de.getWeight() - min;
				DMSTVertex du = (DMSTVertex) de.otherEnd(dv);

				for (DMSTEdge adj : du.dmstAdj) {
					if ((adj.to.getName() == dv.getName()) && (adj.getWeight() == de.getWeight())) {
						gh.edgeMap.put(adj, new DMSTEdge(dv, du, newWeight));
					}
				}

				du.dmstAdj.add(new DMSTEdge(du, dv, newWeight));
				de.disabled = true;

			}
		}

		update(gh);

	}

	private void update(GraphHash<Edge, DMSTEdge> gh) {

		for (Map.Entry<Edge, DMSTEdge> map : gh.edgeMap.entrySet()) {
			Edge e = map.getKey();
			DMSTEdge revEdge = map.getValue();

			DMSTEdge adj = (DMSTEdge) e;
			adj.disabled = true;
			DMSTVertex dFrom = (DMSTVertex) revEdge.from;
			DMSTVertex dTo = (DMSTVertex) revEdge.to;
			dFrom.dmstRevAdj.add(new DMSTEdge(dFrom, dTo, revEdge.getWeight()));

		}

	}

	public void printGraph() {

		System.out.println("reduced graph:");
		for (DMSTVertex dv : dmst) {

			if (dv == null) {
				break;
			}

			if (dv.disabled) {
				continue;
			}

			for (DMSTEdge e : dv.dmstAdj) {
				if (!e.disabled)
					System.out.println(e.stringWithSpaces());
			}
		}
	}

	void createZeroEdgeGraph() {
		
		System.out.println("Zero edge graph:");
		for(DMSTVertex du:dmst)
		{
			if (du == null) {
				break;
			}

			if (du.disabled) {
				continue;
			}

			for(DMSTEdge dme:du.dmstAdj)
			{
				if((!dme.disabled)&&(dme.getWeight()==0))
				{
					System.out.println("000 edges");
					dme.stringWithSpaces();
					DMSTVertex dv=(DMSTVertex)dme.otherEnd(du);
					du.dmstZeroAdj.add(dme);
					dv.dmstZeroRevAdj.add(new DMSTEdge(dv,du,dme.getWeight()));
				}
			}
		}
		

	}
}
