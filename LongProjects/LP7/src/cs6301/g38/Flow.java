// Starter code for LP7
package cs6301.g38;

import cs6301.g38.Graph.*;

import java.util.HashMap;
import java.util.Set;

public class Flow {
	HashMap<Edge, Integer> capacity;
	Graph g;
	Vertex source;
	Vertex terminal;
	int[] edgesFlow;
	private Set<Vertex> minCutT;
	private Set<Vertex> minCutS;
	MaxFlowGraph dg;

	public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
		this.g = g;
		this.capacity = capacity;
		source = s;
		terminal = t;
		edgesFlow = new int[g.edgeSize()];

	}

	// Return max flow found by Dinitz's algorithm
	public int dinitzMaxFlow() {

		dg = new MaxFlowGraph(g, source, terminal, capacity);
		dg.dinitz();
		assignFlowParams();
		System.out.println("DG : " + dg.maxFlow);
		return dg.maxFlow;
	}

	private void assignFlowParams() {
		minCutS = dg.getMinCutS();
		minCutT = dg.getMinCutT();

		for (Vertex v : g) {
			for (Edge e : v) {
				edgesFlow[e.name - 1] = dg.flow(e);
			}
		}
	}

	// Return max flow found by relabelToFront algorithm
	public int relabelToFront() {
		dg = new MaxFlowGraph(g, source, terminal, capacity);
		dg.relabelToFront();
		assignFlowParams();
		System.out.println("RG : " + dg.maxFlow);
		return dg.maxFlow;
	}

	// flow going through edge e
	public int flow(Edge e) {
		return edgesFlow[e.name - 1];
	}

	// capacity of edge e
	public int capacity(Edge e) {
		return capacity.get(e);
	}

	/*
	 * After maxflow has been computed, this method can be called to get the
	 * "S"-side of the min-cut found by the algorithm
	 */
	public Set<Vertex> minCutS() {
		return minCutS;
	}

	/*
	 * After maxflow has been computed, this method can be called to get the
	 * "T"-side of the min-cut found by the algorithm
	 */
	public Set<Vertex> minCutT() {
		return minCutT;
	}
}
