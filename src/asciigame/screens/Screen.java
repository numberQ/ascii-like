package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.ApplicationMain;
import java.awt.event.KeyEvent;

public interface Screen {

	AsciiPanel terminal = ApplicationMain.getTerminal();

	void displayOutput();

	Screen respondToUserInput(KeyEvent key);
}
