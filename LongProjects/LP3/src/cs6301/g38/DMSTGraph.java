package cs6301.g38;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class DMSTGraph extends Graph {

	static int MAX = Integer.MAX_VALUE;

	public static class DMSTVertex extends Vertex {

		boolean disabled;
		List<DMSTEdge> dmstAdj, dmstRevAdj;
		int cno;
		
		public DMSTVertex(Vertex u) {
			super(u);
			cno=-1;
			disabled = false;
			dmstAdj = new LinkedList<>();
			dmstRevAdj = new LinkedList<>();
			
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
			boolean ready,isZero;

			DMSTVertexReverseIterator(DMSTVertex u) {
				this.revIt = u.dmstRevAdj.iterator();
				ready = false; 
				isZero=false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!revIt.hasNext()) {
					return false;
				}
				cur = revIt.next();
				
				if(isZero())
				{
					while (cur.isDisabled() && revIt.hasNext()|| cur.tempWeight!=0 && revIt.hasNext()) { 
						cur = revIt.next();
					}	
				}
				else
				{
					while (cur.isDisabled() && revIt.hasNext()) { 
						cur = revIt.next();
					}
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
			
			public void setZero()
			{
				isZero=true;
			}
			
			public boolean isZero()
			{
				return isZero;
			}

		}
		

	}

	static class DMSTEdge extends Edge {
		boolean disabled;
		int tempWeight;

		DMSTEdge(DMSTVertex from, DMSTVertex to, int weight) {
			super(from, to, weight);
			tempWeight=weight;
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
			int minWeight = MAX;

			if (dv == null) {
				break;
			}
			if (dv.getName() == start.getName() || dv.disabled) {
				continue;
			}

			for (DMSTEdge de : dv.dmstRevAdj) {
				if (de.tempWeight<minWeight){
					minWeight=de.tempWeight;
				}

			}

			shortestEdge.add(minWeight);
		}
			return shortestEdge;
	
}
	void updateEdgeWeights(Graph g, Vertex start, List<Integer> minWeights) {

		int count = 0;

		for (DMSTVertex dv : dmst) {

			if (dv == null) {
				break;
			}

			if (dv.getName() == start.getName() || dv.disabled) {
				continue;
			}

			int min = minWeights.get(count);
			count++;

			for (DMSTEdge de : dv.dmstRevAdj) {
				
				int newWeight = de.tempWeight - min;
				de.tempWeight=newWeight;
				
				DMSTVertex du = (DMSTVertex) de.otherEnd(dv);

				for (DMSTEdge adj : du.dmstAdj) {
					if ((adj.to.getName() == dv.getName()) && (adj.getWeight() == de.getWeight())) {
						
						adj.tempWeight=newWeight;

					}
				}

			}
		}


	}

//	private void update(GraphHash<Edge, DMSTEdge> gh) {
//
//		for (Map.Entry<Edge, DMSTEdge> map : gh.edgeMap.entrySet()) {
//			Edge e = map.getKey();
//			DMSTEdge revEdge = map.getValue();
//
//			DMSTEdge adj = (DMSTEdge) e;
//			adj.disabled = true;
//			DMSTVertex dFrom = (DMSTVertex) revEdge.from;
//			DMSTVertex dTo = (DMSTVertex) revEdge.to;
//			dFrom.dmstRevAdj.add(new DMSTEdge(dFrom, dTo, revEdge.getWeight()));
//
//		}
//
//	}

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
				{
					System.out.println(e.fromVertex()+" "+ e.toVertex()+" "+e.tempWeight);
				}
					
			}
			
		}
		printZeroEdges();
	}

	private void printZeroEdges() {
	
		// how to set the boolean in the iterator from caller function?
		for (DMSTVertex dv : dmst) {

			if (dv == null) {
				break;
			}

			if (dv.disabled) {
				continue;
			}
  
			//DMSTVertexReverseIterator<DMSTEdge> zeroEdges= new DMSTVertexReverseIterator<DMSTEdge>(dv.dmstRevAdj);
			
			for (DMSTEdge e : dv.dmstAdj) {
				if (!e.disabled)
				{
					System.out.println(e.fromVertex()+" "+ e.toVertex()+" "+e.tempWeight);
				}
					
			}
			
		}
		
		
	}


}
