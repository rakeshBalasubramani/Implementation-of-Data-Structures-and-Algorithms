// Driver code for LP4, part c
// Do not rename this file or move it away from cs6301/g??
// Change following line to your group number. Make no other changes.

package cs6301.g38;

public class LP4c {
    static int VERBOSE = 0;

    public static void main(String[] args) {
        if (args.length > 0) {
            VERBOSE = Integer.parseInt(args[0]);
        }
        java.util.Scanner in = new java.util.Scanner(System.in);
        Graph g = Graph.readDirectedGraph(in);
        int source = in.nextInt();
        int target = in.nextInt();
      Timer t = new Timer();
        LP4 handle = new LP4(g, g.getVertex(source));
        long result = handle.countShortestPaths(g.getVertex(target));
        BellManFordGraph bg = new BellManFordGraph(g);
        bg.bellmanFord(g.getVertex(source), g.getVertex(target));
        if (VERBOSE > 0) {
            LP4.printGraph(g, null, g.getVertex(source), g.getVertex(target), 0);
        }
        System.out.println(result + "\n" + t.end());
    }
}
