/**
 * @author rbk Ver 1.0: 2017/09/29 Example to extend Graph/Vertex/Edge classes
 *         to implement algorithms in which nodes and edges need to be disabled
 *         during execution. Design goal: be able to call other graph algorithms
 *         without changing their codes to account for disabled elements.
 *
 *         Ver 1.1: 2017/10/09 Updated iterator with boolean field ready.
 *         Previously, if hasNext() is called multiple times, then cursor keeps
 *         moving forward, even though the elements were not accessed by next().
 *         Also, if program calls next() multiple times, without calling
 *         hasNext() in between, same element is returned. Added
 *         UnsupportedOperationException to remove.
 **/

package cs6301.g38;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class BellManFordGraph extends Graph {

	private static boolean isTightEdgeOnly = false;

	public static class BVertex extends Vertex {
		boolean seen;
		List<BEdge> badj;
		long distance = Long.MAX_VALUE;
		BVertex[] parentVertices;
		int parentSize;
		int count;

		BVertex(Vertex u,int revAdjSize) {
			super(u);
			seen = false;
			badj = new LinkedList<>();
			parentVertices = new BVertex[revAdjSize];
			parentSize=0;
		}

		boolean isSeen() {
			return seen;
		}

		void seen() {
			seen = true;
		}

		void unSeen() {
			seen = false;
		}

		public boolean containsParent(BVertex currrent) {
			boolean isExist=false;
			for(int i=0;i< parentSize;i++)
			{
				if(parentVertices[i].equals(currrent))
				{
					isExist=true;
					break;
				}
			}
			return isExist;
		}

		public void addParent(BVertex currrent) {
			parentVertices[parentSize++]= currrent;
		}

		public void clearParent() {
			parentSize=0;			
		}

		public int parentSize() {
			return parentSize;
		}

	}

	static class BEdge extends Edge {

		BEdge(BVertex from, BVertex to, int weight) {
			super(from, to, weight);
		}

	}

	BVertex[] xv; // vertices of graph

	public BellManFordGraph(Graph g) {
		super(g);
		xv = new BVertex[g.size()]; // Extra space is allocated in array for
									// nodes to be added later
		for (Vertex u : g) {
			xv[u.getName()] = new BVertex(u,u.revAdj.size());
		}

		// Make copy of edges
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				BVertex x1 = getVertex(u);
				BVertex x2 = getVertex(v);
				x1.badj.add(new BEdge(x1, x2, e.weight));
			}
		}
	}

	@Override
	public Vertex getVertex(int n) {
		return xv[n - 1];
	}

	BVertex getVertex(Vertex u) {
		return Vertex.getVertex(xv, u);
	}

	void seen(int i) {
		BVertex u = (BVertex) getVertex(i);
		u.seen();
	}

	boolean bellmanFord(Vertex s, Vertex t) {
		Queue<BVertex> queue = new LinkedList<BellManFordGraph.BVertex>();
		BVertex source = getVertex(s);
		source.seen = true;
		source.distance = 0;
		queue.offer(source);
		boolean isPathExist = false;
		while (!queue.isEmpty()) {
			BVertex currrent = queue.poll();
			currrent.unSeen();
			currrent.count++;
			if (currrent.count >= xv.length - 1) {
				return false;
			}

			for (Edge e : currrent) {

				BVertex child = getVertex(e.otherEnd(currrent));
				if (child.distance >= currrent.distance + e.weight) {

					if (child.distance == currrent.distance + e.weight) {
						if (!child.containsParent(currrent))
							child.addParent(currrent);
					} else {
						child.distance = currrent.distance + e.weight;
						child.clearParent();
						if (!child.containsParent(currrent))
							child.addParent(currrent);
					}

					if (!child.isSeen()) {
						queue.offer(child);
						child.seen();
					}
				}

				if (e.otherEnd(currrent).equals(t)) {
					isPathExist = true;
				}
			}
		}

		if (isPathExist) {
			// List<List<BVertex>> allPaths = new ArrayList<>();
			BVertex[] currentPath = new BVertex[xv.length];

			//dfs(getVertex(t), currentPath, xv.length - 1);

			System.out.println("No of paths: " + dfs(getVertex(t), 0));
		}
		return true;

	}

	private void dfs(BVertex t, BVertex[] currentPath, int curLen) {
		// currentPath.addFirst(t);
		currentPath[curLen--] = t;
		if (t.parentSize() == 0) {
			printPath(currentPath, curLen);
		}

		for (int i=0;i<t.parentSize;i++) {
			
			dfs(t.parentVertices[i], currentPath, curLen);
			
		}

	}

	private void printPath(BVertex[] currentPath, int startIndex) {
		for (int i = startIndex + 1; i < currentPath.length; i++) {

			System.out.print(currentPath[i] + " ");

		}

		System.out.println();
	}

	private long dfs(BVertex t, long allPaths) {
		if (t.parentSize() == 0) {
			return ++allPaths;
		}

		for (int i=0;i<t.parentSize;i++) {
			allPaths = dfs(t.parentVertices[i], allPaths);
			
	
		}

		return allPaths;
	}

	public static void main(String[] args) {
		Graph g = Graph.readGraph(new Scanner(System.in));
		BellManFordGraph xg = new BellManFordGraph(g);
		Vertex src = xg.getVertex(1);

	}

	void printGraph(BFS b) {
		for (Vertex u : this) {
			System.out.print("  " + u + "  :   " + b.distance(u) + "  : ");
			for (Edge e : u) {
				System.out.print(e);
			}
			System.out.println();
		}

	}

}