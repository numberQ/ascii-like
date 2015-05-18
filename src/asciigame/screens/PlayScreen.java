package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.World;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {

	private World world;
	private int centerX;
	private int centerY;
	private int screenWidth;
	private int screenHeight;

	public PlayScreen() {
		
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
		terminal.write("You are now having fun.", 1, 1);
		terminal.writeCenter("Press [enter] to win or [escape] to lose.", bylineY);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				return new WinScreen();
			case KeyEvent.VK_ESCAPE:
				return new LoseScreen();
			default:
				return this;
		}
	}
}
