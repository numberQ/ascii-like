package asciigame.utility;

import asciigame.creatures.Creature;
import java.util.Comparator;

public class Path {

	private Node node;

	public Path(Point start, Point end, Comparator<Node> heuristicComparator, Creature creature) {
		PathFinder.initPath(start, end);
		PathFinder.initPathData(heuristicComparator);
		node = PathFinder.findPath(creature);
	}

	public Node getNextNode() {
		if (node == null || node.getParent() == null) {
			return null;
		}

		while (node.getParent().getParent() != null) {
			node = node.getParent();
		}

		return node;
	}
}
