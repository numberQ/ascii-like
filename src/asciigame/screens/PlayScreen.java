package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.ApplicationMain;
import asciigame.World;
import asciigame.WorldBuilder;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {

	private int screenWidth;
	private int screenHeight;
	private World world;
	private int centerX;
	private int centerY;

	public PlayScreen() {
		screenWidth = ApplicationMain.getScreenWidth();
		screenHeight = ApplicationMain.getScreenHeight();
		createWorld();
		centerX = world.getWidth() / 2;
		centerY = world.getHeight() / 2;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = screenHeight - 2;
		int left = getScrollX();
		int top = getScrollY();

		displayTiles(terminal, left, top);

		terminal.write('X', centerX - left, centerY - top);

		terminal.writeCenter("Press [enter] to win or [escape] to lose.", bylineY);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				return new WinScreen();
			case KeyEvent.VK_ESCAPE:
				return new LoseScreen();
			case KeyEvent.VK_LEFT:
				scrollBy(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				scrollBy(1, 0);
				break;
			case KeyEvent.VK_UP:
				scrollBy(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				scrollBy(0, 1);
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
	 * @return
	 */
	public int getScrollX() {
		int border = Math.min(centerX - (screenWidth / 2), world.getWidth() - screenWidth);
		return Math.max(0, border);
	}

	/**
	 * Finds the Y coordinate of the top left corner of the scroll window.
	 * The top, in other words.
	 * @return
	 */
	public int getScrollY() {
		int border = Math.min(centerY - (screenHeight / 2), world.getHeight() - screenHeight);
		return Math.max(0, border);
	}

	private void scrollBy(int scrollX, int scrollY){
		int border;

		// Set X - border is on right
		border = Math.min(centerX + scrollX, world.getWidth() - 1);
		centerX = Math.max(0, border);

		// Set Y - border is on bottom
		border = Math.min(centerY + scrollY, world.getHeight() - 1);
		centerY = Math.max(0, border);
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
