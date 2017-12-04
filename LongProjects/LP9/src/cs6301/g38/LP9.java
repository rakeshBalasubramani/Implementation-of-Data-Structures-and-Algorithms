package cs6301.g38;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

public class LP9 {
    static int VERBOSE = 0;

    public static void main(String[] args) {
        if (args.length > 0) {
            VERBOSE = Integer.parseInt(args[0]);
        }
        java.util.Scanner in = new java.util.Scanner(System.in);
        Graph g = Graph.readDirectedGraph(in);
        Timer timer = new Timer();
     //   int s = in.nextInt();

        Postman postMan = new Postman(g);
        System.out.println(postMan.postmanTour());
        //System.out.println(postMan.getTour());
        
        
        
       
    

       

        System.out.println(timer.end());
    }
}
