package asciigame.creatures;

import asciigame.Tile;
import asciigame.World;

public class CreatureAi {

	protected Creature creature;
	protected World world;

	public CreatureAi(World world, Creature creature) {
		this.world = world;
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void onEnter(int x, int y, Tile tile) { }
	public void attack(int x, int y) { }
	public void onUpdate() { }
}
