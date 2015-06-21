package asciigame.items;

import java.awt.*;

public class Item {

	private char glyph;
	private Color color;
	private String name;

	public char getGlyph()  { return this.glyph; }
	public Color getColor() { return this.color; }
	public String getName() { return this.name; }

	public Item(char glyph, Color color, String name) {
		this.glyph = glyph;
		this.color = color;
		this.name = name;
	}
}
