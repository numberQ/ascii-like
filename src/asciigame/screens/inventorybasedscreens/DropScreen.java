package asciigame.screens.inventorybasedscreens;

import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.screens.Screen;

public class DropScreen extends InventoryBasedScreen {

	public DropScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "drop";
	}

	@Override
	protected boolean isRelevant(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.drop(item);
		return null;
	}
}
