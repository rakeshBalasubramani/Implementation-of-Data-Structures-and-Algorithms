package cs6301.g38;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import cs6301.g38.DMSTGraph.DMSTVertex;


public class DMSTGraph extends Graph {

	private static int MAX = Integer.MAX_VALUE;
	
	static boolean isRevItr= false;
	
	 static boolean findZeroEdge = false;
	 
	 static int noOfComponents=0;

	public static class DMSTVertex extends Vertex {

		boolean disabled,seen;
		List<DMSTEdge> dmstAdj, dmstRevAdj;
		int cno;
		
		public DMSTVertex(Vertex u) {
			super(u);
			cno=-1;
			disabled = false;
			seen=false;
			dmstAdj = new LinkedList<>();
			dmstRevAdj = new LinkedList<>();
			
		}

		boolean isDisabled() {
			return disabled;
		}
		
		int getComponentNumber()
		{
			return cno;
		}

		void disable() {
			disabled = true;
		}

		@Override
		public Iterator<Edge> iterator() {
			if(isRevItr)
			{
				return new DMSTVertexIterator(this, dmstRevAdj.iterator());
	
			}
			else
			{
			return new DMSTVertexIterator(this,dmstAdj.iterator());
			}
		}

//		@Override
//		public Iterator<Edge> reverseIterator() {
//			return new DMSTVertexReverseIterator(this);
//		}
		
		
		class DMSTVertexIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			DMSTVertexIterator(DMSTVertex u,Iterator<DMSTEdge> it) {
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
				
				if(findZeroEdge)
				{
					while ((cur.isDisabled() && it.hasNext())|| (cur.tempWeight>0 && it.hasNext())) { 
						cur = it.next();
					}	
					
					ready =true;
					
					if(cur.tempWeight>0)
					{
						return false;
					}
					else
					{
						return !cur.isDisabled();
					}
					 
				}
				else
				{			
				
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

//		class DMSTVertexReverseIterator implements Iterator<Edge> {
//			DMSTEdge cur;
//			Iterator<DMSTEdge> revIt;
//			boolean ready;
//
//			DMSTVertexReverseIterator(DMSTVertex u,Iterator<DMSTEdge> revIt) {
//				this.revIt =revIt;
//				ready = false; 
//			
//			}
//
//			public boolean hasNext() {
//				if (ready) {
//					return true;
//				}
//				if (!revIt.hasNext()) {
//					return false;
//				}
//				cur = revIt.next();
//				
//				if(findZeroEdge)
//				{
//					while ((cur.isDisabled() && revIt.hasNext())||( cur.tempWeight>0 && revIt.hasNext())) { 
//						cur = revIt.next();
//					}	
//					
//					ready =true;
//					
//					if(cur.tempWeight>0)// for last edge
//					{
//						return false;
//					}
//					else
//					{
//						return !cur.isDisabled();
//					}
//					 
//				}
//				else
//				{
//					while (cur.isDisabled() && revIt.hasNext()) { 
//						cur = revIt.next();
//					}
//					
//					ready = true;
//					return !cur.isDisabled();
//				}
//				
//				
//			}
//
//			public Edge next() {
//				if (!ready) {
//					if (!hasNext()) {
//						throw new java.util.NoSuchElementException();
//					}
//				}
//				ready = false;
//				return cur;
//			}
//
//			public void remove() {
//				throw new java.lang.UnsupportedOperationException();
//			}
//			
////		}
//		

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
		isRevItr=true;	
		for (DMSTVertex dv : dmst) {
			int minWeight = MAX;

			if (dv == null) {
				break;
			}
			if (dv.getName() == start.getName() || dv.disabled) {
				continue;
			}

		
		
		for (Edge de : dv) {
			DMSTEdge dmstEdge = ((DMSTEdge)de);
				if (dmstEdge.tempWeight<minWeight){
					minWeight=dmstEdge.tempWeight;
				}
					
			}
		

			shortestEdge.add(minWeight);
	}	
		isRevItr=false;
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
			
		
			isRevItr=true;
		for (Edge de : dv) {
				DMSTEdge dmstEdge1 = ((DMSTEdge)de);
				int newWeight = dmstEdge1.tempWeight - min;
				dmstEdge1.tempWeight=newWeight;
				
				DMSTVertex du = (DMSTVertex) dmstEdge1.otherEnd(dv);
				isRevItr=false;
				for (Edge adj : du) {
					DMSTEdge dmstEdge = ((DMSTEdge)adj);
					if ((dmstEdge.to.getName() == dv.getName()) && (dmstEdge.getWeight() == dmstEdge1.getWeight())) {
						
						dmstEdge.tempWeight=newWeight;
						break;
					}
				}

			}
		}
		
		

		
		CC cc=new CC();
		noOfComponents=cc.findCC(dmst);
		System.out.println("No of components:"+ noOfComponents);
		//shrinkComponents(start);
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

			for (Edge e : dv) {
				DMSTEdge dmstEdge=((DMSTEdge)e);
				
					System.out.println(dmstEdge.fromVertex()+" "+ dmstEdge.toVertex()+" "+dmstEdge.tempWeight);
				
					
			}
			
		}
		printZeroEdges();
	}

	private void printZeroEdges() {
	
		System.out.println("Zero edges...");
		// how to set the boolean in the iterator from caller function?
		
		
		
		for (DMSTVertex dv : dmst) {

			if (dv == null) {
				break;
			}

			if (dv.disabled) {
				continue;
			}
  
		
			//isRevItr=true;
			findZeroEdge=true;
		for (Edge e : dv) {
			DMSTEdge dmstEdge=((DMSTEdge)e);
				
					System.out.println(dmstEdge.fromVertex()+" "+ dmstEdge.toVertex()+" "+dmstEdge.tempWeight);
				
					
			}
			
		}
		//isRevItr=false;
		findZeroEdge=false;
		
	}

	public void shrinkComponents(Graph g,Vertex start) {
	
		GraphHash<List<Vertex>,Edge> gh=new GraphHash<List<Vertex>,Edge>(g);
//		List<DMSTVertex> sameComponent= new ArrayList<DMSTVertex>();
		Edge[] mstEdges= new Edge[noOfComponents-1];
		Edge minEdge;
		
		for(DMSTVertex dv:dmst)
		{
			
			if(dv==null)
			{
				break;
			}
			
			if (dv.getName() == start.getName() || dv.disabled) {
				continue;
			}
			
			isRevItr=true;
			for (Edge de : dv) {
				
			//	DMSTEdge dmstEdge = ((DMSTEdge)de);
				
				minEdge=de;
				
				
				DMSTVertex du=(DMSTVertex)de.otherEnd(dv);
				
				
				for(Map.Entry<Vertex,List<Vertex>> map:gh.vertexMap.entrySet())
				{
					
				DMSTVertex d=(DMSTVertex)map.getKey();
				
				if(d.getComponentNumber()==du.getComponentNumber())
				{
					List<Vertex> list=map.getValue();
					list.add((Vertex)dv);
					gh.vertexMap.remove((Vertex)du);
					gh.vertexMap.put((Vertex)du,list);
				}
					
				else
				{
					List<Vertex> component= new ArrayList<Vertex>();
					component.add((Vertex)dv);
					gh.vertexMap.put((Vertex)dv,component);
				}
					
					
				}
				
					
				//put root vertex and list of vertices into hashMap based on component number
				if(du.getComponentNumber()!=dv.getComponentNumber())
				{
					if (de.getWeight()<=minEdge.getWeight()){
						Edge ee=new Edge(de.to,de.from,de.getWeight());
						minEdge=ee;
						mstEdges[dv.getComponentNumber()-1]=minEdge;
						
						
					}
				}
				else
				{
					
				}
					
						
				}
			
			
		}
		
		
		
	}


}
