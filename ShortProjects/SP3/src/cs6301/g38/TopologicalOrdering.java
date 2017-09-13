package cs6301.g38;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import cs6301.g38.Graph.Edge;
import cs6301.g38.Graph.Vertex;

public class TopologicalOrdering {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);
		LinkedList<Graph.Vertex> order1 = new LinkedList<>();
		//LinkedList<Graph.Vertex> order2 = new LinkedList<>();
		order1 = topLogicalOrder1(g);
		System.out.println("Order1 : "+order1);
		//order2 = topLogicalOrder2(g);
	}

	private static LinkedList<Vertex> topLogicalOrder1(Graph g) {
		int topNum=0;
		ArrayDeque<Vertex> q = new ArrayDeque<>();
		LinkedList<Vertex> topList = new LinkedList<>();
		int[] inDegree = new int[g.size()];
		Iterator<Vertex> v = g.iterator();
		while(v.hasNext()) {
			Vertex temp = v.next();
			inDegree[temp.getName()]=temp.revAdj.size(); 
			if(inDegree[temp.getName()]==0) {
				q.add(temp);
			}
		}
		while(!q.isEmpty()) {
			Vertex temp = q.remove();
			topNum++;
			topList.add(temp);
			for(Edge x :temp.adj) {
				inDegree[x.otherEnd(temp).getName()]--;
				if(inDegree[x.otherEnd(temp).getName()]==0) {
					q.add(x.otherEnd(temp));
				}
			}
		}
		if(topNum==g.size()) 
			return topList;
		else
			return null;
	}

//	private static LinkedList<Vertex> toplogicalOrder2(Graph g) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
