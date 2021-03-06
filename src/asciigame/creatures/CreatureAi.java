package asciigame.creatures;

import asciigame.levelup.LevelUp;
import asciigame.utility.Line;
import asciigame.utility.Point;
import asciigame.Tile;
import asciigame.World;

public class CreatureAi {

	protected Creature creature;
	protected World world;
	protected int damageDealt;

	public CreatureAi(World world, Creature creature) {
		this.world = world;
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void onUpdate() {
		if (creature.isPlayer()) {
			creature.modifyFood(-1);
		}
	}

	public void onNotify(String message) { }

	public void onEnter(int z, int x, int y) {
		Tile tile = world.getTile(z, x, y);

		if (world.getCreature(z, x, y) != null) {

			// If the tile is a creature, attack it
			attack(x, y);
		} else if (canEnter(z, x, y)) {

			// If the tile can be walked on, walk on it
			world.moveCreature(creature, z, x, y);
			creature.setZ(z);
			creature.setX(x);
			creature.setY(y);

			if (creature.isPlayer()) {
				creature.modifyFood(-1);
			}
		} else if (tile.isDiggable()) {

			// If the tile can be dug, dig if we have the pick
			if (creature.getWeapon() != null && creature.getWeapon().getName().equals("pick")) {
				dig(z, x, y);
			}
		}
	}

	public void onGainLevel() {
		LevelUp.autoLevelUp(creature);
	}

	public int nextXpThreshold() {
		int threshold = (int)(Math.pow(creature.getLevel(), 1.5) * 20);
		return threshold;
	}

	public void wander() {
		int dx = (int)(Math.random() * 3) - 1;
        int dy = (int)(Math.random() * 3) - 1;

		creature.moveBy(0, dx, dy);
	}

	public void attack(int x, int y) {
		int z = creature.getZ();
		Creature other = world.getCreature(z, x, y);

		if (other != null && !other.getName().equals(creature.getName())) {
			int min = creature.getMinAttack();
			int max = creature.getMaxAttack();

			// Calculate damage
			// 1. random number between min and max natural attack
			// 2. subtract natural defense
			// 3. add weapon attack
			// 4. subtract armor defense
			// 5. can't go lower than 0
			damageDealt = (int)(Math.random() * (max - min)) + min;
			damageDealt -= other.getDefense();
			damageDealt += creature.getItemAttack();
			damageDealt -= other.getItemDefense();
			damageDealt = Math.max(0, damageDealt);

			// Damage enemy
			creature.sayAction("attack the " + other.getName() + " for " + damageDealt + " damage");
			other.modifyHealth(-damageDealt);

			// Degrade attacker weapons
			if (creature.getWeapon() != null && creature.getWeapon().getAttack() > 0) {
				creature.degradeItem(creature.getWeapon(), -1);
			}
			if (creature.getArmor() != null && creature.getArmor().getAttack() > 0) {
				creature.degradeItem(creature.getArmor(), -1);
			}

			// Degrade defender armor
			if (other.getWeapon() != null && other.getWeapon().getDefense() > 0) {
				other.degradeItem(other.getWeapon(), -3);
			}
			if (other.getArmor() != null && other.getArmor().getDefense() > 0) {
				other.degradeItem(other.getArmor(), -3);
			}

			// Cause hunger
			creature.modifyFood(-10);
			other.modifyFood(-15);

			// Gain experience
			if (other.getHealth() <= 0) {
				gainXp(other);
			}
		}
	}

	private void gainXp(Creature other) {
		int attackAverage = (other.getMinAttack() + other.getMaxAttack()) / 2;
		int amount = other.getMaxHealth() + attackAverage + other.getDefense() - creature.getLevel() * 2;
		amount = Math.max(1, amount);
		creature.modifyXp(amount);
	}

	public void dig(int z, int x, int y) {
		world.dig(z, x, y);
		creature.sayAction("dig through a wall");
		creature.modifyFood(-5);
	}

	public boolean canSee(int z, int x, int y) {

		// Different floors trivially cannot see each other
		if (creature.getZ() != z) {
			return false;
		}

		// Set up circular vision radius
		if ((creature.getX() - x) * (creature.getX() - x) + (creature.getY() - y) * (creature.getY() - y)
				> creature.getVisionRadius() * creature.getVisionRadius()) {
			return false;
		}

		// Check points on line of sight
		Point creaturePoint = new Point(creature.getZ(), creature.getX(), creature.getY());
		Point lookPoint = new Point (z, x, y);
		Line lineOfSight = new Line(creaturePoint, lookPoint).construct();
		boolean hitWall = false;
		for (Point point : lineOfSight.getPoints()) {
			if (!hitWall) {
				if (world.getTile(z, point.getX(), point.getY()).isTransparent()) {
					continue;
				} else {
					hitWall = true;
					continue;
				}
			}
			return false;
		}

		return true;
	}

	public boolean canEnter(int z, int x, int y) {
		Tile tile = world.getTile(z, x, y);
		return tile.isWalkable();
	}

	// Empty level up methods that more specific AIs will handle
	public void gainSpreadRate() { }
	public void gainAttackRate() { }
	public void gainSpeed() { }
}
