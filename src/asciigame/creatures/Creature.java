package asciigame.creatures;

import asciigame.Tile;
import asciigame.World;
import asciigame.items.Inventory;
import asciigame.items.Item;
import java.awt.*;

public class Creature {

	// <editor-fold desc="Fields">
	private World world;

	private int z;
	private int x;
	private int y;
	public int getZ() { return z; }
	public void setZ(int z)	{ this.z = z; }
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y)	{ this.y = y; }

	private String name;
	public String getName() { return name; }

	private char glyph;
	public char getGlyph() { return glyph; }

	private Color color;
	public Color getColor() { return color; }

	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

	private int maxHealth;
	public int getMaxHealth() { return maxHealth; }

	private int health;
	public int getHealth() { return health; }

	private int minAttack;
	public int getMinAttack() { return minAttack; }

	private int maxAttack;
	public int getMaxAttack() { return maxAttack; }

	private int defense;
	public int getDefense() { return defense; }

	private int visionRadius;
	public int getVisionRadius() { return visionRadius; }

	private Inventory inventory;
	public Inventory getInventory() { return inventory; }

	private int maxFullness;
	public int getMaxFullness() { return maxFullness; }

	private int fullness;

	private int xp;
	public int getXp() { return xp; }

	private int level;
	public int getLevel() { return level; }

	private Item weapon;
	public Item getWeapon() { return weapon; }

	private Item armor;
	public Item getArmor() { return armor; }

	private boolean updated;
	public boolean isUpdated() { return updated; }
	public void setUpdated(boolean u) { this.updated = u; }
	// </editor-fold>

	public Creature(World world, String name, char glyph, Color color,
					int maxHealth, int minAttack, int maxAttack, int defense, int visionRadius, int invSize,
					int maxFullness) {
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
		if (invSize > 0) {
			this.inventory = new Inventory(invSize);
		}
		this.maxFullness = maxFullness;
		this.fullness = maxFullness * 85 / 100;
		this.xp = 0;
		this.level = 1;
		this.updated = false;
	}

	public void moveBy(int moveZ, int moveX, int moveY) {

        // Ignore creatures standing still
        if (moveZ == 0 && moveX == 0 && moveY == 0) {
            return;
        }

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

		ai.onEnter(moveZ, moveX, moveY);
	}

	public void pickup(Item item) {
		if (item == null) {
			sayAction("grab at the ground, but there's nothing there");
		} else if (inventory.isFull()) {
			sayAction("grab at the " + item.getName() + ", but already have a full inventory");
		} else {
			world.removeItem(z, x, y, item);
			inventory.add(item);
			sayAction("pick up the " + item.getName());
		}
	}

	public void drop(Item item) {
		int idx = inventory.find(item);

		// Item not in inventory
		if (idx < 0) {
			notify("You do not have the " + item.getName() + " in your inventory.");
			return;
		}

		inventory.remove(idx);
		world.addAtLocation(item, z, x, y);
		unequip(item);

		sayAction("drop a " + item.getName());
	}

	public void eat(Item item) {

		// Stop player from overeating
		// TODO - let the player overeat, but then force them to sit and hold their stomach until it's digested
		if (fullness + item.getNutrition() > maxFullness) {
			notify("You want to eat the " + item.getName() + ", but it just wouldn't fit in your stomach.");
			return;
		}

		modifyFood(item.getNutrition());
		inventory.remove(inventory.find(item));
		unequip(item);

		sayAction("eat the " + item.getName());
	}

	public String hungerLevel() {
		double hungerRatio = (double)(fullness) / (double)(maxFullness);

		if (hungerRatio < 0.1) {
			return "Starving!";
		}
		if (hungerRatio < 0.2) {
			return "Hungry";
		}
		if (hungerRatio < 0.4) {
			return "Stomach rumbling";
		}
		if (hungerRatio < 0.5) {
			return "Peckish";
		}
		if (hungerRatio < 0.9) {
			return "Full";
		}
		if (hungerRatio <= 1.0) {
			return "Stuffed";
		}

		return "Overstuffed";
	}

	public void equip(Item item) {

		// Equippable items will have attack or defense
		if (item.getAttack() == 0 && item.getDefense() == 0) {
			return;
		}

		// Weapons have higher attack, armor has higher defense
		if (item.getAttack() >= item.getDefense()) {
			unequip(weapon);
			weapon = item;
			sayAction("wield the " + item.getName());
		} else {
			unequip(armor);
			armor = item;
			sayAction("don the " + item.getName());
		}
	}

	public void unequip(Item item) {
		if (item == null) {
			return;
		}

		if (item == weapon) {
			weapon = null;
			sayAction("put away the " + item.getName());
		} else if (item == armor) {
			armor = null;
			sayAction("remove the " + item.getName());
		}
	}

	public boolean hasItem(String itemName) {
		return inventory.find(itemName) > -1;
	}

	public int getItemAttack() {
		int itemAttack = 0;

		if (weapon != null) {
			itemAttack += weapon.getAttack();
		}
		if (armor != null) {
			itemAttack += armor.getAttack();
		}

		return itemAttack;
	}

	public int getItemDefense() {
		int itemDefense = 0;

		if (weapon != null) {
			itemDefense += weapon.getDefense();
		}
		if (armor != null) {
			itemDefense += armor.getDefense();
		}

		return itemDefense;
	}

	public boolean canSee(int worldZ, int worldX, int worldY) {
		return ai.canSee(worldZ, worldX, worldY);
	}

	public boolean canEnter(int worldZ, int worldX, int worldY) {
		return ai.canEnter(worldZ, worldX, worldY);
	}

	public void update() {
		ai.onUpdate();
	}

	public void modifyHealth(int amount) {
		this.health += amount;

		if (health <= 0) {
			health = 0;
			sayAction("die");
			dropLoot();
			world.removeCreature(this);
		}

		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	public void modifyFood(int amount) {
		this.fullness += amount;

		if (fullness <= 0 && maxFullness > 0) {
			fullness = 0;
			sayAction("starve to death");
			modifyHealth(-health);
		}

		if (fullness > maxFullness && isPlayer()) {
			fullness = maxFullness;
		}
	}

	public void modifyXp(int amount) {
		xp += amount;

		notify("You " + (amount < 0 ? "lose " : "gain ") + amount + " experience.");

		while (xp >= nextXpThreshold()) {
			level++;
			sayAction("advance to level " + level);
			ai.onGainLevel();
		}
	}

	public int nextXpThreshold() {
		return ai.nextXpThreshold();
	}

	public void gainHealth() {
		maxHealth += 10;
		health += 10;
		sayAction("look healthier");
	}

	public void gainMinAttack() {
		minAttack += 2;
		if (minAttack > maxAttack) {
			minAttack = maxAttack;
		}
		sayAction("look stronger");
	}

	public void gainMaxAttack() {
		maxAttack += 2;
		sayAction("look stronger");
	}

	public void gainDefense() {
		defense += 2;
		sayAction("look tougher");
	}

	public void gainVision() {
		visionRadius += 1;
		sayAction("look more aware");
	}

	public void gainFullness() {
		maxFullness += 10;
		fullness += 10;
		sayAction("look hungrier");
	}

	public void gainSpreadRate() {
		ai.gainSpreadRate();
		sayAction("look more reproductive");
	}

	public void gainAttackRate() {
		ai.gainAttackRate();
		sayAction("look angrier");
	}

	public void gainSpeed() {
		ai.gainSpeed();
		sayAction("look faster");
	}

	public void gainInventorySize() {
		inventory.increaseMax(2);
		sayAction("look like you can carry more");
	}

	private void dropLoot() {
		int corpseNutrition = maxHealth * 10;
		Item corpse = new Item('%', color, name + " corpse");
		corpse.setNutrition(corpseNutrition);
		world.addAtLocation(corpse, z, x, y);
	}

	public void degradeItem(Item item, int degradeAmount) {
		item.modifyDurability(degradeAmount);

		if (item.isBroken()) {
			sayAction("break your " + item.getName());
			unequip(item);
		}
	}

	public void sayAction(String message) {
		Creature other;

		// Broadcast to every creature on this layer, if they can see it
		for (int worldX = 0; worldX < world.getWidth(); worldX++) {
			for (int worldY = 0; worldY < world.getHeight(); worldY++) {
				other = world.getCreature(z, worldX, worldY);

				// Ignore empty squares
				if (other == null) {
					continue;
				}

				if (other == this) {
					other.notify("You " + message + ".");
				} else if (other.canSee(z, x, y)) {
					other.notify("The " + getName() + " " + switchMessageSubject(message) + ".");
				}
			}
		}
	}

	public void notify(String message) {
		ai.onNotify(message);
	}

	// All calls to sayAction() are written from the point of view of the player,
	// and conjugates verbs to the pronoun 'you'.
	// This switches the grammar for when the subject is not the player.
	private String switchMessageSubject(String message) {
		String[] messageArr;
		messageArr = message.split(" ");

		message = messageArr[0] + "s";
		for (int i = 1; i < messageArr.length; i++) {
			message += " " + messageArr[i];
		}

		// Grammar changes
		message = message.replaceAll("have", "has");
		message = message.replaceAll("is", "are");
		message = message.replaceAll("your", "its");
		message = message.replaceAll("you", "it");

		// Any references to the player should appear as 'you' in the message feed
		message = message.replaceAll("the player", "you");

		return message;
	}

	public boolean isPlayer() {
		return name.equals("player");
	}
}
