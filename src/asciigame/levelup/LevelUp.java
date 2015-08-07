package asciigame.levelup;

import asciigame.creatures.Creature;
import java.util.ArrayList;
import java.util.List;

public class LevelUp {

	private static LevelUpOption[] options = new LevelUpOption[] {
			new LevelUpOption("Increase health") {
				@Override
				public void invoke(Creature creature) {
					creature.gainHealth();
				}
			},

			new LevelUpOption("Increase minimum attack") {
				@Override
				public void invoke(Creature creature) {
					creature.gainMinAttack();
				}
			},

			new LevelUpOption("Increase maximum attack") {
				@Override
				public void invoke(Creature creature) {
					creature.gainMaxAttack();
				}
			},

			new LevelUpOption("Increase defense") {
				@Override
				public void invoke(Creature creature) {
					creature.gainDefense();
				}
			},

			new LevelUpOption("Increase vision") {
				@Override
				public void invoke(Creature creature) {
					creature.gainVision();
				}
			},

			new LevelUpOption("Increase maximum fullness") {
				@Override
				public void invoke(Creature creature) {
					creature.gainFullness();
				}
			}
	};

	public static List<String> getLevelUpOptions() {
		List<String> optionNames = new ArrayList<>();

		for (LevelUpOption option : options) {
			optionNames.add(option.getName());
		}

		return optionNames;
	}

	public static int getLongestOptionName() {
		int max = 0;

		for (LevelUpOption option : options) {
			if (option.getName().length() > max) {
				max = option.getName().length();
			}
		}

		return max;
	}

	public static void invokeOption(Creature player, int option) {
		options[option].invoke(player);
	}

	public static void autoLevelUp(Creature creature){
		options[(int)(Math.random() * options.length)].invoke(creature);
	}
}
