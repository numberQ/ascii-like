package asciigame.creatures;

import asciigame.World;

public class PlayerAi extends CreatureAi {

	public PlayerAi(World world, Creature creature) {
		super(world, creature);
	}

	@Override
	public void attack(int x, int y) {
		super.attack(x, y);
		Creature other = world.getCreature(x, y);
		creature.say("You attack the " + other.getName() + " for " + damageDealt + " damage!");
	}
}
