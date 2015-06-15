package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.*;
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
		// Define screen vars
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		rightMapBorder = screenWidth * 2 / 3;
		bottomMapBorder = screenHeight - 3;

		// Define messages
		messages = new ArrayList<>();
		messages.add("Welcome! Use the arrow keys to move your character. Move into walls to dig, and move into enemies to attack.");

		// Map shenanigans
		createWorld();
		CreatureFactory.setWorld(world);
		makeCreatures();
	}

	private void makeCreatures() {
		// Make the player
		CreatureFactory.setLayer(0);
		player = CreatureFactory.makePlayer(messages);

		// Make fungi
		int depth = world.getDepth();
		for (int i = 0; i < depth; i++) {
			CreatureFactory.setLayer(i);
			for (int j = 0; j < 4; j++) {
				CreatureFactory.makeFungus();
			}
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
				player.moveBy(0, -1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				player.moveBy(0, 1, 0);
				break;
			case KeyEvent.VK_UP:
				player.moveBy(0, 0, -1);
				break;
			case KeyEvent.VK_DOWN:
				player.moveBy(0, 0, 1);
				break;

			// Debug - instant layer change
			case KeyEvent.VK_Q:
				player.setZ(player.getZ() - 1);
				break;
			case KeyEvent.VK_W:
				player.setZ(player.getZ() + 1);
				break;
		}

		switch (key.getKeyChar()) {
			case '<':
				player.moveBy(-1, 0, 0);
				break;
			case '>':
				player.moveBy(1, 0, 0);
				break;
		}

		return this;
	}

	private void createWorld() {
		int depth = 5;
		int width = (int)(rightMapBorder * 1.5);
		int height = bottomMapBorder * 2;

		world = new WorldBuilder(depth, width, height)
				.makeWorld()
				.build();

		// Benchmarking world gen
		/*long start, end, average, times = 100;
		for (depth = 1; depth <= 500; depth++) {
			average = 0;

			for (int i = 0; i < times; i++) {
				start = System.currentTimeMillis();

				world = new WorldBuilder(depth, width, height)
						.makeWorld()
						.build();

				end = System.currentTimeMillis();
				average += (end - start);
			}

			average /= times;

			System.out.println("Depth: " + depth + " | Average time (ms) : " + average);
		}*/
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
		int worldZ, worldX, worldY;
		worldZ = player.getZ();
		for (int screenX = 0; screenX < rightMapBorder; screenX++) {
			for (int screenY = 0; screenY < bottomMapBorder; screenY++) {
				worldX = screenX + left;
				worldY = screenY + top;

				terminal.write(world.getTile(worldZ, worldX, worldY).glyph(), screenX, screenY, world.getTile(worldZ, worldX, worldY).color());
			}
		}

		// Iterate over creatures
		int screenX, screenY;
		for (Creature c : world.getCreatures()) {
			if (c.getZ() == worldZ) {
				screenX = c.getX() - left;
				screenY = c.getY() - top;
				if (screenX >= 0 && screenX < rightMapBorder
						&& screenY >= 0 && screenY < bottomMapBorder) {
					terminal.write(c.getGlyph(), screenX, screenY, c.getColor());
				}
			}
		}
	}

	private void displayMessages(AsciiPanel terminal) {
		int left = rightMapBorder + 1;
		int maxLength = screenWidth - left;
		String origMessage, message;

		// First, sort out all the messages so everything looks pretty
		for (int i = 0; i < messages.size(); i++) {
			origMessage = messages.get(i);
			message = trimString(origMessage, maxLength);
			messages.set(i, message);

			message = origMessage.substring(message.length());
			if (message.length() > 1) {
				messages.add(i + 1, message.trim());
			}

			if (messages.size() > screenHeight) {
				messages.remove(0);
				i--;
			}
		}

		// Then, print all the prettied-up messages
		for (int j = 0; j < messages.size(); j++) {
			message = messages.get(j);
			terminal.write(message, left, j);
		}
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
