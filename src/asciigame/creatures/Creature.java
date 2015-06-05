package asciigame.creatures;

import asciigame.World;
import asciigame.screens.PlayScreen;

import java.awt.*;

public class Creature {

	private World world;
	private int z;
	private int x;
	private int y;
	private char glyph;
	private Color color;
	private CreatureAi ai;
	private int maxHealth;
	private int health;
	private int minAttack;
	private int maxAttack;
	private int defense;
	private String name;

	public int getZ()						 { return z; }
	public void setZ(int z)					 { this.z = z; }
	public int getX() 						 { return x; }
	public void setX(int x)					 { this.x = x; }
	public int getY() 						 { return y; }
	public void setY(int y)					 { this.y = y; }
	public char getGlyph() 					 { return glyph; }
	public Color getColor() 				 { return color; }
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	public int getMaxHealth()				 { return maxHealth; }
	public int getHealth()					 { return health; }
	public int getMinAttack()				 { return minAttack; }
	public int getMaxAttack()				 { return maxAttack; }
	public int getDefense()					 { return defense; }
	public String getName()					 { return name; }

	public Creature(World world, String name, char glyph, Color color, int maxHealth, int minAttack, int maxAttack, int defense) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.minAttack = minAttack;
		this.maxAttack = maxAttack;
		this.defense = defense;
		this.name = name;
	}

	public void moveBy(int moveX, int moveY) {
		moveX = x + moveX;
		moveY = y + moveY;
		ai.onEnter(z, moveX, moveY, world.getTile(z, moveX, moveY));
	}

	public void update() {
		ai.onUpdate();
	}

	public void modifyHealth(int amount) {
		this.health += amount;

		if (health <= 0) {
			health = 0;
			sayAction("die");
			world.killCreature(this);
		}

		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	public void sayAction(String message) {
		int range = 9;
		Creature other;

		for (int dx = -range; dx <= range; dx++) {
			for (int dy = -range; dy <= range; dy++) {

				// Ensures a diamond shape
				if (dx * dx + dy * dy > range * range) {
					continue;
				}

				other = world.getCreature(z, x + dx, y + dy);

				// Ignore empty squares
				if (other == null) {
					continue;
				}

				if (other == this) {
					other.notify("You " + message + ".");
				} else {
					other.notify("The " + getName() + " " + makeSecondPerson(message) + ".");
				}
			}
		}
	}

	private void notify(String message) {
		ai.onNotify(message);
	}

	private String makeSecondPerson(String message) {
		String[] messageArr;
		messageArr = message.split(" ");

		message = messageArr[0] + "s";
		for (int i = 1; i < messageArr.length; i++) {
			message += " " + messageArr[i];
		}
		message = message.replaceAll("the player", "you");

		return message;
	}
}
