package asciigame.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
		terminal.write("Ascii game", 1, 1);
		terminal.writeCenter("Press [enter] to start.", bylineY);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			return new PlayScreen();
		} else {
			return this;
		}
	}
}
