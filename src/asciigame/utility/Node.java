package asciigame.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node extends Point implements Comparator<Node> {

	private Node parent;
	public Node getParent() 		   { return parent; }
	public void setParent(Node parent) { this.parent = parent; }

	private int realCost;
	public int getRealCost()		   { return realCost; }
	public void setRealCost(int cost) { this.realCost = cost;}

	private int heuristicCost;
	public void setHeuristicCost(int cost) { this.heuristicCost = cost; }

	public Node(int z, int x, int y) {
		super(z, x, y);
	}

	public int getTotalCost() {
		return realCost + heuristicCost;
	}

	@Override
	public int compare(Node node1, Node node2) {
		return node1.getTotalCost() - node2.getTotalCost();
	}

	public List<Node> getAdjacentAsNodes() {
		List<Node> nodes = new ArrayList<>();
		Node node;

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (!(dx == 0 && dy == 0)) {
					node = new Node(z, x + dx, y + dy);
					nodes.add(node);
				}
			}
		}

		// Shuffle so the first point isn't always the top left one
		Collections.shuffle(nodes);

		return nodes;
	}
}
