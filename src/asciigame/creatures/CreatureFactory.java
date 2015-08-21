package asciigame.creatures;

import asciiPanel.AsciiPanel;
import asciigame.FieldOfView;
import asciigame.World;
import java.util.List;

public class CreatureFactory {

	private static World world;
	public static void setWorld(World world) { CreatureFactory.world = world; }

	public static Creature makePlayer(List<String> messages, FieldOfView fov) {
		int maxHealth = 50, minAttack = 3, maxAttack = 6, defense = 1, visionRadius = 9, invSize = 10, maxFullness = 1000;
		Creature player = new Creature(world, "player", '@', AsciiPanel.brightYellow,
				maxHealth, minAttack, maxAttack, defense, visionRadius, invSize, maxFullness);
		new PlayerAi(world, player, messages, fov);
		return player;
	}

	public static Creature makeFungus() {
		int maxHealth = 5, minAttack = 1, maxAttack = 5, defense = 0, visionRadius = 0, invSize = 0, maxFullness = 0;
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green,
				maxHealth, minAttack, maxAttack, defense, visionRadius, invSize, maxFullness);
		new FungusAi(world, fungus);
		return fungus;
	}

	public static Creature makeFungus(int x, int y) {
		int maxHealth = 5, minAttack = 1, maxAttack = 5, defense = 0, visionRadius = 0, invSize = 0, maxFullness = 0;
		Creature fungus = new Creature(world, "fungus", 'f', AsciiPanel.green,
				maxHealth, minAttack, maxAttack, defense, visionRadius, invSize, maxFullness);
		new FungusAi(world, fungus);
		return fungus;
	}

    public static Creature makeBat() {
        int maxHealth = 15, minAttack = 2, maxAttack = 4, defense = 1, visionRadius = 9, invSize = 0, maxFullness = 750;
        Creature bat = new Creature(world, "bat", 'b', AsciiPanel.yellow,
                maxHealth, minAttack, maxAttack, defense, visionRadius, invSize, maxFullness);
        new BatAi(world, bat);
        return bat;
    }

	public static Creature makeZombie(Creature player) {
		int maxHealth = 30, minAttack = 2, maxAttack = 8, defense = 1, visionRadius = 7, invSize = 0, maxFullness = 0;
		Creature zombie = new Creature (world, "zombie", 'z', AsciiPanel.cyan,
				maxHealth, minAttack, maxAttack, defense, visionRadius, invSize, maxFullness);
		new ZombieAi(world, zombie, player);
		return zombie;
	}
}
