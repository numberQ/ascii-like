package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.*;
import asciigame.creatures.Creature;
import asciigame.creatures.CreatureFactory;
import asciigame.items.Item;
import asciigame.items.ItemFactory;
import asciigame.items.ItemPile;
import asciigame.FieldOfView;
import asciigame.screens.inventorybasedscreens.*;

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
		subscreen = new HelpScreen();

		// Define messages
		messages = new ArrayList<>();
		messages.add("Collect the MacGuffin and return with it to win!");

		// Map shenanigans
		createWorld();
		CreatureFactory.setWorld(world);
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
		String stats = "Health: " + player.getHealth() + "/" + player.getMaxHealth() + " " + player.hungerLevel() +
				" XP: " + player.getXp() + "/" + player.nextXpThreshold() + ", level " + player.getLevel();
		terminal.write(stats, 1, terminal.getHeightInCharacters() - 2);

		// Display subscreen on top of current screen
		if (subscreen != null) {
			subscreen.displayOutput(terminal);
		}
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {

		// Record user's level so we can compare and see if it changed later
		int level = player.getLevel();

		// Decides whether the world gets updated
		// This will be set to true for key presses that update the world
		boolean doUpdate = false;

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
				doUpdate = true;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L:
				player.moveBy(0, 1, 0);
				doUpdate = true;
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_J:
				player.moveBy(0, 0, -1);
				doUpdate = true;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_K:
				player.moveBy(0, 0, 1);
				doUpdate = true;
				break;
			case KeyEvent.VK_Y:
				player.moveBy(0, -1, -1);
				doUpdate = true;
				break;
			case KeyEvent.VK_U:
				player.moveBy(0, 1, -1);
				doUpdate = true;
				break;
			case KeyEvent.VK_B:
				player.moveBy(0, -1, 1);
				doUpdate = true;
				break;
			case KeyEvent.VK_N:
				player.moveBy(0, 1, 1);
				doUpdate = true;
				break;

			// Wait
			case KeyEvent.VK_Z:
				doUpdate = true;
				break;

			// Pick up items
			case KeyEvent.VK_G:
				if (world.hasNoItems(player.getZ(), player.getX(), player.getY())) {
					player.sayAction("grab at the ground, but nothing is there");
					break;
				}
				ItemPile items = world.getItems(player.getZ(), player.getX(), player.getY());
				subscreen = new PickUpScreen(player, items);
				doUpdate = true;
				break;

			// Drop items
			case KeyEvent.VK_D:
				subscreen = new DropScreen(player);
				doUpdate = true;
				break;

			// Eat items
			case KeyEvent.VK_E:
				subscreen = new EatScreen(player);
				doUpdate = true;
				break;

			// Equip/unequip items
			case KeyEvent.VK_W:
				subscreen = new EquipScreen(player);
				doUpdate = true;
				break;

			// Check character sheet
			case KeyEvent.VK_C:
				subscreen = new CharacterScreen(player);
				doUpdate = false;
				break;

			// Examine items
			case KeyEvent.VK_X:
				subscreen = new ExamineScreen(player);
				doUpdate = true;
				break;



			// Debug
			case KeyEvent.VK_MINUS:
				player.setZ(player.getZ() - 1);
				break;
			case KeyEvent.VK_PLUS:
				player.setZ(player.getZ() + 1);
				break;
			case KeyEvent.VK_BACK_SPACE:
				Item pick = ItemFactory.makePick();
				player.getInventory().add(pick);
				break;
		}

		// For characters without KeyEvents (as far as I can tell)
		switch (key.getKeyChar()) {

			// Stairs
			case '<':
				if (playerIsExiting()) {
					return playerExits();
				} else {
					player.moveBy(-1, 0, 0);
					doUpdate = true;
					break;
				}
			case '>':
				player.moveBy(1, 0, 0);
				doUpdate = true;
				break;

			// Check help
			case '?':
				subscreen = new HelpScreen();
				doUpdate = false;
				break;
		}

		// Check for level up
		if (player.getLevel() > level) {
			subscreen = new LevelUpScreen(player, player.getLevel() - level);
		}

		if (doUpdate) {
			return update();
		}
		return this;
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

		player.notify("You cannot leave without the magic MacGuffin!");
		return this;
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
		int width = (int) (rightMapBorder * 1.5);
		int height = bottomMapBorder * 2;

		world = new WorldBuilder(depth, width, height)
				.makeWorld()
				.build();
	}

    private void makeCreatures() {
        int depth = world.getDepth();
        int fungusAmount = 8;
        int batAmount = 4;
		int zombieAmount = 1;
		Creature creature;

        // Make the player
        player = CreatureFactory.makePlayer(messages, playerFov);
		world.addPlayer(player);

        for (int i = 0; i < depth; i++) {
            int j;

            // Make fungi
            for (j = 0; j < fungusAmount; j++) {
                creature = CreatureFactory.makeFungus();
				world.addAtEmptyLocation(creature, i);
            }

            // Make bats
            for (j = 0; j < batAmount; j++) {
                creature = CreatureFactory.makeBat();
				world.addAtEmptyLocation(creature, i);
            }

			// Make zombies
			for (j = 0; j < zombieAmount + i; j++) {
				creature = CreatureFactory.makeZombie(player);
				world.addAtEmptyLocation(creature, i);
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
		Item item;

		// Make victory item
		item = ItemFactory.makeVictoryItem();
		world.addAtEmptyLocation(item, depth - 1);

		for (int i = 0; i < depth; i++) {
			int j;

			// Make rocks
			for (j = 0; j < rockAmount; j++) {
				item = ItemFactory.makeRock();
				world.addAtEmptyLocation(item, i);
			}

			// Make food
			for (j = 0; j < foodAmount; j++) {
				item = ItemFactory.makeRandomFood();
				world.addAtEmptyLocation(item, i);
			}

			// Make weapons
			for (j = 0; j < weaponAmount; j++) {
				item = ItemFactory.makeRandomWeapon();
				world.addAtEmptyLocation(item, i);
			}

			// Make armor
			for (j = 0; j < armorAmount; j++) {
				item = ItemFactory.makeRandomArmor();
				world.addAtEmptyLocation(item, i);
			}
		}

		// Make pick
		int pickAmount = (int) (Math.random() * (depth - 1)) + 1;
		for (int j = 0; j < pickAmount; j++) {
			depth = (int) (Math.random() * depth);
			item = ItemFactory.makePick();
			world.addAtEmptyLocation(item, depth);
		}

		// Make legendary great sword
		depth = (int) (Math.random() * depth);
		item = ItemFactory.makeLegendaryGreatSword();
		world.addAtEmptyLocation(item, depth);

		// Make dragonbone armor
		depth = (int) (Math.random() * depth);
		item = ItemFactory.makeDragonboneArmor();
		world.addAtEmptyLocation(item, depth);
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
