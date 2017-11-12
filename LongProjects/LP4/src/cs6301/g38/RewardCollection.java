package cs6301.g38;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

public class RewardCollection extends Graph {

	public static class RVertex extends Vertex {
		boolean seen;
		List<REdge> badj;
		long distance = Long.MAX_VALUE;
		int reward;
		int count;

		RVertex(Vertex u) {
			super(u);
			seen = false;
			badj = new LinkedList<>();

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
	private RVertex source;
	private RVertex[] path;
	int maxReward;
	private int size;

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
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				RVertex x1 = getVertex(u);
				RVertex x2 = getVertex(v);
				x1.badj.add(new REdge(x1, x2, e.weight));
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

	public int findMaxRewardPath() {
		if (bellmanFord()) {

			Edge[] ee = new Edge[this.edgeSize()];
			dfs(source, ee, this.edgeSize() - 1, null, true, 0,
					getVertex(source).reward);
			return this.maxReward;
		} else {
			return 0;
		}
	}

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

	private void dfs(RVertex t, Edge[] currentPath, int curLen, Edge e,
			boolean isStart, long pathLen, int reward) {
		currentPath[curLen--] = e;
		if (t.equals(source) && !isStart) {
			if (reward > maxReward) {

				checkMaxReward(currentPath, curLen, reward);

			}
		}
		boolean isReward = false;
		for (Edge r : t.adj) {
			RVertex toVertex = getVertex(r.otherEnd(t));

			if (!toVertex.isSeen()) {
				toVertex.seen();

				pathLen = pathLen + r.weight;
				if (pathLen == toVertex.distance) {
					reward = reward + toVertex.reward;
					isReward = true;
				}

				dfs(toVertex, currentPath, curLen, r, false, pathLen, reward);

				pathLen = pathLen - r.weight;
				if (isReward) {
					reward = reward - toVertex.reward;
				}

				toVertex.unSeen();
			}

		}

	}

	private void checkMaxReward(Edge[] currentPath, int curLen, int reward) {
		this.maxReward = reward;

		size = 0;
		RVertex prv = getVertex(currentPath[this.edgeSize() - 2].from);
		path[size++] = prv;

		for (int i = this.edgeSize() - 2; i > curLen; i--) {
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

	public void copyRewardPath(List<Vertex> tour) {

		for (int i = 0; i < size; i++) {
			tour.add(path[i]);
		}
	}

}
