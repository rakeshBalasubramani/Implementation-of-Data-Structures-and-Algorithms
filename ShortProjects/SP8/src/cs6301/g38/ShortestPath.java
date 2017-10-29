package cs6301.g38;
/**
 * @author RAKESH BALASUBRAMANI - rxb162130 HARIPRIYAA MANIAN – hum160030
 *         RAJKUMAR PANNEER SELVAM - rxp162130 AVINASH VENKATESH – axv165330
 * 
 * @Description Class implementing ShortestPath algorithms 
 *
 */
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
	
	// class having additional properties for vertices to find shortest path 
	
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
	private ShortestPathVertex src; 
	
	/**
	 * Contructor to intialize graph and source
	 * @param g - input graph
	 * @param s - source vertex
	 */
	public ShortestPath(Graph g, Graph.Vertex s) {
		shortestPathVertex = new ShortestPathVertex[g.size()];
		graph = new ShortestPathVertex(g);
		for (Graph.Vertex u : g) {			
				shortestPathVertex[u.name] = new ShortestPathVertex(u);
		}
		src = shortestPathVertex[s.name];
	}

	/**
	 * function to re-initialize all the vertex fields
	
	 */
	private void resetShortestPathVertex() {
		if (shortestPathVertex != null) {
			for (ShortestPathVertex vertex : shortestPathVertex) {
				vertex.resetEntries();
			}
		}
	}
	
	
	/**
	 * function performing relax operation 
	 * 
	 * @param e - edge to be realxed
	 * @return - true if distance updated
	 */
	public boolean relax(Graph.Edge e){
		Graph.Vertex u = e.from;
		Graph.Vertex v = e.to;
		if(shortestPathVertex[v.name].d > shortestPathVertex[u.name].d + e.weight){
			shortestPathVertex[v.name].d = shortestPathVertex[u.name].d + e.weight;
			//System.out.println("\tupdated distnace " + shortestPathVertex[v.name].d);
			shortestPathVertex[v.name].parent = u;
			
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 
	 * BFS implementation to find shortest path
	 */
	public void bfs() { 
		System.out.println("----------------BFS Shortest Path---------------- ");
		resetShortestPathVertex();
		Queue<ShortestPathVertex> queue = new LinkedList<ShortestPathVertex>();
			
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
					
				}
			}
		}		
		
	}
	
	
	/**
	 * 
	 * implementation of DAG Shortest Path
	 * 
	 */
	public void dagShortestPaths() { 
		resetShortestPathVertex();
		System.out.println("\n-------------DAG Shortest Path---------------");
		List<Graph.Vertex> topologicalOrder = new LinkedList<Graph.Vertex>();
		TopologicalOrder tp = new TopologicalOrder();
		topologicalOrder = tp.topologicalOrder2(graph.g);
		
		src.d = 0;
		
		// for each vertex in topological order, relax the edges out of vertex
		
		if(topologicalOrder == null){
			System.out.println("DAG having cycles with no Topological order " );
		}
		else{
		for(Graph.Vertex u : topologicalOrder){
			for(Graph.Edge e : u.adj){
				relax(e);
				
			}
		  }
		}
	}
	
	
	/**
	 * Dijkstra's Algorithm to find Shortest Path
	 */
	public void dijkstra() { 
		System.out.println("\n------------Dijkstra's Shortest Path-------------------");
		
		//initialize source
		resetShortestPathVertex();
		
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
				/*Graph.Vertex v = e.to;
				if(shortestPathVertex[v.name].d > u.d + e.weight){
					shortestPathVertex[v.name].d = u.d + e.weight;
					shortestPathVertex[v.name].parent = u.vertex;
					changed = true;
				}*/
				
				
				changed = relax(e);
				if(changed){
					Graph.Vertex v1 = e.otherEnd(u.vertex);
					indexedHeap.decreaseKey(shortestPathVertex[v1.name]);
				}
			}
		}		
	}
	
	
	/**
	 * implementation of bellman ford shortest path
	 * 
	 * @return - true if no negative cycles , false if negative cycles
	 */
	public boolean bellmanFord() {
		System.out.println("\n------------Bellman Ford Shortest Path-------------------");
		resetShortestPathVertex();
		Queue<ShortestPathVertex> queue = new LinkedList<ShortestPathVertex>();
		
		//initialize source and add to the queue
		src.d = 0;
		src.seen = true;
		queue.add(src);
		
		while(queue.peek()!=null){
			ShortestPathVertex u = queue.remove();
			u.seen = false; //no longer in the queue
			u.count = u.count+1;
			if(u.count >= shortestPathVertex.length){ //negative cycle
				//System.out.println("Graph has negative cycles");
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
			}
		}		
		return true; 		
	}
	
	 
	/**
	 * function to check for presence of positive and negative weights and edges
	 *
	 * @return - corresponding check value representing positive/negative weights, edges
	 * 
	 */
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
	
	/**
	 * function to print each vertices with thier distance from source
	 */
	public  void printResults(){
		System.out.println("Distance from the source vertex");
		for(ShortestPathVertex u : shortestPathVertex){
			System.out.println("Vertex: " + u.vertex + "  Distance :" + u.d);
		}
	}
	
	
	/**
	 * Function to check whetehr the graph is DAG or not 
	 * @return true/false 
	 */
	public boolean checkForDag(){
		List<Graph.Vertex> topologicalOrder = new LinkedList<Graph.Vertex>();
		TopologicalOrder tp = new TopologicalOrder();
		topologicalOrder = tp.topologicalOrder2(graph.g);
		if(topologicalOrder == null){
			return false;
		}
		return true;
	}
	
	/**
	 *function to determine which shortest path algorithm to run
	 *
	 * @return  - false if graph has negative cycles 
	 */
	public boolean fastestShortestPaths() {
		boolean isDirectedAcyclicGraph = false;
		int isNegativeWeight = findWeight();
		
		isDirectedAcyclicGraph = checkForDag();		
		//System.out.println("dag? " + isDirectedAcyclicGraph);		
		
		if(isNegativeWeight == 1 ){ // same positive weight edges
			System.out.println("Given graph has same positive edge weights...\nRunning BFS...");
			bfs();
			
		}else if (isDirectedAcyclicGraph) { // graph is DAG
			System.out.println("Given directed graph is a DAG...\nrunning DAG shortest path... ");
			dagShortestPaths();
			
		}else if(isNegativeWeight == 0){ // no negative weight edges
			System.out.println("Graph has no negative edge weights...\nRunning Dijkstra's shortest path...");
			dijkstra();
			
		}else{
			System.out.println("Running Bellman Ford");
			boolean isBellmanFord = bellmanFord();
			return(isBellmanFord);			
		}		
		return true;	 
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);
		Graph.Vertex s = g.getVertex(1);
		ShortestPath sp = new ShortestPath(g,s);		
		boolean isShortestPath = sp.fastestShortestPaths();
		if(isShortestPath){
			sp.printResults();
		}else{
			System.out.println("Graph has negative cycles");
		}
	
	}

}
