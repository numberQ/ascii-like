package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import asciigame.levelup.LevelUp;
import java.awt.event.KeyEvent;
import java.util.List;

public class LevelUpScreen implements Screen {

	private Creature player;
	private int numPicks;
	private List<String> optionNames;

	public LevelUpScreen(Creature player, int numPicks) {
		this.player = player;
		this.numPicks = numPicks;
		this.optionNames = LevelUp.getLevelUpOptions(player);
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int x = 5;
		int y = 5;
		String levelUpMessage = "Choose " + numPicks + " level up bonus" + (numPicks > 1 ? "es" : "");

		terminal.clear(' ', x - 1, y - 1, LevelUp.getLongestOptionName(player) + 6, optionNames.size() + 4);

		terminal.write(levelUpMessage, x, y++);

		for (int i = 0; i < levelUpMessage.length(); i++) {
			terminal.write('-', x + i, y);
		}
		y++;

		for (int i = 0; i < optionNames.size(); i++) {
			terminal.write("[" + (i + 1) + "] " + optionNames.get(i), x, y++);
		}
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		String choices = "";

		// Build the list of choices. The string will be "1234...",
		// essentially a list of all choice numbers.
		for (int i = 0; i < optionNames.size(); i++) {
			choices += (i + 1);
		}

		// The location in the choices string should correspond to the option selected
		int option = choices.indexOf(key.getKeyChar());

		// If the key pressed was not an option, do nothing
		if (option < 0) {
			return this;
		}

		LevelUp.invokeOption(player, option);

		if (--numPicks < 1) {
			return null;
		} else {
			return this;
		}
	}
}
