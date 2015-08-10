package asciigame.items;

import java.awt.*;

public class Item {

	private char glyph;
	public char getGlyph() { return glyph; }

	private Color color;
	public Color getColor()	{ return color; }

	private String name;
	public String getName()	{ return name; }

	private int nutrition;
	public int getNutrition() { return nutrition; }
	public void setNutrition(int nutrition) { this.nutrition = nutrition; }

	private int attack;
	public int getAttack() { return attack; }
	public void setAttack(int attack) { this.attack = attack; }

	private int defense;
	public int getDefense() { return defense; }
	public void setDefense(int defense) { this.defense = defense; }

	private int durability;
	public int getDurability() { return durability; }
	public void setDurability(int durability) { this.durability = durability; }

	private int durabilityMax;
	public int getDurabilityMax() { return durabilityMax; }
	public void setDurabilityMax(int durabilityMax) { this.durabilityMax = durabilityMax; }

	public Item(char glyph, Color color, String name) {
		this.glyph = glyph;
		this.color = color;
		this.name = name;
	}

	public void modifyDurability(int amount) {
		durability += amount;

		if (durability > durabilityMax) {
			durability = durabilityMax;
		}

		if (durability < 0) {
			durability = 0;
		}
	}
}
