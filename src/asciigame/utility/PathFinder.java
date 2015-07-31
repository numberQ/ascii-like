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
		startNode.setHeuristicCost(getHeuristicDistance(startNode, endNode));
		endNode.setHeuristicCost(0);

		// Set up comparator for nodes
		Comparator<Node> nodeComparator = (n1, n2) -> {
			int prime = 13;

			// Factor heuristic distances from doubles to ints,
			// using a prime to retain uniqueness.

			double heuristicCompare = n1.getTotalCost() * prime - n2.getTotalCost() * prime;
			return (int)heuristicCompare;
		};

		open = new PriorityQueue<>(1, nodeComparator);
		closed = new ArrayList<>();

		open.add(startNode);
	}

	// Returns the end node, which can be followed through parents to construct the path
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

					// We've reached the end, so give endNode its properties
					if (endNode.equals(n)) {
						endNode.setRealCost(cost);
						endNode.setHeuristicCost(0);
						endNode.setParent(node);
					}
				}
			}
		}

		return endNode;
	}

	private static double getHeuristicDistance(Node n1, Node n2) {

		// Create a true line, not a grid-based line.
		// This will make straight paths preferable to wobbly ones.

		int dx = Math.abs(n2.x - n1.x);
		int dy = Math.abs(n2.y - n1.y);
		return Math.sqrt(dx * dx + dy * dy);
	}
}
