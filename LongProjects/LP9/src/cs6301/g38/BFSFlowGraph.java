package cs6301.g38;

import java.util.LinkedList;

/**
 * Breadth-first search
 * 
 * @author rbk Version 1.0: 2017/09/08
 */

import java.util.Queue;

import cs6301.g38.MinCostGraph.FVertex;


/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 *
 * @Desc Modified BFSClass for max flow.
 */
public class BFSFlowGraph extends GraphAlgorithm<BFSFlowGraph.BFSVertex> {
    public static final int INFINITY = Integer.MAX_VALUE;
    private MinCostGraph graph;

    // Class to store information about a vertex in this algorithm
    static class BFSVertex {
        boolean seen;
        Graph.Vertex parent;
        int distance; // distance of vertex from source

        BFSVertex(Graph.Vertex u) {
            seen = false;
            parent = null;
            distance = INFINITY;
        }

        @Override
        public String toString() {
            return "BFSVertex [seen=" + seen + ", parent=" + parent
                    + ", distance=" + distance + "]";
        }

    }

    Graph.Vertex src;

    public BFSFlowGraph(Graph g,
            Graph.Vertex src) {
        super(g);
        graph = (MinCostGraph) g;
        this.src = src;
        node = new BFSVertex[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            node[u.getName()] = new BFSVertex(u);
        }
        // Set source to be at distance 0
        getVertex(src).distance = 0;
    }

    // reinitialize allows running BFS many times, with different sources
    void reinitialize(Graph.Vertex newSource) {
        src = newSource;
        for (Graph.Vertex u : g) {
            BFSVertex bu = getVertex(u);
            bu.seen = false;
            bu.parent = null;
            bu.distance = INFINITY;
        }
        getVertex(src).distance = 0;
    }

    void bfs() {
        Queue<FVertex> q = new LinkedList<>();
        q.add(graph.getVertex(src));
        getVertex(src).seen = true;
        while (!q.isEmpty()) {
            Graph.Vertex u = q.remove();
            FVertex fv = (FVertex) u;
            for (Graph.Edge e : fv) {
                Graph.Vertex v = e.otherEnd(u);
                if (!seen(v)) {
                    visit(u, v);
                    q.add(graph.getVertex(v));
                }
            }
        }
    }

    boolean seen(Graph.Vertex u) {
        return getVertex(u).seen;
    }

    Graph.Vertex getParent(Graph.Vertex u) {
        return getVertex(u).parent;
    }

    int distance(Graph.Vertex u) {
        return getVertex(u).distance;
    }

    // Visit a node v from u
    void visit(Graph.Vertex u, Graph.Vertex v) {
        BFSVertex bv = getVertex(v);
        bv.seen = true;
        bv.parent = u;
        bv.distance = distance(u) + 1;
    }
}