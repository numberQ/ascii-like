package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.items.ItemPile;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class InventoryBasedScreen implements Screen {

	private String letters;
	private Item[] items;
	protected Creature player;

	protected abstract String getVerb();
	protected abstract boolean isAcceptable(Item item);
	protected abstract Screen use(Item item);

	public InventoryBasedScreen(Creature player) {
		this.letters = "abcdefghijklmnopqrstuvwxyz";
		this.items = player.getInventory().getItems();
		this.player = player;
	}

	public InventoryBasedScreen(Creature player, ItemPile pile) {
		this.letters = "abcdefghijklmnopqrstuvwxyz";
		this.items = new Item[pile.getItems().size()];
		this.player = player;

		for (int i = 0; i < items.length; i++) {
			items[i] = pile.getItem(i);
		}
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		List<String> acceptable = getAcceptable();
		int maxWidth = lengthOfLongestString(acceptable) + 2;

		int x = 1;
		int y = 1;

		String question = "What would you like to " + getVerb() + "?";
		terminal.clear(' ', 0, 0, question.length(), 3);
		terminal.write(question, x, y);

		x = 1;
		y = terminal.getHeightInCharacters() - acceptable.size() - 2;

		terminal.clear(' ', x, y, maxWidth, acceptable.size() + 2);
		for (String line : acceptable) {
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
		if (!isAcceptable(items[choice])) {
			return this;
		}

		return use(items[choice]);
	}

	private List<String> getAcceptable() {
		List<String> acceptable = new ArrayList<>();
		Item itemToCheck;
		String line;

		for (int i = 0; i < items.length; i++) {
			itemToCheck = items[i];
			if (itemToCheck != null && isAcceptable(itemToCheck)) {
				line = letters.charAt(i) + " - (" + itemToCheck.getGlyph() + ") " + itemToCheck.getName();
				acceptable.add(line);
			}
		}

		return acceptable;
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
