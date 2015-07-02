package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.*;
import asciigame.creatures.Creature;
import asciigame.creatures.CreatureFactory;
import asciigame.items.Item;
import asciigame.items.ItemFactory;

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
	private FieldOfView playerFov;

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
		ItemFactory.setWorld(world);
		playerFov = new FieldOfView(world);
		makeCreatures();
		makeItems();
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		displayTiles(terminal, left, top);
		displayMessages(terminal);
		String stats = player.getHealth() + "/" + player.getMaxHealth();
		terminal.write(stats, 1, terminal.getHeightInCharacters() - 2);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				return new WinScreen();
			case KeyEvent.VK_ESCAPE:
				return new LoseScreen("Game over");
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H:
				player.moveBy(0, -1, 0);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L:
				player.moveBy(0, 1, 0);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_J:
				player.moveBy(0, 0, -1);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_K:
				player.moveBy(0, 0, 1);
				break;
			case KeyEvent.VK_Y:
				player.moveBy(0, -1, -1);
				break;
			case KeyEvent.VK_U:
				player.moveBy(0, 1, -1);
				break;
			case KeyEvent.VK_B:
				player.moveBy(0, -1, 1);
				break;
			case KeyEvent.VK_N:
				player.moveBy(0, 1, 1);
				break;

			// Debug - instant layer change
			case KeyEvent.VK_Q:
				player.setZ(player.getZ() - 1);
				break;
			case KeyEvent.VK_W:
				player.setZ(player.getZ() + 1);
				break;
		}

		// For characters without KeyEvents (as far as I can tell)
		switch (key.getKeyChar()) {
			case '<':
				player.moveBy(-1, 0, 0);
				break;
			case '>':
				player.moveBy(1, 0, 0);
				break;
		}

        return update();
	}

    private Screen update() {
        world.update();

        // Check if player is dead
        if (player.getHealth() <= 0) {
            return new LoseScreen(messages.get(messages.size() - 2) + " You die.");
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
	}

    private void makeCreatures() {
        int depth = world.getDepth();
        int fungusAmount = 4;
        int batAmount = 10;

        // Make the player
        CreatureFactory.setLayer(0);
        player = CreatureFactory.makePlayer(messages, playerFov);

        for (int i = 0; i < depth; i++) {
            CreatureFactory.setLayer(i);
            int j;

            // Make fungi
            for (j = 0; j < fungusAmount; j++) {
                CreatureFactory.makeFungus();
            }

            // Make bats
            for (j = 0; j < batAmount; j++) {
                CreatureFactory.makeBat();
            }
        }
    }

	private void makeItems() {
		int depth = world.getDepth();
		int rockAmount = world.getWidth() * world.getHeight() / 20;

		for (int i = 0; i < depth; i++) {
			ItemFactory.setLayer(i);
			int j;

			// Make rocks
			for (j = 0; j < rockAmount; j++) {
				ItemFactory.makeRock();
			}
		}
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
		int worldZ, worldX, worldY, screenX, screenY;
		Creature creature;
		Item item;
		Tile tile;
		worldZ = player.getZ();
		playerFov.update(worldZ, player.getX(), player.getY(), player.getVisionRadius());

		// Iterate over the screen
		for (screenX = 0; screenX < rightMapBorder; screenX++) {
			for (screenY = 0; screenY < bottomMapBorder; screenY++) {
				worldX = screenX + left;
				worldY = screenY + top;

				if (player.canSee(worldZ, worldX, worldY)) {
					if (world.getCreature(worldZ, worldX, worldY) != null) {
						creature = world.getCreature(worldZ, worldX, worldY);
						terminal.write(creature.getGlyph(), screenX, screenY, creature.getColor());
					} else if (world.getItem(worldZ, worldX, worldY) != null) {
						item = world.getItem(worldZ, worldX, worldY);
						terminal.write(item.getGlyph(), screenX, screenY, item.getColor());
					} else {
						tile = world.getTile(worldZ, worldX, worldY);
						terminal.write(tile.getGlyph(), screenX, screenY, tile.getColor());
					}
				} else {

					// Draw unseen tiles
					terminal.write(playerFov.getTile(worldZ, worldX, worldY).getGlyph(), screenX, screenY, AsciiPanel.brightBlack);
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
