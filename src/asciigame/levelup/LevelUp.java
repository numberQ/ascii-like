package asciigame.levelup;

import asciigame.creatures.Creature;

import java.lang.reflect.Array;
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

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getMinAttack() < creature.getMaxAttack();
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

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getVisionRadius() > 0;
				}
			},

			new LevelUpOption("Increase maximum fullness") {
				@Override
				public void invoke(Creature creature) {
					creature.gainFullness();
				}

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getMaxFullness() > 0;
				}
			},

			new LevelUpOption("Increase spread rate") {
				@Override
				public void invoke(Creature creature) {
					creature.gainSpreadRate();
				}

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getName().equals("fungus");
				}
			},

			new LevelUpOption("Increase attack rate") {
				@Override
				public void invoke(Creature creature) {
					creature.gainAttackRate();
				}

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getName().equals("fungus");
				}
			},

			new LevelUpOption("Increase movement speed") {
				@Override
				public void invoke(Creature creature) {
					creature.gainSpeed();
				}

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getName().equals("bat");
				}
			},

			new LevelUpOption("Increase inventory size") {
				@Override
				public void invoke(Creature creature) {
					creature.gainInventorySize();
				}

				@Override
				public boolean isRelevant(Creature creature) {
					return creature.getInventory() != null;
				}
			}
	};

	public static List<String> getLevelUpOptions(Creature creature) {
		List<String> optionNames = new ArrayList<>();

		for (LevelUpOption option : buildOptions(creature)) {
			if (option != null) {
				optionNames.add(option.getName());
			}
		}

		return optionNames;
	}

	public static int getLongestOptionName(Creature creature) {
		int max = 0;

		for (LevelUpOption option : buildOptions(creature)) {
			if (option.getName().length() > max) {
				max = option.getName().length();
			}
		}

		return max;
	}

	public static void invokeOption(Creature creature, int optionIdx) {
		List<LevelUpOption> builtOptions = buildOptions(creature);
		builtOptions.get(optionIdx).invoke(creature);
	}

	public static void autoLevelUp(Creature creature) {
		List<LevelUpOption> builtOptions = buildOptions(creature);
		int idx = (int)(Math.random() * builtOptions.size());
		builtOptions.get(idx).invoke(creature);
	}

	private static List<LevelUpOption> buildOptions(Creature creature) {
		List<LevelUpOption> builtOptions = new ArrayList<>();

		for (LevelUpOption option : options) {
			if (option.isRelevant(creature)) {
				builtOptions.add(option);
			}
		}

		return builtOptions;
	}
}
