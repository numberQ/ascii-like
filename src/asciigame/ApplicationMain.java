package asciigame;

import javax.swing.*;
import asciiPanel.AsciiPanel;
import asciigame.screens.Screen;
import asciigame.screens.StartScreen;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Credit for AsciiPanel and this class goes to:
 * http://trystans.blogspot.com/2011/08/roguelike-tutorial-01-java-eclipse.html
 */
public class ApplicationMain extends JFrame implements KeyListener {

	private static int screenWidth = 80;
	private static int screenHeight = 24;
	private AsciiPanel terminal;
	private static Screen startScreen;

	public ApplicationMain(Screen screen){
		super();
		terminal = new AsciiPanel(screenWidth, screenHeight);
		add(terminal);
		pack();
		startScreen = screen;
		addKeyListener(this);
		repaint();
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	@Override
	public void repaint() {
		terminal.clear();
		startScreen.displayOutput(terminal);
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		startScreen = startScreen.respondToUserInput(e);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public static void main(String[] args) {
		Screen screen = new StartScreen();
		ApplicationMain app = new ApplicationMain(screen);
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
