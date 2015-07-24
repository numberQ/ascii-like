package asciigame.screens;

import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.items.ItemPile;

public class PickUpScreen extends InventoryBasedScreen {

	public PickUpScreen(Creature player, ItemPile items) {
		super(player, items);
	}

	@Override
	protected String getVerb() {
		return "pick up";
	}

	@Override
	protected boolean isRelevant(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.pickup(item);
		return null;
	}
}
