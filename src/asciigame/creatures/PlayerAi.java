package asciigame.creatures;

import asciigame.Tile;
import asciigame.World;

public class PlayerAi extends CreatureAi {

	public PlayerAi(World world, Creature creature) {
		super(world, creature);
	}

	@Override
	public void onEnter(int x, int y, Tile tile) {
		if (world.getCreature(x, y) != null) {
			attack(x, y);
		} else if (tile.isWalkable()) {
			// If the tile can be walked on, walk on it
			creature.setX(x);
			creature.setY(y);
		} else {
			// Otherwise, attempt to dig
			creature.dig(x, y);
		}
	}

	@Override
	public void attack(int x, int y) {
		Creature other = world.getCreature(x, y);
		world.killCreature(other);
	}
}
