package asciigame.screens.targetbasedscreens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import asciigame.screens.Screen;
import java.awt.event.KeyEvent;

public class TargetBasedScreen implements Screen {

	protected Creature player;
	protected String caption;
	private int offsetX;
	private int offsetY;
	private int x;
	private int y;

	public TargetBasedScreen(Creature player, String caption, int offsetX, int offsetY) {
		this.player = player;
		this.caption = caption;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		return null;
	}
}
