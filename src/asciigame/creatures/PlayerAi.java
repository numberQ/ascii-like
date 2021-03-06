package asciigame.creatures;

import asciigame.FieldOfView;
import asciigame.World;
import java.util.List;

public class PlayerAi extends CreatureAi {

	private List<String> messages;
	private FieldOfView fov;

	public PlayerAi(World world, Creature creature, List<String> messages, FieldOfView fov) {
		super(world, creature);
		this.messages = messages;
		this.fov = fov;
	}

	@Override
	public void attack(int x, int y) {
		super.attack(x, y);
	}

	@Override
	public void onNotify(String message) {
		messages.add(message);
	}

	@Override
	public void onGainLevel() { }

	@Override
	public boolean canSee(int z, int x, int y) {
		return fov.isVisible(z, x, y);
	}
}
