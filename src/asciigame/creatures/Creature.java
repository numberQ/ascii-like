package asciigame.creatures;

import asciigame.Tile;
import asciigame.World;
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
	private int visionRadius;

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
	public int getVisionRadius()			 { return visionRadius; }

	public Creature(World world, String name, char glyph, Color color, int maxHealth, int minAttack, int maxAttack, int defense, int visionRadius) {
		this.world = world;
		this.name = name;
		this.glyph = glyph;
		this.color = color;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.minAttack = minAttack;
		this.maxAttack = maxAttack;
		this.defense = defense;
		this.visionRadius = visionRadius;
	}

	public void moveBy(int moveZ, int moveX, int moveY) {
		moveX = x + moveX;
		moveY = y + moveY;
		Tile tile = world.getTile(z, moveX, moveY);

		if (moveZ < 0) {
			if (tile == Tile.STAIRS_UP) {
				sayAction("walk up the stairs to level " + (z + moveZ + 1));
			} else {
				sayAction("attempt to go up, but the ceiling is in the way");
				return;
			}
		} else if (moveZ > 0) {
			if (tile == Tile.STAIRS_DOWN) {
				sayAction("walk down the stairs to level " + (z + moveZ + 1));
			} else {
				sayAction("attempt to go down, but the floor is in the way");
				return;
			}
		}

		moveZ = z + moveZ;
		tile = world.getTile(moveZ, moveX, moveY);

		ai.onEnter(moveZ, moveX, moveY, tile);
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
