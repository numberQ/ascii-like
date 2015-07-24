package asciigame.utility;

public class Node extends Point {

	private Node parent;
	public Node getParent() 		   { return parent; }
	public void setParent(Node parent) { this.parent = parent; }

	private int totalCost;

	public Node (int z, int x, int y) {
		super(z, x, y);
	}
}
