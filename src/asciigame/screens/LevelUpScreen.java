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
		this.optionNames = LevelUp.getLevelUpOptions();
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int x = 5;
		int y = 5;
		String levelUpMessage = "Choose a level up bonus";

		terminal.clear(' ', x - 1, y - 1, LevelUp.getLongestOptionName() + 6, optionNames.size() + 4);

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
		return this;
	}
}
