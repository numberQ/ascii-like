package asciigame.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {

	private String deathMessage;

	public LoseScreen(String deathMessage) {
		this.deathMessage = deathMessage;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
		terminal.write(deathMessage, 1, 1);
		terminal.writeCenter("Press [enter] to play again.", bylineY);
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
