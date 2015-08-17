package asciigame.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class HelpScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();

		int x = 1;
		int y = 1;

		terminal.writeCenter("Help", y++);

		String message = "Can you make it to fifth level of the Caves of Slight Danger and recover the magical MacGuffin?";

		printWrapAndReturnLines(message, x, ++y);

		terminal.write("[h] to move left", x, y++);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		return null;
	}

	private String printWrapAndReturnLines(String message, int x, int y) {
		return message;
	}
}
