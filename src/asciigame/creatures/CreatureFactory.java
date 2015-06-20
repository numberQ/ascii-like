package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.FieldOfView;
import asciigame.World;
import java.util.List;

public class CreatureFactory {

	private static World world;
	private static int layer;

	public static void setWorld(World world) { CreatureFactory.world = world; }
	public static void setLayer(int layer) { CreatureFactory.layer = layer; }

	public static Creature makePlayer(List<String> messages, FieldOfView fov) {
		int maxHealth = 1, minAttack = 3, maxAttack = 10, defense = 1, visionRadius = 9;
		Creature player = new Creature(world, "player", '@', AsciiPanel.brightYellow,
				maxHealth, minAttack, maxAttack, defense, visionRadius);
		world.addAtEmptyLocation(player, layer);
		new PlayerAi(world, player, messages, fov);
		return player;
	}

	public static Creature makeFungus() {
		int maxHealth = 5, minAttack = 1, maxAttack = 5, defense = 0, visionRadius = 0;
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green,
				maxHealth, minAttack, maxAttack, defense, visionRadius);
		world.addAtEmptyLocation(fungus, layer);
		new FungusAi(world, fungus);
		return fungus;
	}

	public static Creature makeFungus(int x, int y) {
		int maxHealth = 5, minAttack = 1, maxAttack = 5, defense = 0, visionRadius = 0;
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green,
				maxHealth, minAttack, maxAttack, defense, visionRadius);
		world.addAtLocation(fungus, layer, x, y);
		new FungusAi(world, fungus);
		return fungus;
	}

    public static Creature makeBat() {
        int maxHealth = 15, minAttack = 2, maxAttack = 6, defense = 1, visionRadius = 4;
        Creature bat = new Creature(world, "bat", 'b', AsciiPanel.yellow,
                maxHealth, minAttack, maxAttack, defense, visionRadius);
        world.addAtEmptyLocation(bat, layer);
        new BatAi(world, bat);
        return bat;
    }
}
