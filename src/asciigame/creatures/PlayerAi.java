package asciigame.creatures;

import asciigame.Tile;

public class PlayerAi extends CreatureAi {

	public PlayerAi(Creature creature) {
		super(creature);
	}

	@Override
	public void onEnter(int x, int y, Tile tile) {
		if (tile.isWalkable()) {
			// If that tile can be walked on, walk on it
			creature.setX(x);
			creature.setY(y);
		} else {
			// Otherwise, attempt to dig
			creature.dig(x, y);
		}
	}
}
