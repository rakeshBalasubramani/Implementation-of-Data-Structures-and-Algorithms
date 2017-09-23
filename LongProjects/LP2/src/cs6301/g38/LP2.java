package cs6301.g38;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cs6301.g38.Graph.Vertex;

public class LP2 {
	static int VERBOSE = 1;

	public static void main(String[] args) throws IOException {
		Scanner in;
		System.out.println(args[0]);
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		int start = 1;
		if (args.length > 1) {
			start = Integer.parseInt(args[1]);
		}
		if (args.length > 2) {
			VERBOSE = Integer.parseInt(args[2]);
		}
		
	//VERBOSE=10;
		Graph g = Graph.readDirectedGraph(in);
		System.out.println("Graph read done");
		
		
		long ver = 0; // DEbug PurposeDEbug Purpose
		for(Vertex v : g)// DEbug PurposeDEbug Purpose
		{
			ver = v.adj.size()+ver;// DEbug PurposeDEbug Purpose
		}
		System.out.println("Graph edges " +ver);// DEbug PurposeDEbug Purpose
		Graph.Vertex startVertex = g.getVertex(start);

		Timer timer = new Timer();
		Euler euler = new Euler(g, startVertex);
		euler.setVerbose(VERBOSE);

		boolean eulerian = euler.isEulerian();
		if (!eulerian) {
			return;
		}
		List<Graph.Edge> tour = euler.findEulerTour();
		timer.end();
System.out.println("Tour edges " + tour.size());// DEbug PurposeDEbug Purpose
		BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));// DEbug PurposeDEbug Purpose
		if (VERBOSE > 0) {
			System.out.println("Output:\n_________________________");
			writer.write("Output:\n_________________________");// DEbug PurposeDEbug Purpose
			
			for (Graph.Edge e : tour) {
				//System.out.print(e);
				writer.write(e.toString() + " \n");// DEbug PurposeDEbug Purpose
				
			}
			
			writer.flush();// DEbug PurposeDEbug Purpose
			writer.close();// DEbug PurposeDEbug Purpose
			System.out.println();
			System.out.println("_________________________");
		}
		System.out.println(timer);
	}
}
