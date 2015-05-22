package asciigame.creatures;

import asciigame.World;

public class FungusAi extends CreatureAi {

	private int spreadMax;
	private int spreadCount;
	private double spreadRate;
	private int spreadRange;
	private double attackRate;

	public FungusAi(World world, Creature creature) {
		super(world, creature);
		this.spreadMax = 5;
		this.spreadRate = 0.001;
		this.spreadRange = 5;
		this.attackRate = 1;
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

	@Override
	public void attack(int x, int y) {
		Creature c = world.getCreature(x, y);
		if (c != null && !c.getName().equals("fungus")) {
			super.attack(x, y);
		}
	}

	private void spread() {
		int x, y;

		x = creature.getX() + (int)(Math.random() * (spreadRange * 2 + 1)) - spreadRange;
		y = creature.getY() + (int)(Math.random() * (spreadRange * 2 + 1)) - spreadRange;

		if (!world.getTile(x, y).isWalkable() || world.getCreature(x, y) != null) {
			return;
		}

		CreatureFactory.makeFungus(x, y);
		spreadCount++;
	}

	private void attackAllAdjacent() {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				attack(creature.getX() + dx, creature.getY() + dy);
			}
		}
	}
}
