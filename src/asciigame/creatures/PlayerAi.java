package asciigame.creatures;

import asciigame.World;

public class PlayerAi extends CreatureAi {

	public PlayerAi(World world, Creature creature) {
		super(world, creature);
	}

	@Override
	public void attack(int x, int y) {
        Creature other = world.getCreature(x, y);
        String name = other.getName();
		super.attack(x, y);
		creature.say("You attack the " + name + " for " + damageDealt + " damage!");
	}
}
