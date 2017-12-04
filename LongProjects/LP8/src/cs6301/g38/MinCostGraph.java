package cs6301.g38;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Class used to implement CycleCancellation and Successive Shortest Paths algorithm to calculate Mincost-MaxFlow.
 */
public class MinCostGraph extends Graph {
	private FVertex[] fVertices; // vertices of graph
	private FVertex source;
	private FVertex terminal;
	private int currentFlow;
	private int currentCost;
	private FEdge[] reverseEdge, forwardEdge;
	private BFSFlowGraph bfsHelper;

	static class FVertex extends Vertex {

		public static final int INFINITY = Integer.MAX_VALUE;

		List<FEdge> adjEdge, revEdge;

		boolean seen;
		
		int distance = INFINITY;
		FEdge parentEdge;
		int count; // used for BellmanFord algo iteration count.

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
		
		public boolean containsTightEdge(FEdge fe) {
			boolean isExist = false;
			for (int i = 0; i < tightEdgeSize; i++) {
				if (tightEdges[i].equals(fe)) {
					isExist = true;
					break;
				}
			}
			return isExist;
		}
		FEdge[] tightEdges;
		int tightEdgeSize = 0;
		public void addTightEdge(FEdge fe) {
			tightEdges[tightEdgeSize++] = fe;
			fe.isTightEdge = true;
		}

		public void clearTightEdges() {
			for (FEdge e : tightEdges) {
				if (e != null)
					e.isTightEdge = false;
			}
			tightEdgeSize = 0;

		}

	}

	static class FEdge extends Edge {

		int capacity = 0;
		int flow = 0;
		FEdge reverseEdge;
		int cost;
		boolean isTightEdge;
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

		FEdge(FVertex from, FVertex to, int weight, int capacity, int cost,
				int name) {
			super(from, to, weight, name);
			flow = 0;
			this.capacity = capacity;
			this.cost = cost;
		}

		boolean isFeasibleFlow() {
			return (flow < capacity);
		}

		

	}

	public MinCostGraph(Graph g, Vertex source, Vertex terminal,
			HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
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
						cost.get(e), e.name);
				forwardEdge[e.name - 1] = frwdE;
				x1.adjEdge.add(frwdE);
				FEdge revE = new FEdge(x2, x1, e.weight, 0, -1 * cost.get(e),
						e.name);
				x2.revEdge.add(revE);
				frwdE.setReverseEdge(revE);
				revE.setReverseEdge(frwdE);
				reverseEdge[e.name - 1] = revE;

			}
		}

		for (FVertex u : fVertices) {
			u.adjEdge.addAll(u.revEdge);
		}
		bfsHelper = new BFSFlowGraph(this, source);
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

				if (temp_flow > -1) {
					currentCost += fe.cost * temp_flow;
					fe.flow += temp_flow;
					fe.getReverseEdge().flow -= temp_flow;
					return temp_flow;
				}
			}
		}

		return -1;
	}

	private void dinitzFlow(int flowNeeded) {
		int flow;
		bfsHelper.bfs();
		boolean isDone = false;
		// boolean feasible = false;

		while (bfsHelper.distance(terminal) != BFSFlowGraph.INFINITY) {
			flow = pushFlow(getVertex(source), flowNeeded - currentFlow);
			while (flow > -1) {

				this.currentFlow += flow;
				if (flowNeeded > currentFlow) {
					flow = pushFlow(getVertex(source), flowNeeded - currentFlow);
				}
				// else if (flowNeeded < currentFlow) {
				// isDone = true;
				// feasible = false;
				// }
				else {
					isDone = true;
					break;

					// feasible = true;

				}

			}

			if (isDone) {
				break;
			}
			bfsHelper.reinitialize(source);
			bfsHelper.bfs();

		}

		// return feasible;
	}

	
	private boolean hasNegativeCycle()
	{
		boolean noChange=false;
		for (FVertex fv : fVertices) {
			fv.distance = FVertex.INFINITY;
			fv.parentEdge = null;
			fv.seen=false;
		}
		boolean isVertexFound = false;

		for(FVertex fv : fVertices)
		{
			fv.seen=true;
			
			for(Edge e:fv)
			{
				fv.distance=0;
				isVertexFound=true;
				break;
			}
			if(isVertexFound)
			{
				break;
			}
		}		
		
		
		noChange = runBellman(noChange);
		
		if(noChange)
		{
			for(FVertex fv:fVertices)
			{

				if(!fv.seen)
				{
					fv.distance=0;
					fv.seen=true;
					noChange = runBellman(noChange);
					if(!noChange)
					{
						return true;
					}
				}
			}
		
			
			
		return false;
		}
		else
		{
			return true;
		}
			
	}

	private boolean runBellman(boolean noChange) {
		for(int k=0; k<fVertices.length; k++ )
		{
			noChange=true;
			
			for(FEdge frwdEdge : forwardEdge)
			{
				if(frwdEdge.isFeasibleFlow())
				{
					FVertex u = getVertex(frwdEdge.from);
					FVertex v = getVertex(frwdEdge.to);
					
					if(u.distance!=FVertex.INFINITY && v.distance > u.distance + frwdEdge.cost)
					{
						v.distance = u.distance + frwdEdge.cost;
						v.parentEdge = frwdEdge;
						u.seen=true;
						v.seen=true;
						noChange = false;
					}
				}
			}
			
			for(FEdge revEdge:reverseEdge)
			{
				if(revEdge.isFeasibleFlow())
				{
					FVertex u = getVertex(revEdge.from);
					FVertex v = getVertex(revEdge.to);
					
					if(u.distance!=FVertex.INFINITY && v.distance > u.distance + revEdge.cost)
					{
						v.distance = u.distance + revEdge.cost;
						v.parentEdge = revEdge;
						u.seen=true;
						v.seen=true;
						noChange = false;
					}
				}
			}
				
			
		}
		return noChange;
	}

	
	
	
	public int cycleCancellation(int flow) {

		dinitzFlow(flow);
		
		HashSet<FEdge> edgeSet = new HashSet<FEdge>();
		while (hasNegativeCycle()) {

		
			 cancelCycle(edgeSet);
		
		}

		return currentCost;
	}

	private FVertex cancelCycle( Set<FEdge> edgeSet) {
		FVertex cycleVertex = null;
		int cycleCost=0;

		for(FVertex fv : fVertices)
		{
			edgeSet.clear();
			 cycleCost=0;
			if(fv.parentEdge!=null)
			{
			FEdge parentEdge = fv.parentEdge;
			fv.seen=true;
			FVertex parent = getVertex(parentEdge.otherEnd(fv));
			while(!edgeSet.contains(parentEdge)) {
				edgeSet.add(parentEdge);
				parentEdge = parent.parentEdge;
				parent.seen=true;
				parent = getVertex(parentEdge.otherEnd(parent));
				

			}
			FEdge currentEdge = parentEdge;
			cycleVertex = parent;
			do
			{
				cycleCost+=currentEdge.cost;
				parent = getVertex(currentEdge.otherEnd(parent));
				currentEdge = parent.parentEdge;

				
			}
			while(!parentEdge.equals(currentEdge));
			
			
			if(cycleCost<0)
			{
			int	minFlowEdge = identifyMinFlow(cycleVertex);
				augment(minFlowEdge, cycleVertex);

			}
			else
			{
				cycleVertex=null;
			}
			}
			
		}
		
		
		return cycleVertex;
	}

	private void augment(int minFlowEdge, FVertex cycleVertex) {
		FEdge parentEdge = cycleVertex.parentEdge;
		FEdge cycleEdge = parentEdge;

		FVertex parent = getVertex(parentEdge.otherEnd(cycleVertex));
		do {

			parentEdge.flow += minFlowEdge;
			parentEdge.getReverseEdge().flow -= minFlowEdge;
			currentCost += parentEdge.cost * minFlowEdge;
			parentEdge = parent.parentEdge;
			parent = getVertex(parentEdge.otherEnd(parent));

		}while (!parentEdge.equals(cycleEdge));

	}

	private int identifyMinFlow(FVertex cycleVertex) {
		
		FEdge parentEdge = cycleVertex.parentEdge;
		FEdge cycleEdge = parentEdge;
		FVertex parent = getVertex(parentEdge.otherEnd(cycleVertex));
		int minFlow = Integer.MAX_VALUE;
		do {
			minFlow = Math
					.min(minFlow, (parentEdge.capacity - parentEdge.flow));
			parentEdge = parent.parentEdge;
			parent = getVertex(parentEdge.otherEnd(parent));

		}while(!parentEdge.equals(cycleEdge));

		return minFlow;
	}

	public int getFlow() {
		return currentFlow;
	}
	
	
	
	private boolean hasPathToTerminal() {
		for (FVertex fv : fVertices) {
			fv.distance = FVertex.INFINITY;
			fv.parentEdge = null;
			for (FEdge e : fv.adjEdge) {
				e.isTightEdge = false;
			}
			fv.tightEdges = new FEdge[fv.adjEdge.size()];
			fv.tightEdgeSize = 0;

		}
		source.distance = 0;
		boolean existingEdge=false;
		for (int i = 0; i < fVertices.length - 1; i++) {
			for (FVertex u : fVertices) {
				if(u.distance==FVertex.INFINITY) {
					continue;
				}
				for (Edge e : u) {
					FEdge fe = (FEdge) e;
					existingEdge=false;
					FVertex child = (FVertex) fe.otherEnd(u);
					for(FEdge temp:u.tightEdges) {
						if(temp!=null&&temp.reverseEdge.equals(fe)) {
							existingEdge=true;
							break;
						}
					}
					if (child.distance > u.distance + fe.cost && !existingEdge) {
						
						if (child.distance == u.distance + fe.cost) {
							if (!child.containsTightEdge(fe)) {
								child.addTightEdge(fe);
							}
						} else {
							child.distance = u.distance + fe.cost;
							child.clearTightEdges();
							child.addTightEdge(fe);
						}
						child.parentEdge = fe;
					}
				}
			}
		}
		if (terminal.parentEdge == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	private void cancelNegativeCycle() {
		boolean cyclePossible = true;
		while (cyclePossible) {
			cyclePossible = false;
			for (FVertex i : fVertices) {
				FVertex cycleNode = findTightEdges(i);
				if (cycleNode != null) {
					System.out.println(cycleNode);
					cyclePossible = true;
					int pathFlow = Integer.MAX_VALUE;
					FVertex v = cycleNode, u = (FVertex) v.parentEdge.to;
					FEdge tempEdge = v.parentEdge;
					pathFlow = Math.min(pathFlow, tempEdge.capacity - tempEdge.flow);
					for (v = u; !v.equals(cycleNode); v = (FVertex) v.parentEdge.to) {
						u = (FVertex) v.parentEdge.to;
						tempEdge = v.parentEdge;
						pathFlow = Math.min(pathFlow, tempEdge.capacity - tempEdge.flow);
						
					}
					v = cycleNode;
					u = (FVertex) v.parentEdge.to;
					tempEdge = v.parentEdge;
					FEdge tempEdgeRev = tempEdge.reverseEdge;
					tempEdge.flow -= pathFlow;
					tempEdgeRev.flow += pathFlow;
					for (v = u; !v.equals(cycleNode); v = (FVertex) v.parentEdge.to) {
						u = (FVertex) v.parentEdge.to;
						tempEdge = v.parentEdge;
						tempEdgeRev = tempEdge.reverseEdge;
						tempEdge.flow -= pathFlow;
						tempEdgeRev.flow+=pathFlow;
					}

				}
			}
		}

	}
	private FVertex findTightEdges(FVertex tempFVertex) {
		FVertex cycleNode = null;
		for (FVertex fv : fVertices) {
			fv.distance = FVertex.INFINITY;

		}
		tempFVertex.distance = 0;
		for (int i = 0; i < fVertices.length - 1; i++) {
			for (FVertex current : fVertices) {
				for (Edge e : current) {
					FVertex child = getVertex(e.otherEnd(current));
					FEdge fe = (FEdge) e;
					if (child.distance > current.distance + (fe.cost)) {
						child.distance = current.distance + e.weight;
						child.parentEdge = fe;
					}
				}
			}
		}
		for (FVertex u : fVertices) {
			for (Edge e : u) {
				FVertex child = getVertex(e.otherEnd(u));
				FEdge fe = (FEdge) e;
				if (child.distance > u.distance + fe.cost) {
					cycleNode = child;
				}
			}
		}
		return cycleNode;

	}
	public int successiveSPMinCostFlow(int d) {
		int returnFlow = 0;
		currentCost = 0;
		int requiredFlow = d;
		cancelNegativeCycle();
		while (hasPathToTerminal() && requiredFlow > 0) {
		for(FVertex fv : fVertices)
			{
				fv.seen=false;
			}
			for (Edge e : source) {
				FEdge fe = (FEdge) e;
				if (fe.isTightEdge) {
					source.seen=true;
					returnFlow = pushToTerminal(source, requiredFlow);
					source.seen=false;
					if (returnFlow > 0) {
						requiredFlow -= returnFlow;
						currentFlow += returnFlow;
					}
				}
			}

		}
		return currentCost;
	}

	private int pushToTerminal(FVertex vertex, int flow) {
		if (vertex.equals(terminal))
			return flow;

		int curr_flow;
		int temp_flow;
		for (Edge e : vertex) {
			Vertex otherVertex = e.otherEnd(vertex);
			FEdge fe = (FEdge) e;
			FVertex fvOther= getVertex(otherVertex);
			if (fe.isTightEdge && !fvOther.seen) {
				curr_flow = Math.min(flow, fe.capacity - fe.flow);
				fvOther.seen=true;
				temp_flow = pushToTerminal(getVertex(otherVertex), curr_flow);
				fvOther.seen=false;
				if (temp_flow > 0) {
					fe.flow += temp_flow;
					currentCost += fe.cost * temp_flow;
					fe.getReverseEdge().flow -= temp_flow;
					return temp_flow;
				}
			}
		}
		return 0;
	}
	
	public int flow(Edge e)
	{
		return forwardEdge[e.name-1].flow;
	}
	
	public int capacity(Edge e)
	{
		return forwardEdge[e.name-1].capacity;
	}
	
	public int cost(Edge e)
	{
		return forwardEdge[e.name-1].cost;
	}
}
