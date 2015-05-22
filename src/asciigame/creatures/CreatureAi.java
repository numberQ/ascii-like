package asciigame.creatures;

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

	public void onEnter(int x, int y, Tile tile) {
		if (world.getCreature(x, y) != null) {
			// If the tile is a creature, attack it
			attack(x, y);
		} else if (tile.isWalkable()) {
			// If the tile can be walked on, walk on it
			creature.setX(x);
			creature.setY(y);
		} else {
			// Otherwise, attempt to dig
			dig(x, y);
		}
	}

	public void attack(int x, int y) {
		Creature other = world.getCreature(x, y);

		if (other != null) {
			int min = creature.getMinAttack();
			int max = creature.getMaxAttack();

			damageDealt = (int) (Math.random() * (max - min)) + min;
			damageDealt = damageDealt - other.getDefense();
			damageDealt = Math.max(0, damageDealt);

			other.modifyHealth(-damageDealt);

			if (creature.getName() != "player") {
				creature.say("The " + creature.getName() + " attacks the " + other.getName() + " for " + damageDealt + " damage!");
			}
		}
	}

	public void dig(int worldX, int worldY) {
		world.dig(worldX, worldY);
	}
}
