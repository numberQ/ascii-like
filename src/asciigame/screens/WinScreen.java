package asciigame.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class WinScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
		terminal.write("You won!", 1, 1);
		terminal.writeCenter("Press [enter] to play again.", bylineY);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			return new PlayScreen();
		} else {
			return this;
		}
	}
}
