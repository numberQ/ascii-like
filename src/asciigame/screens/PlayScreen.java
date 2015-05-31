package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.ApplicationMain;
import asciigame.World;
import asciigame.WorldBuilder;
import asciigame.creatures.Creature;
import asciigame.creatures.CreatureFactory;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

	private int screenWidth;
	private int screenHeight;
	private int rightMapBorder;
	private int bottomMapBorder;
	private World world;
	private List<String> messages;
	private Creature player;

	public PlayScreen() {
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		rightMapBorder = screenWidth * 2 / 3;
		bottomMapBorder = screenHeight - 3;
		createWorld();
		CreatureFactory.setWorld(world);
		messages = new ArrayList<>();
		makeCreatures();
		messages.add("Welcome! Use the arrow keys to move you character. Move into walls to dig, and move into enemies to attack.");
	}

	private void makeCreatures() {
		// Make the player
		player = CreatureFactory.makePlayer(messages);

		// Make fungi
		for (int i = 0; i < 8; i++) {
			CreatureFactory.makeFungus();
		}
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		world.update();
		displayTiles(terminal, left, top);
		displayMessages(terminal);
		String stats = player.getHealth() + "/" + player.getMaxHealth();
		terminal.write(stats, 1, terminal.getHeightInCharacters() - 2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				return new WinScreen();
			case KeyEvent.VK_ESCAPE:
				return new LoseScreen();
			case KeyEvent.VK_LEFT:
				player.moveBy(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				player.moveBy(1, 0);
				break;
			case KeyEvent.VK_UP:
				player.moveBy(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				player.moveBy(0, 1);
				break;
		}

		return this;
	}

	private void createWorld() {
		int width = (int)(rightMapBorder * 1.5);
		int height = bottomMapBorder * 2;
		world = new WorldBuilder(width, height)
				.makeWorld()
				.build();
	}

	/**
	 * Finds the X coordinate of the top left corner of the scroll window.
	 * The left, in other words.
	 * @return - The X coord.
	 */
	public int getScrollX() {
		int border = Math.min(player.getX() - (rightMapBorder / 2), world.getWidth() - rightMapBorder);
		return Math.max(0, border);
	}

	/**
	 * Finds the Y coordinate of the top left corner of the scroll window.
	 * The top, in other words.
	 * @return - The Y coord.
	 */
	public int getScrollY() {
		int border = Math.min(player.getY() - (bottomMapBorder / 2), world.getHeight() - bottomMapBorder);
		return Math.max(0, border);
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		// Iterate over the screen
		for (int screenX = 0; screenX < rightMapBorder; screenX++) {
			for (int screenY = 0; screenY < bottomMapBorder; screenY++) {
				int worldX = screenX + left;
				int worldY = screenY + top;

				terminal.write(world.getTile(worldX, worldY).glyph(), screenX, screenY, world.getTile(worldX, worldY).color());
			}
		}

		// Iterate over creatures
		for (Creature c : world.getCreatures()) {
			int screenX = c.getX() - left;
			int screenY = c.getY() - top;
			if (screenX >= 0 && screenX < rightMapBorder
					&& screenY >= 0 && screenY < bottomMapBorder) {
				terminal.write(c.getGlyph(), screenX, screenY, c.getColor());
			}
		}
	}

	private void displayMessages(AsciiPanel terminal) {
		//int top = screenHeight - messages.size();
		int left = rightMapBorder + 1;
		int maxLength = screenWidth - left;
		String origMessage, message;

		for (int i = 0; i < messages.size(); i++) {
			origMessage = messages.get(i);
			message = trimString(origMessage, maxLength);
			messages.set(i, message);
			terminal.write(message, left, i);

			message = origMessage.substring(message.length());
			if (message.length() > 1) {
				messages.add(i + 1, message.trim());
			}
		}

		messages.clear();
	}

	private String trimString(String message, int maxLength) {
		message = message.trim();

		// Message is already short enough
		if (message.length() < maxLength) {
			return message.trim();
		}

		// Truncate message exactly at maxLength, and check if it ends at the end of a word
		String trimmed = message.substring(0, maxLength - 1);
		if (trimmed.endsWith(" ") || message.charAt(trimmed.length()) == ' ') {
			return trimmed.trim();
		}

		// Otherwise, truncate to the last word (words separated by spaces, obviously)
		trimmed = trimmed.substring(0, trimmed.lastIndexOf(' '));
		return trimmed.trim();
	}
}
