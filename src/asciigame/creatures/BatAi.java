package asciigame.creatures;

import asciigame.World;

public class BatAi extends CreatureAi {

    private int speed;

    public BatAi(World world, Creature creature) {
        super(world, creature);
        speed = 2;
    }

    @Override
    public void onUpdate() {
        for (int i = 0; i < speed; i++) {
            wander();
        }
    }
}
