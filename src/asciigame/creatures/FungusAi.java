package asciigame.creatures;

import asciigame.Rarity;
import asciigame.World;

public class FungusAi extends CreatureAi {

	private int spreadMax;
	private int spreadCount;
	private double spreadRate;
	private int spreadRange;
	private double attackRate;

	public FungusAi(World world, Creature creature) {
		super(world, creature);
		spreadMax = 3;
		spreadCount = 0;
		spreadRate = Rarity.FUNGUS_SPAWN_RATE.getRarity();
		spreadRange = 5;
		attackRate = Rarity.VERY_COMMON.getRarity();
	}

	@Override
	public void onUpdate() {
		double percent = Math.random();
		if (spreadCount < spreadMax && percent < spreadRate) {
			spread();
		} else if (percent < attackRate) {
			attackAllAdjacent();
		}
	}

	private void spread() {
		int z, x, y;

		z = creature.getZ();
		x = creature.getX() + (int)(Math.random() * (spreadRange * 2 + 1)) - spreadRange;
		y = creature.getY() + (int)(Math.random() * (spreadRange * 2 + 1)) - spreadRange;

		if (!world.getTile(z, x, y).isWalkable() || world.getCreature(z, x, y) != null) {
			return;
		}

		Creature spawn = CreatureFactory.makeFungus();
		world.addAtLocation(spawn, z, x, y);
		creature.sayAction("spawn a child");
		spreadCount++;
	}

	private void attackAllAdjacent() {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				attack(creature.getX() + dx, creature.getY() + dy);
			}
		}
	}

	@Override
	public int nextXpThreshold() {
		int threshold = (int)(Math.pow(creature.getLevel(), 1.5));
		return threshold;
	}

	@Override
	public void gainSpreadRate() {
		spreadCount += 2;
		spreadRate += 0.01;
	}

	@Override
	public void gainAttackRate() {
		attackRate += 0.05;
	}
}
