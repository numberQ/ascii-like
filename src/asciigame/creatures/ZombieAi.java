package asciigame.creatures;

import asciigame.utility.Node;
import asciigame.utility.Path;
import asciigame.utility.Point;
import asciigame.Rarity;
import asciigame.World;

public class ZombieAi extends CreatureAi {

	private Creature player;
	private Point lastKnownPlayerPoint;

	public ZombieAi(World world, Creature creature, Creature player) {
		super(world, creature);
		this.player = player;
	}

	@Override
	public void onUpdate() {

		// There's a chance each turn of not seeing the player the zombie will forget their location
		double forgetChance = Rarity.UNCOMMON.getRarity();
		if (Math.random() > forgetChance) {
			lastKnownPlayerPoint = null;
		}

		// There's a chance each turn that the zombie will do nothing
		double idleChance = Rarity.UNCOMMON.getRarity();
		if (Math.random() > idleChance) {
			return;
		}

		if (creature.canSee(player.getZ(), player.getX(), player.getY())) {
			hunt(player);
			lastKnownPlayerPoint = new Point(player.getZ(), player.getX(), player.getY());
		} else if (lastKnownPlayerPoint != null) {
			hunt(lastKnownPlayerPoint);
		} else {
			wander();
		}
	}

	private void hunt(Creature target) {
		Point zombiePoint = new Point(creature.getZ(), creature.getX(), creature.getY());
		Point goalPoint = new Point(target.getZ(), target.getX(), target.getY());
		Path path = new Path(zombiePoint, goalPoint, creature);
		Node nextNode = path.getNextNode();

		if (nextNode != null) {
			creature.moveBy(0, nextNode.getX() - creature.getX(), nextNode.getY() - creature.getY());
		}
	}

	private void hunt(Point target) {
		Point zombiePoint = new Point(creature.getZ(), creature.getX(), creature.getY());
		Point goalPoint = new Point(target.getZ(), target.getX(), target.getY());
		Path path = new Path(zombiePoint, goalPoint, creature);
		Node nextNode = path.getNextNode();

		if (nextNode != null) {
			creature.moveBy(0, nextNode.getX() - creature.getX(), nextNode.getY() - creature.getY());
		} else {
			creature.sayAction("stay still");
		}
	}
}
