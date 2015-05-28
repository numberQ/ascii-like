package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.ApplicationMain;
import asciigame.World;
import asciigame.WorldBuilder;
import asciigame.creatures.Creature;
import asciigame.creatures.CreatureFactory;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Deque;

public class PlayScreen implements Screen {

	private static Deque<String> messages;
    private static Deque<String> toDisplay;

	public static void addMessage(String message) { messages.addLast(message); }

	private int screenWidth;
	private int screenHeight;
	private int rightMapBorder;
	private int bottomMapBorder;
	private World world;
	private Creature player;

	public PlayScreen() {
		messages = new ArrayDeque<>();
		toDisplay = new ArrayDeque<>();
		addMessage("Welcome!");
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		rightMapBorder = screenWidth * 2 / 3;
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
		int messageX = rightMapBorder + 1;
		int messageY = 1;
        String message;
        toDisplay = new ArrayDeque<>(messages);

		while (!toDisplay.isEmpty()) {
			message = toDisplay.removeFirst();
            message = trimString(message, screenWidth - messageX);
			terminal.write(message, messageX, messageY);
			messageY++;

            if (messageY > screenHeight - 2) {
                toDisplay.clear();
                messages.clear();
            }
		}
	}

	private String trimString(String string, int maxLength) {

        // Don't do anything if the string is already short enough
        if (string.length() > maxLength) {

            // Determine word breaks
            String[] splitBySpace = string.split(" ");
            if (splitBySpace.length == 1) {

                // If there are no spaces, just truncate the word at maxLength
                string = string.substring(0, maxLength);
                toDisplay.addFirst(string.substring(maxLength));
            } else {

                // Gather every word that fits until the word that takes it over maxLength
                int i = 0;
                int sum = 0;
                string = "";
                do {
                    string += splitBySpace[i] + " ";
                    sum += splitBySpace[i].length() + 1;
                    i++;
                } while (i < splitBySpace.length && sum < maxLength);

                // Remove that last word
                i--;
                sum -= splitBySpace[i].length() + 1;
                string = string.substring(0, sum);

                // Stitch together remaining words, if any, and push them back on the Deque
                String remaining = "";
                for (int j = i; j < splitBySpace.length; j++) {
                    remaining += splitBySpace[j] + " ";
                }
                toDisplay.addFirst(remaining);
            }
        }

		return string;
	}
}
