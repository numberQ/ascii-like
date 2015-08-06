package asciigame.levelup;

import asciigame.creatures.Creature;

public class LevelUp {

	private static LevelUpOption[] options = new LevelUpOption[] {
			new LevelUpOption("Increased health") {
				@Override
				public void invoke(Creature creature) {
					creature.gainHealth();
				}
			},

			new LevelUpOption("Increased minimum attack") {
				@Override
				public void invoke(Creature creature) {
					creature.gainMinAttack();
				}
			},

			new LevelUpOption("Increased maximum attack") {
				@Override
				public void invoke(Creature creature) {
					creature.gainMaxAttack();
				}
			},

			new LevelUpOption("Increased defense") {
				@Override
				public void invoke(Creature creature) {
					creature.gainDefense();
				}
			},

			new LevelUpOption("Increased vision") {
				@Override
				public void invoke(Creature creature) {
					creature.gainVision();
				}
			},

			new LevelUpOption("Increased maximum fullness") {
				@Override
				public void invoke(Creature creature) {
					creature.gainFullness();
				}
			}
	};

	public static void autoLevelUp(Creature creature){
		options[(int)(Math.random() * options.length)].invoke(creature);
	}
}
