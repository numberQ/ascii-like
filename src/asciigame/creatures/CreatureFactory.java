package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.World;

public class CreatureFactory {

	private World world;

	public CreatureFactory(World world) {
		this.world = world;
	}

	public Creature makePlayer() {
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
		world.addAtEmptyLocation(player);
		new PlayerAi(player);

		return player;
	}
}
