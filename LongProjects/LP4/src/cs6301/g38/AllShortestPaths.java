package cs6301.g38;

import java.util.LinkedList;
import java.util.Queue;


/**
 * @author Avinash Venkatesh - axv165330 <br>
 *         HariPriyaa - hum160030 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         Raj Kumar Panneer Selvam - rxp162130
 *
 * @description This class is used to find all shortest paths between given source and destination vertex
 * in the given input graph
 */
public class AllShortestPaths extends Graph {

	/**
	 * @description BVertex used to store shortest distance, its parent vertices and size.
	 *
	 */
	public static class BVertex extends Vertex {
		boolean seen;
		long distance = Long.MAX_VALUE;
		BVertex[] parentVertices;
		int parentSize;
		int count; // used for BellmanFord algo iteration count.

		BVertex(Vertex u, int revAdjSize) {
			super(u);
			seen = false;
			parentVertices = new BVertex[revAdjSize];
			parentSize = 0;
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
			boolean isExist = false;
			for (int i = 0; i < parentSize; i++) {
				if (parentVertices[i].equals(currrent)) {
					isExist = true;
					break;
				}
			}
			return isExist;
		}

		public void addParent(BVertex currrent) {
			parentVertices[parentSize++] = currrent;
		}

		public void clearParent() {
			parentSize = 0;
		}

		public int parentSize() {
			return parentSize;
		}

	}


	private BVertex[] xv; // vertices of graph
	
	private BVertex[]currentPath; // vertices used to store the path from src to dest.

	public AllShortestPaths(Graph g) {
		super(g);
		xv = new BVertex[g.size()]; 
		for (Vertex u : g) {
			xv[u.getName()] = new BVertex(u, u.revAdj.size());
		}

	
	}

	@Override
	public Vertex getVertex(int n) {
		return xv[n - 1];
	}

	BVertex getVertex(Vertex u) {
		return Vertex.getVertex(xv, u);
	}

	

	/**count all shortest paths from dest to src using dfs backtracking.
	 * @param t - dest vertex
	 * @return no of shortest paths from src to dest.
	 */
	private long countAllSPs(Vertex t) {
		long noOfSp = findPathToSrc(getVertex(t), 0);
		return noOfSp;
	}

	/**count and enumerate all shortest paths from dest to src using dfs backtracking.
	 * @param t - dest vertex
	 * @return no of shortest paths from src to dest.
	 */
	private long enumerateAllSPs(Vertex t) {

		currentPath = new BVertex[xv.length];

		return findPathToSrc(getVertex(t), xv.length - 1, 0);
	}

	/**count all shortest paths from s to t.
	 * @param s - src vertex
	 * @param t - dest vertex
	 * @return no of shortest paths from src to dest.
	 */
	public long countAllSPs(Vertex s, Vertex t) {
		if (bellmanFord(s, t)) {
			return countAllSPs(t);
		} else {
			System.out
			.println("Non-positive cycle in graph.  Unable to solve problem");
			return Long.MAX_VALUE;
		}

	}

	/**count and enumerate all shortest paths from s to t.
	 * @param s - src vertex
	 * @param t - dest vertex
	 * @return no of shortest paths from src to dest.
	 */
	public long enumerateAllSPs(Vertex s, Vertex t) {
		if (bellmanFord(s, t)) {
			return enumerateAllSPs(t);
		} else {
			System.out
			.println("Non-positive cycle in graph.  Unable to solve problem");
			return Long.MAX_VALUE;
		}

	}

	/**Run bellmanford from s to t
	 * @param s - src vertex
	 * @param t - dest vertex
	 * @return - true, if path exists between s to t, else false.
	 */
	private boolean bellmanFord(Vertex s, Vertex t) {
		Queue<BVertex> queue = new LinkedList<AllShortestPaths.BVertex>();
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

		return isPathExist;

	}

	/**Finds all paths from src to dest using DFS.
	 * @param t - current Vertex
	 * @param curIndex - currentIndex in the path array
	 * @param allPaths - to keep track of no of shortest paths b/w src to dest.
	 * @return - No of shortest paths from src to dest
	 */
	private long findPathToSrc(BVertex t,  int curIndex, long allPaths) {
		currentPath[curIndex--] = t;
		if (t.parentSize() == 0) {
			printPath(currentPath, curIndex);
			return ++allPaths;
		}

		for (int i = 0; i < t.parentSize; i++) {

			allPaths = findPathToSrc(t.parentVertices[i], curIndex, allPaths);

		}
		return allPaths;

	}

	/**Print the vertices in the path array.
	 * @param currentPath - given Path array
	 * @param startIndex -  start index of the array
	 */
	private void printPath(BVertex[] currentPath, int startIndex) {
		for (int i = startIndex + 1; i < currentPath.length; i++) {

			System.out.print(currentPath[i] + " ");

		}

		System.out.println();
	}

	/**Finds all paths from src to dest using DFS.
	 * @param t - current Vertex
	 * @param allPaths - to keep track of no of shortest paths b/w src to dest.
	 * @return - No of shortest paths from src to dest
	 */
	private long findPathToSrc(BVertex t, long allPaths) {
		if (t.parentSize() == 0) {
			return ++allPaths;
		}

		for (int i = 0; i < t.parentSize; i++) {
			allPaths = findPathToSrc(t.parentVertices[i], allPaths);

		}

		return allPaths;
	}

}