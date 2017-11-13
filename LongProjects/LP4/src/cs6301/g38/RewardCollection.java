package cs6301.g38;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
/**
 * @author Avinash Venkatesh - axv165330 <br>
 *         HariPriyaa - hum160030 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         Raj Kumar Panneer Selvam - rxp162130
 *
 * @description This class is used to find max reward cycle in the graph from a given source where rewards can be collected at a vertex, when it gets reached using shortest paths.
 */
public class RewardCollection extends Graph {

	/**
	 * @description BVertex used to store shortest distance, reward.
	 *
	 */
	public static class RVertex extends Vertex {
		boolean seen;
		REdge[] badj;
		long distance = Long.MAX_VALUE;
		int reward;
		int count;// used for BellmanFord algo iteration count.

		RVertex(Vertex u) {
			super(u);
			seen = false;
			badj = new REdge[u.adj.size()] ;

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

	}

	static class REdge extends Edge {

		REdge(RVertex from, RVertex to, int weight) {
			super(from, to, weight);
		}

	}

	RVertex[] xv; // vertices of graph
	private RVertex source; // Given source vertex
	private RVertex[] path; // max reward cycle path of vertices.
	int maxReward; // maximum rewards collected.
	private int size; // size of the path array.

	private	Edge[] currentPath = new Edge[this.edgeSize()];

	public RewardCollection(Graph g, Vertex src, HashMap<Vertex, Integer> reward) {
		super(g);
		xv = new RVertex[g.size()];
		path = new RVertex[g.size()];
		for (Vertex u : g) {
			xv[u.getName()] = new RVertex(u);
		}

		for (Entry<Vertex, Integer> v : reward.entrySet()) {
			RVertex rv = getVertex(v.getKey());
			rv.reward = v.getValue();
		}

		source = getVertex(src);

		// Make copy of edges
		for (Vertex u : g) {
			int i=0;
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				RVertex x1 = getVertex(u);
				RVertex x2 = getVertex(v);
				x1.badj[i++] = new REdge(x1, x2, e.weight);
			}
		}
	}

	@Override
	public Vertex getVertex(int n) {
		return xv[n - 1];
	}

	RVertex getVertex(Vertex u) {
		return Vertex.getVertex(xv, u);
	}

	void seen(int i) {
		RVertex u = (RVertex) getVertex(i);
		u.seen();
	}

	/**Method used to find maximum reward cycle fromt the given source.
	 * @return max reward collected.
	 */
	public int findMaxRewardCycle() {
		if (bellmanFord()) {

			findMaxRewardCycle(source, this.edgeSize() - 1, null, true, 0,
					getVertex(source).reward);
			return this.maxReward;
		} else {
			return 0;
		}
	}

	/**Run bellmanford from source to find SPs for all vertices from source
	  * @return - true, if there is no Non-positive cycle in graph, else false.
	 */
	private boolean bellmanFord() {
		Queue<RVertex> queue = new LinkedList<RVertex>();
		RVertex source = getVertex(this.source);
		source.seen = true;
		source.distance = 0;
		queue.offer(source);
		while (!queue.isEmpty()) {
			RVertex currrent = queue.poll();
			currrent.unSeen();
			currrent.count++;
			if (currrent.count >= xv.length - 1) {
				return false;
			}

			for (Edge e : currrent) {

				RVertex child = getVertex(e.otherEnd(currrent));
				if (child.distance > currrent.distance + e.weight) {

					child.distance = currrent.distance + e.weight;
					if (!child.isSeen()) {
						queue.offer(child);
						child.seen();
					}
				}

			}
		}

		return true;

	}

	/** Find max reward cycle from the given source using DFS BackTracking 
	 * @param t - current Vertex to be processed
	 * @param curIndex - current Index of the currentPath array.
	 * @param e - current edge
	 * @param isStart - true, if this start vertex (only at start instance), else false.
	 * @param pathLen - current path length of the cycle.
	 * @param reward - current reward collected
	 */
	private void findMaxRewardCycle(RVertex t, int curIndex, Edge e,
			boolean isStart, long pathLen, int reward) {
		currentPath[curIndex--] = e;
		if (t.equals(source) && !isStart) {
			if (reward > maxReward) {

				saveMaxReward( curIndex, reward);

			}
		}
		boolean isReward = false;
		for (int i=0; i < t.badj.length ; i++) {
			Edge r = t.badj[i];
			RVertex toVertex = getVertex(r.otherEnd(t));

			if (!toVertex.isSeen()) {
				toVertex.seen();

				pathLen = pathLen + r.weight;
				if (pathLen == toVertex.distance) {
					reward = reward + toVertex.reward;
					isReward = true;
				}

				findMaxRewardCycle(toVertex, curIndex, r, false, pathLen, reward);

				pathLen = pathLen - r.weight;
				if (isReward) {
					reward = reward - toVertex.reward;
				}

				toVertex.unSeen();
			}

		}

	}

	/**Method used to store the max reward cycle 
	 * @param curIndex - current index of the current path array.
	 * @param reward - reward collected.
	 */
	private void saveMaxReward( int curIndex, int reward) {
		this.maxReward = reward;

		size = 0;
		RVertex prv = getVertex(currentPath[this.edgeSize() - 2].from);
		path[size++] = prv;

		for (int i = this.edgeSize() - 2; i > curIndex; i--) {
			Edge ee = currentPath[i];
			RVertex from = getVertex(ee.from);
			RVertex to = getVertex(ee.to);

			RVertex current;
			if (from.equals(prv)) {
				current = to;
			} else {
				current = from;
			}
			prv = current;

			path[size++] = current;
		}
	}

	/**copies the max reward cycle path into the given list
	 * @param tour - list where cycle path of vertices to be stored
	 */
	public void copyRewardCyclePath(List<Vertex> tour) {

		for (int i = 0; i < size; i++) {
			tour.add(path[i]);
		}
	}

}
