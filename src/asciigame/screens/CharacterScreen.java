package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import java.awt.event.KeyEvent;

public class CharacterScreen implements Screen {

	private Creature player;

	public CharacterScreen(Creature player) {
		this.player = player;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
		int sheetWidthStart = terminal.getWidthInCharacters() / 6;
		int sheetHeightStart = terminal.getHeightInCharacters() / 6;
		int sheetWidth = terminal.getWidthInCharacters() * 2 / 3;
		int sheetHeight = terminal.getHeightInCharacters() * 2 / 3;

		// Create space
		terminal.clear(' ', 0, bylineY - 1, terminal.getWidthInCharacters() - 1, 3);
		for (int i = 0; i < sheetHeight; i++) {
			if (i == 0 || i == sheetHeight - 1) {
				for (int j = 0; j <= sheetWidth; j++) {
					terminal.write('-', sheetWidthStart + j, sheetHeightStart + i);
				}
			} else {
				terminal.write('|', sheetWidthStart, sheetHeightStart + i);
				terminal.clear(' ', sheetWidthStart + 1, sheetHeightStart + i, sheetWidth - 1, 1);
				terminal.write('|', sheetWidthStart + sheetWidth, sheetHeightStart + i);
			}
		}

		terminal.write("Character sheet", sheetWidthStart + 1, sheetHeightStart + 1);
		terminal.writeCenter("Press [escape] or [enter] to exit.", bylineY);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_ENTER:
				return null;
			default:
				return this;
		}
	}
}
