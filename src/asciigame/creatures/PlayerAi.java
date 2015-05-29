package asciigame.creatures;

import asciigame.World;

import java.util.List;

public class PlayerAi extends CreatureAi {

	private List<String> messages;

	public PlayerAi(World world, Creature creature, List<String> messages) {
		super(world, creature);
		this.messages = messages;
	}

	@Override
	public void attack(int x, int y) {
		super.attack(x, y);
	}

	@Override
	public void onNotify(String message) {
		messages.add(message);
	}
}
