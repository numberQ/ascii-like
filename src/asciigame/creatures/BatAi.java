package asciigame.creatures;

import asciigame.World;

public class BatAi extends CreatureAi {

    private int speed;
	private double realSpeed;

    public BatAi(World world, Creature creature) {
        super(world, creature);
        realSpeed = 2;
		speed = (int)(realSpeed);
    }

    @Override
    public void onUpdate() {
        for (int i = 0; i < speed; i++) {
            wander();
        }
    }

	@Override
	public void gainSpeed() {
		realSpeed += 0.6;
		speed = (int)(realSpeed);
	}
}
