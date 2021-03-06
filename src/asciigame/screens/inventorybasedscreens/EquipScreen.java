package asciigame.screens.inventorybasedscreens;

import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.screens.Screen;

public class EquipScreen extends InventoryBasedScreen {

	public EquipScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "wear or wield";
	}

	@Override
	protected boolean isRelevant(Item item) {
		return (item.getAttack() > 0 || item.getDefense() > 0) && item.getDurability() > 0;
	}

	@Override
	protected Screen use(Item item) {
		if (player.getWeapon() == item || player.getArmor() == item) {
			player.unequip(item);
		} else {
			player.equip(item);
		}
		return null;
	}
}
