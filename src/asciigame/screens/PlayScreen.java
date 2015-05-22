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

	private static List<String> messages;

	public static void addMessage(String message) { messages.add(message); }

	private int screenWidth;
	private int screenHeight;
	private int leftMapBorder;
	private int bottomMapBorder;
	private World world;
	private Creature player;

	public PlayScreen() {
		messages = new ArrayList<>();
		addMessage("Welcome!");
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		leftMapBorder = screenWidth * 2 / 3;
		bottomMapBorder = screenHeight - 3;
		createWorld();
		CreatureFactory.setWorld(world);
		makeCreatures();
	}

	private void makeCreatures() {
		// Make the player
		player = CreatureFactory.makePlayer();

		// Make fungi
		for (int i = 0; i < 8; i++) {
			CreatureFactory.makeFungus();
		}
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		displayTiles(terminal, left, top);
		displayMessages(terminal);
		world.update();
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
		int width = (int)(leftMapBorder * 1.5);
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
		int border = Math.min(player.getX() - (leftMapBorder / 2), world.getWidth() - leftMapBorder);
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
		for (int screenX = 0; screenX < leftMapBorder; screenX++) {
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
			if (screenX >= 0 && screenX < leftMapBorder
					&& screenY >= 0 && screenY < bottomMapBorder) {
				terminal.write(c.getGlyph(), screenX, screenY, c.getColor());
			}
		}
	}

	private void displayMessages(AsciiPanel terminal) {
		int messageX = leftMapBorder + 1;
		int messageY = 1;
		for (String message : messages) {
			message = trimString(message, screenWidth - messageX);
			terminal.write(message, messageX, messageY);
			messageY++;
		}
	}

	private String trimString(String string, int maxLength) {
		/**
		 * Make messages a Deque. If a string is too long,
		 * cut off the too long bit and add it to the head of the Deque.
		 */
	}
}
