package asciigame.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public interface Screen {

	void displayOutput(AsciiPanel terminal);

	Screen respondToUserInputAndUpdate(KeyEvent key);
}
