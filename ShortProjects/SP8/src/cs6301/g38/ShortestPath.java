package cs6301.g38;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class ShortestPath  {
	static final int Infinity = Integer.MAX_VALUE;

	class ShortestPathVertex implements Comparator<ShortestPathVertex>, Index {
		Graph.Vertex parent;
		Graph.Vertex vertex;
		int d;
		int index;
		int count;
		boolean seen;
		Graph g;
		public ShortestPathVertex(Graph.Vertex u){
			vertex = u;
			parent = null;
			d = Infinity;
			seen = false;
			count = 0;
		}
		public ShortestPathVertex(Graph g){
			this.g= g;
		}
		public void resetEntries() {
			parent = null;
			d = Infinity;
			seen = false;
			count = 0;
		}
		@Override
		public int compare(ShortestPathVertex u, ShortestPathVertex v) {
			if (u.d < v.d) {
				return -1;
			} else if (u.d == v.d) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override	
		public void putIndex(int i) {
			index = i;
		}
		@Override
		public int getIndex() {
			return index;
		}

			
	}	
	private ShortestPathVertex[] shortestPathVertex;
	private ShortestPathVertex graph;
	public ShortestPath(Graph g, Graph.Vertex s) {
		shortestPathVertex = new ShortestPathVertex[g.size()];
		graph = new ShortestPathVertex(g);
		for (Graph.Vertex u : g) {			
				shortestPathVertex[u.name] = new ShortestPathVertex(u);
		}
	}

	//function to re-initialize all the vertex fields
	private void resetShortestPathVertex() {
		if (shortestPathVertex != null) {
			for (ShortestPathVertex vertex : shortestPathVertex) {
				vertex.resetEntries();
			}
		}
	}
	
	public boolean relax(Graph.Edge e){
		Graph.Vertex u = e.from;
		Graph.Vertex v = e.to;
		//System.out.println("edge check in relax " + "from "+ e.from +" to "+ e.to + " weight " +e.weight);
		if(shortestPathVertex[v.name].d > shortestPathVertex[u.name].d + e.weight){
			shortestPathVertex[v.name].d = shortestPathVertex[u.name].d + e.weight;
			//System.out.println("\tupdated distnace " + shortestPathVertex[v.name].d);
			shortestPathVertex[v.name].parent = u;
			
			return true;
		}
		return false;
	}
	
	
	//bfs implementation to find shortest path
	public void bfs() { 
		System.out.println("----------------BFS Shortest Path---------------- ");
		resetShortestPathVertex();
		Queue<ShortestPathVertex> queue = new LinkedList<ShortestPathVertex>();
			
		ShortestPathVertex src = shortestPathVertex[0];
		System.out.println("Source Vertex : "+ src.vertex);
		
		//initialize source and add it to the queue
		src.d = 0;
		src.seen = true;
		queue.add(src);
		
		while(queue.peek()!=null){
			System.out.println("Quueue:" + queue.element().vertex);
			ShortestPathVertex u = queue.remove();
			for(Graph.Edge edge : u.vertex){
				Graph.Vertex v = edge.otherEnd(u.vertex);
				if(!shortestPathVertex[v.name].seen){
					shortestPathVertex[v.name].d = u.d+1;
					shortestPathVertex[v.name].parent = u.vertex;
					shortestPathVertex[v.name].seen = true;
					queue.add(shortestPathVertex[v.name]);	
					
					System.out.println("Vertex: " + shortestPathVertex[v.name].vertex+ "  Distance :" + shortestPathVertex[v.name].d);
					
				}
			}
		}		
		
	}
	
	// implementation of DAG Shortest Path
	public void dagShortestPaths() { 
		resetShortestPathVertex();
		System.out.println("\n-------------DAG Shortest Path---------------");
		List<Graph.Vertex> topologicalOrder = new LinkedList<Graph.Vertex>();
		TopologicalOrder tp = new TopologicalOrder();
		topologicalOrder = tp.topologicalOrder2(graph.g);
		
		
		ShortestPathVertex src = shortestPathVertex[0];
		System.out.println("Source Vertex : "+ src.vertex);
		src.d = 0;
		//System.out.println("Topological Order " + topologicalOrder);
		// for each vertex in topological order, relax the edges out of vertex
		
		if(topologicalOrder == null){
			System.out.println("topological order " + null);
		}
		else{
		for(Graph.Vertex u : topologicalOrder){
			for(Graph.Edge e : u.adj){
				relax(e);
				System.out.println("Vertex: " + shortestPathVertex[u.name].vertex+ "  Distance :" + shortestPathVertex[u.name].d);
				
			}
		  }
		}
	}
	
	//Dijkstra's Algorithm to find Shortest Path
	public void dijkstra() { 
		System.out.println("\n------------Dijkstra's Shortest Path-------------------");
		
		//initialize source
		resetShortestPathVertex();
		ShortestPathVertex src = shortestPathVertex[0];
		System.out.println("Source Vertex : "+ src.vertex);
		src.d = 0;
		boolean changed = false;
		
		// create indexed priority queue
		IndexedHeap<ShortestPathVertex> indexedHeap = new IndexedHeap<ShortestPathVertex>(Arrays.copyOf(shortestPathVertex, 	shortestPathVertex.length),
				shortestPathVertex[0], shortestPathVertex.length);
		indexedHeap.buildHeap();
		while (indexedHeap.peek() != null) {
			ShortestPathVertex u = indexedHeap.remove();
			u.seen = true;
			for(Graph.Edge e : u.vertex.adj){
				
				changed = relax(e);
				System.out.println("Vertex: " + u.vertex+ "  Distance :" + u.d);
				if(changed){
					Graph.Vertex v = e.otherEnd(u.vertex);
					indexedHeap.decreaseKey(shortestPathVertex[v.name]);
				}
			}
		}		
	}
	
	//implementation of bellman ford shortest path
	public boolean bellmanFord() {
		System.out.println("\n------------Bellman Ford Shortest Path-------------------");
		resetShortestPathVertex();
		
		Queue<ShortestPathVertex> queue = new LinkedList<ShortestPathVertex>();
		
		//initialize source and add to the queue
		
			
		ShortestPathVertex src = shortestPathVertex[0];
		System.out.println("Source Vertex : "+ src.vertex);
		src.d = 0;
		src.seen = true;
		System.out.println("src: " + src.vertex + src.d + " :" + src.seen);
		queue.add(src);
		System.out.println("queue" + queue.element().vertex);
		
		
		while(queue.peek()!=null){
			ShortestPathVertex u = queue.remove();
			u.seen = false; //no longer in the queue
			u.count = u.count+1;
			
			if(u.count >= shortestPathVertex.length){ //negative cycle
				return false;
			}
			for(Graph.Edge e : u.vertex.adj){
				Graph.Vertex v = e.to;
				if(shortestPathVertex[v.name].d > u.d + e.weight){
					shortestPathVertex[v.name].d = u.d + e.weight;
					shortestPathVertex[v.name].parent = u.vertex;
					if(!shortestPathVertex[v.name].seen){
						queue.add(shortestPathVertex[v.name]);
						shortestPathVertex[v.name].seen = true;	
					}
				}
				System.out.println("Vertex: " + u.vertex + "  Distance :" + u.d);
			}
			System.out.println("Vertex: " + u.vertex+ "  Distance :" + u.d);
		}		
		return true; 		
	}
	
	public int findWeight(){
		int result = 0;	
		HashSet<Integer> checkWeight = new HashSet<Integer>();
		
		for(Graph.Vertex u : graph.g.v){
			for(Graph.Edge e : u.adj){
				if(e.weight < 0){ // negative edge 
					result = -1;
					return result;					
				}
				else if(e.weight > 0){
					checkWeight.add(e.weight);					
				}
			}
		}
		if(checkWeight.size() == 1){ // same positive edge 
			return 1;
		}
		return result;
	}
	
	
	public boolean fastestShortestPaths() {
		boolean isDirectedAcyclicGraph = false;
		int isNegativeWeight = findWeight();
		isDirectedAcyclicGraph = DirectedAcyclicGraph.isDAG(graph.g);
		System.out.println("dag " + isDirectedAcyclicGraph);
		// equal positive- BFS
		// dag- dag
		// no negative weight edge - dijkstra
		// else Bellman ford
		// negative cycle - return false 
		
		if(isNegativeWeight == 1 ){
			System.out.println("Given graph has same positive edge weights...\nRunning BFS...");
			bfs();
		}else if (isDirectedAcyclicGraph) {
			System.out.println("Given directed graph is a DAG...\nrunning DAG shortest path... ");
			dagShortestPaths();
		}else if(isNegativeWeight == 0){
			System.out.println("Graph has no negative edge weights...\nRunning Dijkstra's shortest path...");
			dijkstra();
		}else{
			System.out.println("Running Bellman Ford");
			return(bellmanFord());			
		}		
		return true;	
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);

		Graph.Vertex s = g.getVertex(1);
		ShortestPath sp = new ShortestPath(g,s);		
		sp.fastestShortestPaths();
	
	}

}
