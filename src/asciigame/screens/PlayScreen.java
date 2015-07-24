package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.*;
import asciigame.creatures.Creature;
import asciigame.creatures.CreatureFactory;
import asciigame.items.Item;
import asciigame.items.ItemFactory;
import asciigame.items.ItemPile;
import asciigame.FieldOfView;

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
	private Screen subscreen;

	public PlayScreen() {
		// Define screen vars
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		rightMapBorder = screenWidth * 2 / 3;
		bottomMapBorder = screenHeight - 3;

		// Define messages
		messages = new ArrayList<>();
		messages.add("HJKL to move your character, YUBN for diagonals. Move into enemies to attack. G to pick up items, D to drop them. " +
				"Collect the MacGuffin and return with it to win!");

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
		String stats = player.getHealth() + "/" + player.getMaxHealth() + " " + player.hungerLevel();
		terminal.write(stats, 1, terminal.getHeightInCharacters() - 2);

		// Display subscreen on top of current screen
		if (subscreen != null) {
			subscreen.displayOutput(terminal);
		}
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {

		// Divert keyboard control to a subscreen if it exists
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInputAndUpdate(key);
			return this;
		}

		// Respond to the input
		switch (key.getKeyCode()) {

			// Movement
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

			// Pick up items
			case KeyEvent.VK_G:
				if (world.hasNoItems(player.getZ(), player.getX(), player.getY())) {
					player.sayAction("grab at the ground, but nothing is there");
					break;
				}
				ItemPile items = world.getItems(player.getZ(), player.getX(), player.getY());
				subscreen = new PickUpScreen(player, items);
				break;

			// Drop items
			case KeyEvent.VK_D:
				subscreen = new DropScreen(player);
				break;

			// Eat items
			case KeyEvent.VK_E:
				subscreen = new EatScreen(player);
				break;

			// Equip/unequip items
			case KeyEvent.VK_W:
				subscreen = new EquipScreen(player);
				break;



			// Debug - instant layer change
			case KeyEvent.VK_MINUS:
				player.setZ(player.getZ() - 1);
				break;
			case KeyEvent.VK_PLUS:
				player.setZ(player.getZ() + 1);
				break;
			case KeyEvent.VK_BACK_SPACE:
				player.getInventory().add(new Item ('/', AsciiPanel.brightGreen, "pick"));
		}

		// For characters without KeyEvents (as far as I can tell)
		switch (key.getKeyChar()) {
			case '<':
				if (playerIsExiting()) {
					return playerExits();
				} else {
					player.moveBy(-1, 0, 0);
					break;
				}
			case '>':
				player.moveBy(1, 0, 0);
				break;
		}

		return update();
	}

	private boolean playerIsExiting() {
		if (world.getTile(player.getZ(), player.getX(), player.getY()) != Tile.STAIRS_UP) {
			return false;
		}
		return player.getZ() == 0;
	}

	private Screen playerExits() {
		if (player.hasItem("MacGuffin")) {
			return new WinScreen();
		}
		return new LoseScreen("You return from the cave empty-handed. Despair engulfs you. You die.");
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
        int fungusAmount = 8;
        int batAmount = 4;
		int zombieAmount = 2;

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

			// Make zombies
			for (j = 0; j < zombieAmount + i; j++) {
				CreatureFactory.makeZombie(player);
			}
        }
    }

	private void makeItems() {
		int depth = world.getDepth();
		int worldSize = world.getWidth() * world.getHeight();
		int rockAmount = worldSize / 25;
		int foodAmount = worldSize / 80;
		int weaponAmount = worldSize / 550;
		int armorAmount = worldSize / 550;

		// Make victory item
		ItemFactory.setLayer(depth);
		ItemFactory.makeVictoryItem();

		for (int i = 0; i < depth; i++) {
			ItemFactory.setLayer(i);
			int j;

			// Make rocks
			for (j = 0; j < rockAmount; j++) {
				ItemFactory.makeRock();
			}

			// Make food
			for (j = 0; j < foodAmount; j++) {
				ItemFactory.makeRandomFood();
			}

			// Make weapons
			for (j = 0; j < weaponAmount; j++) {
				ItemFactory.makeRandomWeapon();
			}

			// Make armor
			for (j = 0; j < armorAmount; j++) {
				ItemFactory.makeRandomArmor();
			}
		}

		// Make pick
		int pickAmount = (int)(Math.random() * (depth - 1)) + 1;
		for (int j = 0; j < pickAmount; j++) {
			depth = (int) (Math.random() * depth);
			ItemFactory.setLayer(depth);
			ItemFactory.makePick();
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
					} else if (!world.hasNoItems(worldZ, worldX, worldY)) {
						item = world.getTopItem(worldZ, worldX, worldY);
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
