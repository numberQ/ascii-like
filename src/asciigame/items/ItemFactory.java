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
}
