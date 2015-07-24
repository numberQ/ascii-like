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

	public void onUpdate() {
		if (creature.isPlayer()) {
			creature.modifyFood(-1);
		}
	}

	public void onNotify(String message) { }

	public void onEnter(int z, int x, int y, Tile tile) {
		if (world.getCreature(z, x, y) != null) {

			// If the tile is a creature, attack it
			attack(x, y);
		} else if (tile.isWalkable()) {

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
			int addedAttack = creature.getWeapon() == null ? 0 : creature.getWeapon().getAttack();
			addedAttack += creature.getArmor() == null ? 0 : creature.getArmor().getAttack();
			int subtractedDefense = other.getArmor() == null ? 0 : other.getArmor().getDefense();
			subtractedDefense += other.getWeapon() == null ? 0 : other.getWeapon().getDefense();

			damageDealt = (int) (Math.random() * (max - min)) + min;
			damageDealt = damageDealt - other.getDefense();
			damageDealt += addedAttack - subtractedDefense;
			damageDealt = Math.max(0, damageDealt);

			creature.sayAction("attack the " + other.getName() + " for " + damageDealt + " damage");

			other.modifyHealth(-damageDealt);

			creature.modifyFood(-20);
			other.modifyFood(-2);
		}
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
}
