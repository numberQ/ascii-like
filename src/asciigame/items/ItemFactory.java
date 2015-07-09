package asciigame.items;

import asciiPanel.AsciiPanel;
import asciigame.World;

public class ItemFactory {

	private static World world;
	private static int layer;

	public static void setWorld(World world) { ItemFactory.world = world; }
	public static void setLayer(int layer) { ItemFactory.layer = layer; }

	public static Item makeRock() {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, layer);
		return rock;
	}

	public static Item makePick() {
		Item pick = new Item ('/', AsciiPanel.brightCyan, "pick");
		world.addAtEmptyLocation(pick, layer);
		return pick;
	}

	public static Item makeVictoryItem() {
		Item victoryItem = new Item('*', AsciiPanel.brightYellow, "shiny rock");
		world.addAtEmptyLocation(victoryItem, world.getDepth() - 1);
		return victoryItem;
	}
}
