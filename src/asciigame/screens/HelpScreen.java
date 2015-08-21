package asciigame.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class HelpScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();

		int x = 1;
		int y = 1;

		terminal.writeCenter("Help", y++);

		String message = "Can you make it to the fifth level of the Caves of Slight Danger and recover the magical MacGuffin? " +
				"Use what you find to destroy your enemies (by walking into them)! You'll need to balance health, stats, and hunger to survive.";
		String origMessage;

		while (!message.isEmpty()) {
			origMessage = message;
			message = trimString(message, terminal.getWidthInCharacters() - 2);

			terminal.write(message, x, ++y);

			message = origMessage.substring(message.length()).trim();
		}

		y++;

		terminal.write("[?] to access this help menu", x, ++y);
		terminal.write("[h] to move left, [l] to move right, [j] to move up, [k] to move down", x, ++y);
		terminal.write("[y] to move up-left, [u] to move up-right", x, ++y);
		terminal.write("[b] to move down-left, [n] to move down-right", x, ++y);
		terminal.write("[z] to wait in place", x, ++y);
		terminal.write("[<] to go upstairs", x, ++y);
		terminal.write("[>] to go downstairs", x, ++y);
		terminal.write("[g] to pick up", x, ++y);
		terminal.write("[d] to drop", x, ++y);
		terminal.write("[w] to equip or unequip", x, ++y);
		terminal.write("[e] to eat", x, ++y);
		terminal.write("[x] to examine your inventory", x, ++y);
		terminal.write("[;] to look around (not implemented)", x, ++y);
		terminal.write("[c] to access character sheet", x, ++y);
		terminal.write("[m] to access monster bestiary (not implemented)", x, ++y);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		return null;
	}

	private String trimString(String message, int maxLength) {
		message = message.trim();

		// If the message is already short enough, return it
		if (message.length() <= maxLength) {
			return message;
		}

		// Truncate at maxLength and return if it ends at a word
		message = message.substring(0, maxLength);
		if (message.endsWith(" ")) {
			return message.trim();
		}

		// Count backwards until we find the end of a word, then truncate there
		int idx = maxLength;
		while (--idx >= 0) {
			if (message.charAt(idx) == ' ') {
				return message.substring(0, idx).trim();
			}
		}

		// This means we've counted to the start of the message and found no spaces - just return the message up to maxLength
		return message.substring(0, maxLength);
	}
}
