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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AllShortestPaths extends Graph {

	public static class BVertex extends Vertex {
		boolean seen;
		long distance = Long.MAX_VALUE;
		BVertex[] parentVertices;
		int parentSize;
		int count;

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

	static class BEdge extends Edge {

		BEdge(BVertex from, BVertex to, int weight) {
			super(from, to, weight);
		}

	}

	BVertex[] xv; // vertices of graph
	
	BVertex[]currentPath;

	public AllShortestPaths(Graph g) {
		super(g);
		xv = new BVertex[g.size()]; // Extra space is allocated in array for
		// nodes to be added later
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

	void seen(int i) {
		BVertex u = (BVertex) getVertex(i);
		u.seen();
	}

	private long countAllSPs(Vertex t) {
		long noOfSp = dfs(getVertex(t), 0);
		return noOfSp;
	}

	private long enumerateAllSPs(Vertex t) {

		currentPath = new BVertex[xv.length];

		return dfs(getVertex(t), xv.length - 1, 0);
	}

	public long countAllSPs(Vertex s, Vertex t) {
		if (bellmanFord(s, t)) {
			return countAllSPs(t);
		} else {
			System.out
			.println("Non-positive cycle in graph.  Unable to solve problem");
			return Long.MAX_VALUE;
		}

	}

	public long enumerateAllSPs(Vertex s, Vertex t) {
		if (bellmanFord(s, t)) {
			return enumerateAllSPs(t);
		} else {
			System.out
			.println("Non-positive cycle in graph.  Unable to solve problem");
			return Long.MAX_VALUE;
		}

	}

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

	private long dfs(BVertex t,  int curLen, long allPaths) {
		currentPath[curLen--] = t;
		if (t.parentSize() == 0) {
			printPath(currentPath, curLen);
			return ++allPaths;
		}

		for (int i = 0; i < t.parentSize; i++) {

			allPaths = dfs(t.parentVertices[i], curLen, allPaths);

		}
		return allPaths;

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

		for (int i = 0; i < t.parentSize; i++) {
			allPaths = dfs(t.parentVertices[i], allPaths);

		}

		return allPaths;
	}

}