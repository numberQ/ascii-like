package asciigame.utility;

import asciigame.creatures.Creature;
import java.util.*;

public class PathFinder {

	private static Queue<Node> open;
	private static List<Node> closed;
	private static Node startNode;
	private static Node endNode;

	public static void initPath(Point start, Point end) {

		// Create start and end nodes
		startNode = new Node(start.getZ(), start.getX(), start.getY());
		endNode = new Node(end.getZ(), end.getX(), end.getY());

		// Set exact distance from start
		startNode.setRealCost(0);

		// Set heuristic distances from end
		Line optimalPath = new Line(start, end);
		startNode.setHeuristicCost(getHeuristicDistance(startNode, endNode));
		endNode.setHeuristicCost(0);

		// Set up lists of checked and to-be-checked nodes
		Comparator<Node> nodeComparator = (node1, node2) -> node1.getTotalCost() - node2.getTotalCost();
		open = new PriorityQueue<>(1, nodeComparator);
		closed = new ArrayList<>();

		open.add(startNode);
	}

	// Returns the start node, which can be followed through parents to construct the path
	public static Node findPath(Creature creature) {
		Node node = null;
		int cost;

		while (!endNode.equals(node) && open.size() > 0) {
			node = open.remove();
			closed.add(node);

			for (Node n : node.getAdjacentAsNodes()) {
				cost = node.getRealCost() + 1;
				if (open.contains(n) && cost < n.getRealCost()) {

					// New path is better
					open.remove(n);

				} else if (closed.contains(n) && cost < n.getRealCost()) {

					// Likely won't happen
					closed.remove(n);

				} else if (!open.contains(n) && !closed.contains(n) && creature.canEnter(creature.getZ(), n.x, n.y)) {
					n.setRealCost(cost);
					n.setHeuristicCost(getHeuristicDistance(n, endNode));
					open.add(n);
					n.setParent(node);
				}
			}
		}

		return endNode;
	}

	private static int getHeuristicDistance(Node node1, Node node2) {
		Line optimalPath = new Line(node1, node2);
		return optimalPath.size() - 1;
	}
}
