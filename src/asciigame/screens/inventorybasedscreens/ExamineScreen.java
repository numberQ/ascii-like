package asciigame.screens.inventorybasedscreens;

import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.screens.Screen;

public class ExamineScreen extends InventoryBasedScreen {

	public ExamineScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "examine";
	}

	@Override
	protected boolean isRelevant(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.notify(item.details());

		return null;
	}
}
