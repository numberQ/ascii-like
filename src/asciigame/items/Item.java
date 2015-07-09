package asciigame.items;

import java.awt.*;

public class Item {

	private char glyph;
	private Color color;
	private String name;
	private int nutrition;

	public char getGlyph()		{ return glyph; }
	public Color getColor()		{ return color; }
	public String getName()		{ return name; }
	public int getNutrition()	{ return nutrition; }

	public Item(char glyph, Color color, String name, int nutrition) {
		this.glyph = glyph;
		this.color = color;
		this.name = name;
		this.nutrition = nutrition;
	}
}
