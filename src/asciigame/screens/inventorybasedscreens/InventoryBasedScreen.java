package asciigame.screens.inventorybasedscreens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.items.ItemPile;
import asciigame.screens.Screen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class InventoryBasedScreen implements Screen {

	private String letters;
	private Item[] items;
	protected Creature player;

	protected abstract String getVerb();
	protected abstract boolean isRelevant(Item item);
	protected abstract Screen use(Item item);

	public InventoryBasedScreen(Creature player) {
		this.letters = "abcdefghijklmnopqrstuvwxyz";
		this.items = player.getInventory().getItems();
		this.player = player;
	}

	public InventoryBasedScreen(Creature player, ItemPile pile) {
		this.letters = "abcdefghijklmnopqrstuvwxyz";
		this.items = new Item[pile.getPileSize()];
		this.player = player;

		for (int i = 0; i < items.length; i++) {
			items[i] = pile.getItem(i);
		}
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		List<String> relevantItems = getRelevantItems();
		int maxWidth = lengthOfLongestString(relevantItems) + 2;

		int x = 0;
		int y = 0;

		String question = "What would you like to " + getVerb() + "?";
		terminal.clear(' ', x++, y++, question.length() + 2, 3);
		terminal.write(question, x, y);

		x = 1;
		y = terminal.getHeightInCharacters() - relevantItems.size() - 2;

		terminal.clear(' ', x, y, maxWidth, relevantItems.size() + 2);
		for (String line : relevantItems) {
			y++;
			terminal.write(line, x, y);
		}

		terminal.repaint();
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		int choice = letters.indexOf(key.getKeyChar());

		// User quit manually
		if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			return null;
		}

		// User did not enter a valid option
		if (choice < 0 || choice >= items.length) {
			return this;
		}

		// User selected empty inventory slot
		if (items[choice] == null) {
			return this;
		}

		// Selected item cannot be verbed
		if (!isRelevant(items[choice])) {
			return this;
		}

		return use(items[choice]);
	}

	private List<String> getRelevantItems() {
		List<String> relevantItems = new ArrayList<>();
		Item itemToCheck;
		String line;

		for (int i = 0; i < items.length; i++) {
			itemToCheck = items[i];
			if (itemToCheck != null && isRelevant(itemToCheck)) {
				line = letters.charAt(i) + " - (" + itemToCheck.getGlyph() + ") " + itemToCheck.getName();

				// Identify equipped items
				if (player.getWeapon() == itemToCheck || player.getArmor() == itemToCheck) {
					line += " (equipped)";
				}

				// Identify broken items
				if (itemToCheck.getDurability() <= 0 && itemToCheck.getDurabilityMax() > 0) {
					line += " (broken)";
				}

				relevantItems.add(line);
			}
		}

		return relevantItems;
	}

	private int lengthOfLongestString(List<String> strings) {
		int maxLength = 0, curLength;
		for (String string : strings) {
			curLength = string.length();
			if (curLength > maxLength) {
				maxLength = curLength;
			}
		}
		return maxLength;
	}
}
