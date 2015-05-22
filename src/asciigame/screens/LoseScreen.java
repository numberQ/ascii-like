package asciigame.screens;

import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {

	@Override
	public void displayOutput() {
		int bylineY = terminal.getHeightInCharacters() - 2;
		terminal.write("You lost...", 1, 1);
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



/**
 *	Maybe stop trying to static-ize ApplicationMain?
 *
 */