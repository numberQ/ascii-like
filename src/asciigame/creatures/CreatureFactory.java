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
		int maxHealth = 50, minAttack = 5, maxAttack = 10, defense = 1;
		Creature player = new Creature(world, "player", '@', AsciiPanel.brightYellow,
				maxHealth, minAttack, maxAttack, defense);
		world.addAtEmptyLocation(player, layer);
		new PlayerAi(world, player, messages);
		return player;
	}

	public static Creature makeFungus() {
		int maxHealth = 10, minAttack = 1, maxAttack = 4, defense = 0;
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green,
				maxHealth, minAttack, maxAttack, defense);
		world.addAtEmptyLocation(fungus, layer);
		new FungusAi(world, fungus);
		return fungus;
	}

	public static Creature makeFungus(int x, int y) {
		int maxHealth = 10, minAttack = 1, maxAttack = 4, defense = 0;
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green,
				maxHealth, minAttack, maxAttack, defense);
		world.addAtLocation(fungus, layer, x, y);
		new FungusAi(world, fungus);
		return fungus;
	}
}
