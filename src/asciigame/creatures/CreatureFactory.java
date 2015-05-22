package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.World;

public class CreatureFactory {

	private static World world;

	public static void setWorld(World world) {
		CreatureFactory.world = world;
	}

	public static Creature makePlayer() {
		Creature player = new Creature(world, '@', AsciiPanel.brightYellow, 50, 10, 5, 1);
		world.addAtEmptyLocation(player);
		new PlayerAi(world, player);
		return player;
	}

	public static Creature makeFungus() {
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 20, 5, 1, 0);
		world.addAtEmptyLocation(fungus);
		new FungusAi(world, fungus);
		return fungus;
	}

	public static Creature makeFungus(int x, int y) {
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 20, 5, 1, 0);
		world.addAtLocation(fungus, x, y);
		new FungusAi(world, fungus);
		return fungus;
	}
}
