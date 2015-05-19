package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.World;
import asciigame.WorldBuilder;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {

	private World world;
	private int centerX;
	private int centerY;
	private int screenWidth;
	private int screenHeight;

	public PlayScreen() {
		/**
		 * TODO
		 * Make these not magic numbers!
		 */
		screenWidth = 80;
		screenHeight = 24;
		createWorld();
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
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

	public int getScrollX() {
		return Math.max(0, Math.min(centerX - screenWidth / 2, world.getWidth() - screenWidth));
	}

	public int getScrollY() {
		return Math.max(0, Math.min(centerY - screenHeight / 2, world.getHeight() - screenHeight));
	}

	private void scrollBy(int scrollX, int scrollY){
		centerX = Math.max(0, Math.min(centerX + scrollX, world.getWidth() - 1));
		centerY = Math.max(0, Math.min(centerY + scrollY, world.getHeight() - 1));
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for (int x = 0; x < screenWidth; x++){
			for (int y = 0; y < screenHeight; y++){
				int worldX = x + left;
				int worldY = y + top;

				terminal.write(world.getTile(worldX, worldY).glyph(), x, y, world.getTile(worldX, worldY).color());
			}
		}
	}
}
