package asciigame.items;

import java.awt.*;

public class Item {

	private char glyph;
	private Color color;
	private String name;
	private int nutrition;
	private int attack;
	private int defense;
	private int durability;
	private int durabilityMax;

	public char getGlyph()							{ return glyph; }
	public Color getColor()							{ return color; }
	public String getName()							{ return name; }
	public int getNutrition()						{ return nutrition; }
	public void setNutrition(int nutrition)			{ this.nutrition = nutrition; }
	public int getAttack()							{ return attack; }
	public void setAttack(int attack)				{ this.attack = attack; }
	public int getDefense()							{ return defense; }
	public void setDefense(int defense)				{ this.defense = defense; }
	public int getDurability()						{ return durability; }
	public void setDurability(int durability)		{ this.durability = durability; }
	public int getDurabilityMax()					{ return durabilityMax; }
	public void setDurabilityMax(int durabilityMax) { this.durabilityMax = durabilityMax; }

	public Item(char glyph, Color color, String name) {
		this.glyph = glyph;
		this.color = color;
		this.name = name;
	}
}
