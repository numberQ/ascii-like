package asciigame.creatures;

import asciigame.World;
import asciigame.screens.PlayScreen;

import java.awt.*;

public class Creature {

	private World world;
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

	public Creature(World world, String name, char glyph, Color color, int maxHealth, int maxAttack, int minAttack, int defense) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.maxAttack = maxAttack;
		this.minAttack = minAttack;
		this.defense = defense;
		this.name = name;
	}

	public void moveBy(int moveX, int moveY) {
		moveX = x + moveX;
		moveY = y + moveY;
		ai.onEnter(moveX, moveY, world.getTile(moveX, moveY));
	}

	public void update() {
		ai.onUpdate();
	}

	public void modifyHealth(int amount) {
		this.health += amount;

		if (health <= 0) {
			health = 0;
			world.killCreature(this);
		}

		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	public void say(String message) {
		PlayScreen.addMessage(message);
	}
}
