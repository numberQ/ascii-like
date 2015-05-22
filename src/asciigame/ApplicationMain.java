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
	private static AsciiPanel terminal;
	private static Screen screen;

	public static int getScreenWidth() 		{ return screenWidth; }
	public static int getScreenHeight() 	{ return screenHeight; }
	public static AsciiPanel getTerminal()  { return terminal; }

	public ApplicationMain(){
		super();
		terminal = new AsciiPanel(screenWidth, screenHeight);
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
	}

	@Override
	public void repaint() {
		terminal.clear();
		screen.displayOutput();
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }

	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
