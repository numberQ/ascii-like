package asciigame.creatures;

import asciigame.World;

public class FungusAi extends CreatureAi {

	private int spreadMax;
	private int spreadCount;
	private double spreadRate;
	private int spreadRange;

	public FungusAi(World world, Creature creature) {
		super(world, creature);
		this.spreadMax = 5;
		this.spreadRate = 0.001;
		this.spreadRange = 5;
	}

	@Override
	public void onUpdate() {
		if (spreadCount < spreadMax && Math.random() < spreadRate) {
			spread();
		}
	}

	public void spread() {
		int x, y;

		x = creature.getX() + (int)(Math.random() * (spreadRange * 2 + 1)) - spreadRange;
		y = creature.getY() + (int)(Math.random() * (spreadRange * 2 + 1)) - spreadRange;

		if (!world.getTile(x, y).isWalkable() || world.getCreature(x, y) != null) {
			return;
		}

		CreatureFactory.makeFungus(x, y);
		spreadCount++;
	}
}