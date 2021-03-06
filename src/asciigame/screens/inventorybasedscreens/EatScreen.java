package asciigame.screens.inventorybasedscreens;

import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.screens.Screen;

public class EatScreen extends InventoryBasedScreen {

	public EatScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "eat";
	}

	@Override
	protected boolean isRelevant(Item item) {
		return item.getNutrition() > 0;
	}

	@Override
	protected Screen use(Item item) {
		player.eat(item);
		return null;
	}
}
