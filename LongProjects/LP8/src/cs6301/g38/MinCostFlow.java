package cs6301.g38;

import java.util.HashMap;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

public class MinCostFlow {
	private Graph g;

	private Vertex source;

	private Vertex terminal;

	private HashMap<Edge, Integer> capacity;

	private HashMap<Edge, Integer> cost;

	public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {

		this.g = g;
		this.source = s;
		this.terminal = t;
		this.capacity = capacity;
		this.cost = cost;

	}

	// Return cost of d units of flow found by cycle cancellation algorithm
	int cycleCancellingMinCostFlow(int d) {
		System.out.println("Running Cycle Cancellation Algorithm:");
		MinCostGraph ccg = new MinCostGraph(g, source, terminal, capacity, cost);
		int minCost = ccg.cycleCancellation(d);
		System.out.println("Flow: " + ccg.getFlow() + "  Min Cost: " + minCost);

		return minCost;
	}

	// Return cost of d units of flow found by successive shortest paths
	int successiveSPMinCostFlow(int d) {
		System.out.println("Running Successive Shortest Paths Algorithm:");
		MinCostGraph ccg = new MinCostGraph(g, source, terminal, capacity, cost);
		int minCost = ccg.successiveSPMinCostFlow(d);
		System.out.println("Flow: " + ccg.getFlow() + "  Min Cost: " + minCost);

		return minCost;
	}

	// Return cost of d units of flow found by cost scaling algorithm
	int costScalingMinCostFlow(int d) {
		return 0;
	}

	// flow going through edge e
	public int flow(Edge e) {
		return 0;
	}

	// capacity of edge e
	public int capacity(Edge e) {
		return 0;
	}

	// cost of edge e
	public int cost(Edge e) {
		return 0;
	}
}