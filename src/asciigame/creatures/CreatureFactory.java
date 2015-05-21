package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.World;

public class CreatureFactory {

	private World world;

	public CreatureFactory(World world) {
		this.world = world;
	}

	public Creature makePlayer() {
		Creature player = new Creature(world, '@', AsciiPanel.brightYellow);
		world.addAtEmptyLocation(player);
		new PlayerAi(world, player);
		return player;
	}

	public Creature makeFungus() {
		Creature fungus = new Creature(world, 'f', AsciiPanel.green);
		world.addAtEmptyLocation(fungus);
		new FungusAi(world, fungus, this);
		return fungus;
	}

	public Creature makeFungus(int x, int y) {
		Creature fungus = new Creature(world, 'f', AsciiPanel.green);
		world.addAtLocation(fungus, x, y);
		new FungusAi(world, fungus, this);
		return fungus;
	}
}
