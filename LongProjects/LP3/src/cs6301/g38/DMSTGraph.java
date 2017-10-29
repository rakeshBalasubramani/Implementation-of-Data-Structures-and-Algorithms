package cs6301.g38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import cs6301.g38.DMSTGraph.DMSTVertex;

public class DMSTGraph extends Graph {

	private static int MAX = Integer.MAX_VALUE;

	static boolean isRevItr = false;

	static boolean findZeroEdge = false;

	static int noOfComponents = 0;

	static int noOfVertices = 0;

	private List<Edge> dmstEdges;

	private Vertex source;

	private Graph g;

	private DMSTVertex dmst[];

	private DMSTEdge[][] minEdgesBetweenComponents;

	private DMSTEdge minEdge;

	GraphHash<List<Vertex>, Edge> gh = new GraphHash<List<Vertex>, Edge>(g);

	public static class DMSTVertex extends Vertex {

		boolean disabled, seen;
		List<DMSTEdge> dmstAdj, dmstRevAdj;
		int cno;

		public DMSTVertex(Vertex u) {
			super(u);
			cno = -1;
			disabled = false;
			seen = false;
			dmstAdj = new LinkedList<>();
			dmstRevAdj = new LinkedList<>();

		}

		boolean isDisabled() {
			return disabled;
		}

		int getComponentNumber() {
			return cno;
		}

		void disable() {
			disabled = true;
		}

		@Override
		public Iterator<Edge> iterator() {
			if (isRevItr) {
				return new DMSTVertexIterator(this, dmstRevAdj.iterator());

			} else {
				return new DMSTVertexIterator(this, dmstAdj.iterator());
			}
		}

		// @Override
		// public Iterator<Edge> reverseIterator() {
		// return new DMSTVertexReverseIterator(this);
		// }

		class DMSTVertexIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			DMSTVertexIterator(DMSTVertex u, Iterator<DMSTEdge> it) {
				this.it = it;
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

				if (findZeroEdge) {
					while ((cur.isDisabled() && it.hasNext()) || (cur.tempWeight > 0 && it.hasNext())) {
						cur = it.next();
					}

					ready = true;

					if (cur.tempWeight > 0) {
						return false;
					} else {
						return !cur.isDisabled();
					}

				} else {

					while (cur.isDisabled() && it.hasNext()) {
						cur = it.next();
					}

					ready = true;
					return !cur.isDisabled();
				}

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
		int tempWeight;

		DMSTEdge(DMSTVertex from, DMSTVertex to, int weight) {
			super(from, to, weight);
			tempWeight = weight;
			disabled = false;
		}

		boolean isDisabled() {
			DMSTVertex dfrom = (DMSTVertex) from;
			DMSTVertex dto = (DMSTVertex) to;
			return disabled || dfrom.isDisabled() || dto.isDisabled();
		}
	}

	public DMSTGraph(Graph g, Vertex start, List<Edge> dmstEdges) {
		super(g);
		this.dmstEdges = dmstEdges;
		this.source = start;
		this.g = g;
		dmst = new DMSTVertex[2 * g.size()];
		for (Vertex v : g) {
			dmst[v.getName()] = new DMSTVertex(v);
			noOfVertices++;
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
			this.it = new ArrayIterator<DMSTVertex>(dm.dmst, 0, noOfVertices-1); // Iterate
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

	List<Integer> findMinWeightIncomingEdge() {
		List<Integer> minWeighEdge = new ArrayList<Integer>();
		isRevItr = true;
		for (Vertex dv : this) {
			int minWeight = MAX;

			for (Edge de : ((DMSTVertex) dv)) {
				DMSTEdge dmstEdge = ((DMSTEdge) de);
				if (dmstEdge.tempWeight < minWeight) {
					minWeight = dmstEdge.tempWeight;
				}

			}

			minWeighEdge.add(minWeight);
		}
		isRevItr = false;
		return minWeighEdge;

	}

	void updateEdgeWeights(List<Integer> minWeights) {

		int index = 0;
		int min;
		int newWeight;
		for (Vertex dv : this) {
			min = minWeights.get(index);
			index++;

			isRevItr = true;
			for (Edge de : ((DMSTVertex) dv)) {
				DMSTEdge dmstEdge1 = ((DMSTEdge) de);
				newWeight = dmstEdge1.tempWeight - min;
				dmstEdge1.tempWeight = newWeight;

				DMSTVertex du = (DMSTVertex) dmstEdge1.otherEnd(dv); // update
																		// the
																		// other
																		// end
																		// Vertex
																		// as
																		// well.
				isRevItr = false;
				for (Edge adj : du) {
					DMSTEdge dmstEdge = ((DMSTEdge) adj);
					if ((dmstEdge.to.getName() == dv.getName()) && (dmstEdge.getWeight() == dmstEdge1.getWeight())) {

						dmstEdge.tempWeight = newWeight;
						break;
					}
				}

			}
		}

	}

	public void printGraph() {

		System.out.println("reduced graph:");
		for (Vertex dv : this) {

			for (Edge e : ((DMSTVertex) dv)) {
				DMSTEdge dmstEdge = ((DMSTEdge) e);

				System.out.println(dmstEdge.fromVertex() + " " + dmstEdge.toVertex() + " " + dmstEdge.tempWeight);

			}

		}
		printZeroEdges();
	}

	private void printZeroEdges() {

		System.out.println("Zero edges...");
		// how to set the boolean in the iterator from caller function?

		for (Vertex dv : this) {

			// isRevItr=true;
			findZeroEdge = true;
			for (Edge e : ((DMSTVertex) dv)) {
				DMSTEdge dmstEdge = ((DMSTEdge) e);

				System.out.println(dmstEdge.fromVertex() + " " + dmstEdge.toVertex() + " " + dmstEdge.tempWeight);

			}

		}
		// isRevItr=false;
		findZeroEdge = false;

	}

	private void storeComponentVertices() {

		HashMap<Integer, List<DMSTVertex>> componentVertices = new HashMap<Integer, List<DMSTVertex>>();

		// store same component vertices to temp hashMap
		for (Vertex v : this) {

			DMSTVertex dv = ((DMSTVertex) v);

			if (componentVertices.containsKey(dv.getComponentNumber())) {
				List<DMSTVertex> vertexComponents = componentVertices.get(dv.getComponentNumber());
				vertexComponents.add(dv);

				componentVertices.put(dv.getComponentNumber(), vertexComponents);
			}

			else {
				List<DMSTVertex> vertices = new ArrayList<DMSTVertex>();
				vertices.add(dv);
				componentVertices.put(dv.getComponentNumber(), vertices);

			}

		}

		// print same component vertices

		for (Map.Entry<Integer, List<DMSTVertex>> tempMap : componentVertices.entrySet()) {
			int cno = tempMap.getKey();
			List<DMSTVertex> sameComponentVertices = tempMap.getValue();

			System.out.print("C" + cno + ":");

			for (Vertex v : sameComponentVertices) {
				System.out.print(v + " ");
			}
			System.out.println();
		}

		shrinkComponents(componentVertices);

	}

	private void shrinkComponents(HashMap<Integer, List<DMSTVertex>> componentVertices) {

		
		int totalVertices=noOfVertices; // track newly created vertices
		
		DMSTVertex[] newVertex= new DMSTVertex[noOfComponents];
		DMSTVertex[] compVertex = new DMSTVertex[noOfComponents];
		
		// assign same component vertices to hashMap
		for (Map.Entry<Integer, List<DMSTVertex>> tempMap : componentVertices.entrySet()) {
			int compNo = tempMap.getKey();
			List<DMSTVertex> sameCompVertices = tempMap.getValue();

			// if the no of vertices is greater than 1 in the component
			if (sameCompVertices.size() > 1) {

				
				Vertex shrinkVertex = new Vertex(totalVertices++);

				DMSTVertex componentVertex = new DMSTVertex(shrinkVertex);
				componentVertex.cno=compNo;
				newVertex[compNo-1]=componentVertex;
				
				//gh.vertexMap.put(componentVertex, sameCompVertices);
			}
		}		
		
		
		for(DMSTEdge[] row: minEdgesBetweenComponents)
		{
			for(DMSTEdge column: row)
			{
				if(column==null)
				{
					continue;
				}
				
				DMSTVertex from=(DMSTVertex)column.fromVertex();
				DMSTVertex to=(DMSTVertex)column.toVertex();
				
				List<DMSTVertex> cfVertices=componentVertices.get(from.getComponentNumber());
				List<DMSTVertex> ctVertices= componentVertices.get(to.getComponentNumber());
				
				// for from vertex
				if(cfVertices.size()>1)
				{
					
					// vertex for each Component
					compVertex[from.getComponentNumber()-1]=newVertex[from.getComponentNumber()-1];
					
					//changing the from vertex in the matrix
					column.from=newVertex[from.getComponentNumber()-1];
					
				
					if(ctVertices.size()>1)
					{
						to=newVertex[to.getComponentNumber()-1];
					}
					
					//adding edges to adj and revadj list
					compVertex[((DMSTVertex)column.from).getComponentNumber()-1].dmstRevAdj.add(new DMSTEdge(compVertex[((DMSTVertex)column.from).getComponentNumber()-1],to,column.tempWeight));
					
					to.dmstAdj.add(new DMSTEdge(to,compVertex[((DMSTVertex)column.from).getComponentNumber()-1],column.tempWeight));

					
				
					//disable vertices of the same component
					for(DMSTVertex disf: cfVertices)
					{
						disf.disable();
					}
				}
				
				else 
				{
					compVertex[from.getComponentNumber()-1]=cfVertices.get(0);
				}
				
				
				// for to vertex
				if(ctVertices.size()>1)
				{
					// Vertex for each component
					compVertex[to.getComponentNumber()-1]=newVertex[to.getComponentNumber()-1];
					
					//changing to vertex in matrix
					column.to=newVertex[to.getComponentNumber()-1];
					
					
					compVertex[((DMSTVertex)column.to).getComponentNumber()-1].dmstAdj.add(new DMSTEdge(compVertex[((DMSTVertex)column.to).getComponentNumber()-1],(DMSTVertex)column.from,column.tempWeight));
					((DMSTVertex)column.from).dmstRevAdj.add(new DMSTEdge(((DMSTVertex)column.from),((DMSTVertex)column.to),column.tempWeight));
					
					//disable vertices of the same component
					for(DMSTVertex dist: ctVertices)
					{
						dist.disable();
					}
				
				
				}
				else
				{
					compVertex[to.getComponentNumber()-1]=ctVertices.get(0);
				}
			}
		}
		

		for(DMSTVertex ver: newVertex)
		{
			if(ver!=null)
			dmst[noOfVertices++]=ver;
		}
		

	}

//	private void updateMinimumWeightMatrix(int compNo, DMSTVertex componentVertex,DMSTEdge cEdge) {
//		
//		DMSTEdge edge= new DMSTEdge((DMSTVertex)cEdge.toVertex(),componentVertex,cEdge.tempWeight);
//		
//		cEdge.from=edge.from;
//		cEdge.to=edge.to;
//		cEdge.weight=edge.getWeight();
//		
//}
//	private void createNewEdges(DMSTVertex fromVertex, DMSTVertex toVertex, int weight) {
//
//		fromVertex.dmstAdj.add(new DMSTEdge(fromVertex, toVertex, weight));
//
//		toVertex.dmstRevAdj.add(new DMSTEdge(toVertex, fromVertex, weight));
//
//	}

	private void findMinimumEdgeBetweenComponents(int noOfComponents) {

		DMSTEdge[][] minEdgeComponent= new DMSTEdge[noOfComponents][noOfComponents];
		minEdgesBetweenComponents =minEdgeComponent;

		isRevItr = true;
		for (Vertex v : this) {
			DMSTVertex dv = ((DMSTVertex) v);

			if (dv.getName() == source.getName()) {
				minEdgesBetweenComponents[dv.getComponentNumber() - 1][dv.getComponentNumber() - 1] = null; // for
				// root
				// node
			}

			for (Edge e : dv) {

				DMSTVertex du = (DMSTVertex) e.otherEnd(dv);

				DMSTEdge edge = new DMSTEdge((DMSTVertex)e.fromVertex(),(DMSTVertex)e.toVertex(),((DMSTEdge)e).tempWeight);
				DMSTEdge tempEdge = edge;
				
				if (du.getComponentNumber() == dv.getComponentNumber()) {
					minEdgesBetweenComponents[dv.getComponentNumber() - 1][dv.getComponentNumber() - 1] = null; // for
					// edges
					// to
					// same
					// component
				}

				else {
					if (minEdgesBetweenComponents[du.getComponentNumber() - 1][dv.getComponentNumber() - 1] != null) {
						minEdge = minEdgesBetweenComponents[du.getComponentNumber() - 1][dv.getComponentNumber() - 1];

						if (tempEdge.tempWeight <= minEdge.tempWeight) {
							minEdge = tempEdge;
							minEdgesBetweenComponents[du.getComponentNumber() - 1][dv.getComponentNumber()
									- 1] = minEdge;
						}
					}

					else {
						minEdge = tempEdge;
						minEdgesBetweenComponents[du.getComponentNumber() - 1][dv.getComponentNumber() - 1] = minEdge;
					}

				}
			}

		}

		isRevItr = false;
		// printing 2d array

		printMiniMumEdgeBetweenComponents();
		

	}

	private void printMiniMumEdgeBetweenComponents() {
		
		for (DMSTEdge[] minimum : minEdgesBetweenComponents) {
			for (DMSTEdge min : minimum) {
				if (min == null) {
					System.out.print("-1" + " ");
				} else {
					System.out.print(min.tempWeight + " ");
					// System.out.println(min.stringWithSpaces());
				}
			}

			System.out.println();
		}
		
		System.out.println();
	}

	public void findMST() {
		List<Integer> minWeights = findMinWeightIncomingEdge();
		updateEdgeWeights(minWeights);
		printGraph();

		
		//System.out.println("Total number of vertices:" + noOfVertices);
		CC cc = new CC();
		noOfComponents = cc.findCC(dmst);
		System.out.println("No of components:" + noOfComponents);

		if (noOfComponents==1)
		{
			
			System.out.println("Done");

		
			printGraph();
			return;
		}
		
		
		findMinimumEdgeBetweenComponents(noOfComponents);

		storeComponentVertices();

		printGraph();
		
		resetSeenOldVertices();
		findMST();

	}

	private void resetSeenOldVertices() {
	
		System.out.println("RESET SEEN");
		for(Vertex v : this)
		{
			System.out.println(v);
			((DMSTVertex)v).seen=false;
		}
		System.out.println("---------");
		
	}

}
