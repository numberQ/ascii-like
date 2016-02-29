package asciigame.screens.targetbasedscreens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import asciigame.screens.Screen;
import java.awt.event.KeyEvent;

public class TargetBasedScreen implements Screen
{

	protected Creature player;
	private int x;
	private int y;
	protected String caption;

	public TargetBasedScreen(Creature player)
	{
		this.player = player;
		this.x = player.getX();
		this.y = player.getY();
		this.caption = "You";
	}

	@Override
	public void displayOutput(AsciiPanel terminal)
	{
		int xx = x, yy = y;

		terminal.write('*', xx, yy, AsciiPanel.brightMagenta);

		xx = 1;
		yy = terminal.getHeightInCharacters() - 3;

		terminal.clear(' ', xx++, yy++, caption.length() + 2, 3);
		terminal.write(caption, xx, yy);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key)
	{
		int xx = x, yy = y;
		switch (key.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H:
				xx--;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L:
				xx++;
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_K:
				yy--;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_J:
				yy++;
				break;
			case KeyEvent.VK_Y:
				xx--;
				yy--;
				break;
			case KeyEvent.VK_U:
				xx++;
				yy--;
				break;
			case KeyEvent.VK_B:
				xx--;
				yy++;
				break;
			case KeyEvent.VK_N:
				xx++;
				yy++;
				break;
		}

		if (isAcceptable(xx, yy))
		{
			x = xx;
			y = yy;
		}

		enterCoords(x, y);

		return null;
	}

	public boolean isAcceptable(int xx, int yy)
	{
		return true;
	}

	public void enterCoords(int xx, int yy)
	{
		// To override if needed
	}
}
