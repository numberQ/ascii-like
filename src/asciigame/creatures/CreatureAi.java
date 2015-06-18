package asciigame.creatures;

import asciigame.Line;
import asciigame.Point;
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

	public void onUpdate() { }

	public void onNotify(String message) { }

	public void onEnter(int z, int x, int y, Tile tile) {
		if (world.getCreature(z, x, y) != null) {
			// If the tile is a creature, attack it
			attack(x, y);
		} else if (tile.isWalkable()) {
			// If the tile can be walked on, walk on it
			creature.setZ(z);
			creature.setX(x);
			creature.setY(y);
		} else {
			// Otherwise, attempt to dig
			dig(z, x, y);
		}
	}

	public void attack(int x, int y) {
		int z = creature.getZ();
		Creature other = world.getCreature(z, x, y);

		if (other != null) {
			int min = creature.getMinAttack();
			int max = creature.getMaxAttack();

			damageDealt = (int) (Math.random() * (max - min)) + min;
			damageDealt = damageDealt - other.getDefense();
			damageDealt = Math.max(0, damageDealt);

			creature.sayAction("attack the " + other.getName() + " for " + damageDealt + " damage");

			other.modifyHealth(-damageDealt);
		}
	}

	public void dig(int z, int x, int y) {
		world.dig(z, x, y);
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
}
