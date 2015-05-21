package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.ApplicationMain;
import asciigame.World;
import asciigame.WorldBuilder;
import asciigame.creatures.Creature;
import asciigame.creatures.CreatureFactory;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {

	private int screenWidth;
	private int screenHeight;
	private World world;
	Creature player;

	public PlayScreen() {
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		createWorld();
		CreatureFactory creatureFactory = new CreatureFactory(world);
		player = creatureFactory.makePlayer();
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();

		displayTiles(terminal, left, top);

		terminal.write(player.getGlyph(), player.getX() - left, player.getY() - top, player.getColor());
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
		int width = (int)(screenWidth * 1.5);
		int height = screenHeight * 2;
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
		int border = Math.min(player.getX() - (screenWidth / 2), world.getWidth() - screenWidth);
		return Math.max(0, border);
	}

	/**
	 * Finds the Y coordinate of the top left corner of the scroll window.
	 * The top, in other words.
	 * @return - The Y coord.
	 */
	public int getScrollY() {
		int border = Math.min(player.getY() - (screenHeight / 2), world.getHeight() - screenHeight);
		return Math.max(0, border);
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		// Iterate over the screen
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int worldX = x + left;
				int worldY = y + top;

				terminal.write(world.getTile(worldX, worldY).glyph(), x, y, world.getTile(worldX, worldY).color());
			}
		}
	}
}
