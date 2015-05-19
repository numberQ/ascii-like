package asciigame.creatures;

import asciigame.World;

import java.awt.*;

public class Creature {

	private World world;
	private int x;
	private int y;
	private char glyph;
	private Color color;

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public char getGlyph() {
		return glyph;
	}
	public Color getColor() {
		return color;
	}

	public Creature(World world, char glyph, Color color) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
	}
}
