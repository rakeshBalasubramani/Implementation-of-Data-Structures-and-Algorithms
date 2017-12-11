// Driver program for LP72: push-relabel algorithm using 2 heuristics
package cs6301.g38;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs6301.g38.Graph;
import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;


public class LP72 {
    public static void main(String[] args) throws FileNotFoundException {
	int extra = 0, choice = 0;
	if(args.length > 0) { extra = Integer.parseInt(args[0]); }
	if(args.length > 1) { choice = Integer.parseInt(args[1]); }
//	java.util.Scanner in = new java.util.Scanner(new FileReader("lp7-big.txt"));
	java.util.Scanner in = new java.util.Scanner(System.in);
	Graph g = Graph.readDirectedGraph(in);
	int s = in.nextInt();
	int t = in.nextInt();
	java.util.HashMap<Edge,Integer> capacity = new java.util.HashMap<>();
	int[] arr = new int[1 + g.edgeSize()];
	for(int i=1; i<=g.edgeSize(); i++) {
	    arr[i] = 1;   // default capacity
	}
	while(in.hasNextInt()) {
	    int i = in.nextInt();
	    int cap = in.nextInt();
	    arr[i] = cap;
	}

	Vertex src = g.getVertex(s);
	Vertex target = g.getVertex(t);

	if(extra > 0) {
	    for(Edge e: src) {
		Vertex v = e.otherEnd(src);
		arr[e.getName()] += extra;
		for(Edge e2: v) {
		    arr[e2.getName()] += extra/10;
		}
	    }
	    java.util.Iterator<Edge> it = target.reverseIterator();
	    while(it.hasNext()) {
		Edge e = it.next();
		Vertex v = e.otherEnd(target);
		java.util.Iterator<Edge> it2 = v.reverseIterator();
		while(it2.hasNext()) {
		    Edge e2 = it2.next();
		    arr[e2.getName()] += extra/10;
		}
		arr[e.getName()] += extra;
	    }
	}

	for(Vertex u: g) {
	    for(Edge e: u) {
		capacity.put(e, arr[e.getName()]);
	    }
	}

	Timer timer = new Timer();
	Flow f = new Flow(g, src, target, capacity);

	/* use the following logic:
		choice = 0  =>  use no heuristic
		choice = 1  =>  use BFS heuristic only
		choice = 2  =>  use gap heuristic only
		choice = 3  =>  use both heuristics
	*/
	f.chooseHeuristics(1);
	int value = f.relabelToFront();
	System.out.println(value);
	System.out.println(timer.end());
    }
}
	
