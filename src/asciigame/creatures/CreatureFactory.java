package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.World;

import java.util.List;

public class CreatureFactory {

	private static World world;

	public static void setWorld(World world) { CreatureFactory.world = world; }

	public static Creature makePlayer(List<String> messages) {
		Creature player = new Creature(world, "player", '@', AsciiPanel.brightYellow, 50, 5, 10, 1);
		world.addAtEmptyLocation(player);
		new PlayerAi(world, player, messages);
		return player;
	}

	public static Creature makeFungus() {
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green, 20, 1, 5, 0);
		world.addAtEmptyLocation(fungus);
		new FungusAi(world, fungus);
		return fungus;
	}

	public static Creature makeFungus(int x, int y) {
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green, 20, 1, 5, 0);
		world.addAtLocation(fungus, x, y);
		new FungusAi(world, fungus);
		return fungus;
	}
}
