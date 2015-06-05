package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.World;

import java.util.List;

public class CreatureFactory {

	private static World world;
	private static int layer;

	public static void setWorld(World world) { CreatureFactory.world = world; }
	public static void setLayer(int layer) { CreatureFactory.layer = layer; }

	public static Creature makePlayer(List<String> messages) {
		Creature player = new Creature(world, "player", '@', AsciiPanel.brightYellow, 50, 5, 10, 1);
		world.addAtEmptyLocation(player, layer);
		new PlayerAi(world, player, messages);
		return player;
	}

	public static Creature makeFungus() {
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green, 20, 1, 5, 0);
		world.addAtEmptyLocation(fungus, layer);
		new FungusAi(world, fungus);
		return fungus;
	}

	public static Creature makeFungus(int x, int y) {
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green, 20, 1, 5, 0);
		world.addAtLocation(fungus, layer, x, y);
		new FungusAi(world, fungus);
		return fungus;
	}
}
