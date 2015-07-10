package asciigame.items;

import asciiPanel.AsciiPanel;
import asciigame.World;

public class ItemFactory {

	private static World world;
	private static int layer;

	public static void setWorld(World world) { ItemFactory.world = world; }
	public static void setLayer(int layer) { ItemFactory.layer = layer; }

	public static Item makeVictoryItem() {
		Item victoryItem = new Item('*', AsciiPanel.brightYellow, "MacGuffin", 0);
		world.addAtEmptyLocation(victoryItem, world.getDepth() - 1);
		return victoryItem;
	}

	public static Item makeRock() {
		Item rock = new Item(',', AsciiPanel.yellow, "rock", 0);
		world.addAtEmptyLocation(rock, layer);
		return rock;
	}

	public static Item makePick() {
		Item pick = new Item ('/', AsciiPanel.brightCyan, "pick", 0);
		world.addAtEmptyLocation(pick, layer);
		return pick;
	}

	public static Item makeCheeseSteak() {
		Item cheeseSteak = new Item('c', AsciiPanel.yellow, "cheese steak", 150);
		world.addAtEmptyLocation(cheeseSteak, layer);
		return cheeseSteak;
	}

	public static Item makeSpaghetti() {
		Item spaghetti = new Item('s', AsciiPanel.yellow, "spaghetti", 140);
		world.addAtEmptyLocation(spaghetti, layer);
		return spaghetti;
	}

	public static Item makeGranolaBar() {
		Item granolaBar = new Item('g', AsciiPanel.yellow, "granola bar", 50);
		world.addAtEmptyLocation(granolaBar, layer);
		return granolaBar;
	}

	public static Item makeAmbrosia() {
		Item ambrosia = new Item('A', AsciiPanel.brightYellow, "ambrosia", 1000);
		world.addAtEmptyLocation(ambrosia, layer);
		return ambrosia;
	}
}
