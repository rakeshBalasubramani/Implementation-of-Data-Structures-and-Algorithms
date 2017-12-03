package cs6301.g38;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

public class LP8 {
    static int VERBOSE = 0;

    public static void main(String[] args) {
        if (args.length > 0) {
            VERBOSE = Integer.parseInt(args[0]);
        }
        java.util.Scanner in = new java.util.Scanner(System.in);
        Graph g = Graph.readDirectedGraph(in);
        Timer timer = new Timer();
        int s = in.nextInt();
        int t = in.nextInt();
        java.util.HashMap<Edge, Integer> capacity = new java.util.HashMap<>();
        java.util.HashMap<Edge, Integer> cost = new java.util.HashMap<>();

        int[] arr = new int[1 + g.edgeSize()];
        for (int i = 1; i <= g.edgeSize(); i++) {
            arr[i] = 1; // default capacity
        }
        while (in.hasNextInt()) {
            int i = in.nextInt();
            int cap = in.nextInt();
            arr[i] = cap;
        }
        for (Vertex u : g) {
            for (Edge e : u) {
                capacity.put(e, arr[e.getName()]);
            }
        }

        for (Vertex u : g) {
            for (Edge e : u) {
                cost.put(e, e.weight);
            }
        }

        MinCostFlow f = new MinCostFlow(g, g.getVertex(s), g.getVertex(t), capacity, cost);
        f.cycleCancellingMinCostFlow(Integer.MAX_VALUE);
      //  f.successiveSPMinCostFlow(Integer.MAX_VALUE);
        //f.setVerbose(VERBOSE);

        /* Uncomment this if you have implemented verify()
        if(f.verify()) {
        System.out.println("Max flow is verified");
        } else {
        System.out.println("Algorithm is wrong. Verification failed.");
        }
        */

        System.out.println(timer.end());
    }
}
