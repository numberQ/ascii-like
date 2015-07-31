package asciigame.utility;

import asciigame.creatures.Creature;

public class Path {

	private Node node;

	public Path(Point start, Point end, Creature creature) {
		PathFinder.initPath(start, end);
		node = PathFinder.findPath(creature);
	}

	public Node getNextNode() {
		if (node.getParent() == null) {
			return null;
		}

		while (node.getParent().getParent() != null) {
			node = node.getParent();
		}

		return node;
	}
}
