package asciigame.items;

import java.awt.*;

public class Item {

	private char glyph;
	public char getGlyph() { return glyph; }

	private Color color;
	public Color getColor()	{ return color; }

	private String name;
	public String getName()	{ return name; }

	private String article;

	private String description;
	public void setDescription(String description) { this.description = description; }

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

	public Item(char glyph, Color color, String name, String article) {
		this.glyph = glyph;
		this.color = color;
		this.name = name;
		this.article = article;
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

	public boolean isBroken() {
		return durability <= 0;
	}

	public String details() {
		String details = "It's ";

		// Add article if it exists
		if (!article.isEmpty()) {
			details += article + " ";
		}
		details += name + ". ";

		boolean special = false;

		// Description
		if (!description.isEmpty())
		{
			details += description + " ";
			special = true;
		}

		// Attack value
		if (getAttack() > 0) {
			details += "It has attack power " + getAttack() + ". ";
			special = true;
		}

		// Defense value
		if (getDefense() > 0) {
			details += "Its defense rating is " + getDefense() + ". ";
			special = true;
		}

		// Food value
		if (getNutrition() > 0) {
			details += "It has " + getNutrition() + " nutrition points. ";
			special = true;
		}

		// Durability
		if (getDurabilityMax() > 0) {
			double percentDurability = (double)getDurability() / getDurabilityMax();
			percentDurability *= 100;
			percentDurability = 100 - percentDurability;
			details += "It is " + (int)percentDurability + "% broken. ";
			special = true;
		}

		// No stats
		if (!special) {
			details = "There doesn't seem to be anything special about it. ";
		}

		return details;
	}
}
