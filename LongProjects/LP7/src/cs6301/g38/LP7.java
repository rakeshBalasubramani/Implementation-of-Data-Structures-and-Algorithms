package cs6301.g38;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

public class LP7 {
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
		Flow f = new Flow(g, g.getVertex(s), g.getVertex(t), capacity);
		PrintStream o = null;
		try {
			o = new PrintStream(new File("Flow.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	    // Assign o to output stream
	   // System.setOut(o);
		// f.setVerbose(VERBOSE);
		int value;
<<<<<<< HEAD
		//value = f.dinitzMaxFlow(); 
		
=======
		value = f.dinitzMaxFlow();
>>>>>>> 1effdc18452b114a5c49c9dab02072a28bb81b8c
		if (VERBOSE > 0) {
			for (Vertex u : g) {
				System.out.print(u + " : ");
				for (Edge e : u) {
				//	System.out.print(e + ":" + f.flow(e) + "/" + f.capacity(e)
					//		+ " | ");
				}
				System.out.println();
			}
			System.out.println("Min cut: S = " + f.minCutS());
			System.out.println("Min cut: T = " + f.minCutT());
		}

		System.out.println(timer.end());
		timer = new Timer(); 
		value = f.relabelToFront();

		/*
		 * Uncomment this if you have implemented verify() if(f.verify()) {
		 * System.out.println("Max flow is verified"); } else {
		 * System.out.println("Algorithm is wrong. Verification failed."); }
		 */

		// System.out.println(value);

		if (VERBOSE > 0) {
			for (Vertex u : g) {
				System.out.print(u + " : ");
				for (Edge e : u) {
				//	System.out.print(e + ":" + f.flow(e) + "/" + f.capacity(e)
					//		+ " | ");
				}
				System.out.println();
			}
			System.out.println("Min cut: S = " + f.minCutS());
			System.out.println("Min cut: T = " + f.minCutT());
		}

		System.out.println(timer.end());
	}
}
