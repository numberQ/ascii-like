package asciigame.items;

import asciiPanel.AsciiPanel;
import asciigame.Rarity;

public class ItemFactory {

	public static Item makeVictoryItem() {
		Item victoryItem = new Item('*', AsciiPanel.brightYellow, "MacGuffin", "the");
		return victoryItem;
	}

	public static Item makeRock() {
		Item rock = new Item(',', AsciiPanel.yellow, "rock", "a");
		return rock;
	}

	public static Item makePick() {
		int attack = 3;
		int durability = 250;
		Item pick = new Item ('/', AsciiPanel.brightGreen, "pick", "a");
		pick.setAttack(attack);
		pick.setDurability(durability);
		pick.setDurabilityMax(durability);
		return pick;
	}

	public static Item makeCheeseSteak() {
		int nutrition = 150;
		Item cheeseSteak = new Item('c', AsciiPanel.brightCyan, "cheese steak", "a");
		cheeseSteak.setNutrition(nutrition);
		return cheeseSteak;
	}

	public static Item makeSpaghetti() {
		int nutrition = 140;
		Item spaghetti = new Item('s', AsciiPanel.brightCyan, "spaghetti", "a");
		spaghetti.setNutrition(nutrition);
		return spaghetti;
	}

	public static Item makeGranolaBar() {
		int nutrition = 50;
		Item granolaBar = new Item('g', AsciiPanel.brightCyan, "granola bar", "a");
		granolaBar.setNutrition(nutrition);
		return granolaBar;
	}

	public static Item makeAmbrosia() {
		int nutrition = 1000;
		Item ambrosia = new Item('A', AsciiPanel.brightYellow, "ambrosia", "a cup of");
		ambrosia.setNutrition(nutrition);
		return ambrosia;
	}

	public static Item makeTastyRock() {
		int nutrition = 3;
		Item tastyRock = new Item(',', AsciiPanel.brightCyan, "tasty rock", "a");
		tastyRock.setNutrition(nutrition);
		return tastyRock;
	}

	public static Item makeShortSword() {
		int attack = 5;
		int durability = 20;
		Item dagger = new Item('/', AsciiPanel.white, "short sword", "a");
		dagger.setAttack(attack);
		dagger.setDurability(durability);
		dagger.setDurabilityMax(durability);
		return dagger;
	}

	public static Item makeLongSword() {
		int attack = 10;
		int durability = 50;
		Item longSword = new Item('/', AsciiPanel.brightWhite, "long sword", "a");
		longSword.setAttack(attack);
		longSword.setDurability(durability);
		longSword.setDurabilityMax(durability);
		return longSword;
	}

	public static Item makeLegendaryGreatSword() {
		int attack = 20;
		int defense = 10;
		int durability = 150;
		Item legendaryGreatSword = new Item('/', AsciiPanel.brightYellow, "legendary great sword", "the");
		legendaryGreatSword.setAttack(attack);
		legendaryGreatSword.setDefense(defense);
		legendaryGreatSword.setDurability(durability);
		legendaryGreatSword.setDurabilityMax(durability);
		return legendaryGreatSword;
	}

	public static Item makeStaff() {
		int attack = 5;
		int defense = 3;
		int durability = 50;
		Item staff = new Item('/', AsciiPanel.yellow, "staff", "a");
		staff.setAttack(attack);
		staff.setDefense(defense);
		staff.setDurability(durability);
		staff.setDurabilityMax(durability);
		return staff;
	}

	public static Item makeTunic() {
		int defense = 2;
		int durability = 10;
		Item tunic = new Item('[', AsciiPanel.brightGreen, "tunic", "a");
		tunic.setDefense(defense);
		tunic.setDurability(durability);
		tunic.setDurabilityMax(durability);
		return tunic;
	}

	public static Item makeChainmail() {
		int defense = 4;
		int durability = 50;
		Item chainmail = new Item('[', AsciiPanel.white, "chainmail armor", "a set of");
		chainmail.setDefense(defense);
		chainmail.setDurability(durability);
		chainmail.setDurabilityMax(durability);
		return chainmail;
	}

	public static Item makePlatemail() {
		int defense = 7;
		int durability = 100;
		Item platemail = new Item('[', AsciiPanel.brightWhite, "platemail armor", "a set of");
		platemail.setDefense(defense);
		platemail.setDurability(durability);
		platemail.setDurabilityMax(durability);
		return platemail;
	}

	public static Item makeDragonboneArmor() {
		int defense = 20;
		int attack = 10;
		int durability = 150;
		Item dragonboneArmor = new Item('[', AsciiPanel.brightYellow, "dragonbone armor", "a set of");
		dragonboneArmor.setDefense(defense);
		dragonboneArmor.setAttack(attack);
		dragonboneArmor.setDurability(durability);
		dragonboneArmor.setDurabilityMax(durability);
		return dragonboneArmor;
	}

	public static Item makeBaguette() {
		int attack = 4;
		int nutrition = 200;
		int durability = 5;
		Item baguette = new Item('/', AsciiPanel.yellow, "baguette", "a");
		baguette.setAttack(attack);
		baguette.setNutrition(nutrition);
		baguette.setDurability(durability);
		baguette.setDurabilityMax(durability);
		return baguette;
	}

	public static Item makeRandomFood() {
		double percent = Math.random();

		if (percent < Rarity.ULTRA_RARE.getRarity()) {
			return makeAmbrosia();
		}
		if (percent < Rarity.RARE.getRarity()) {
			return makeTastyRock();
		}
		if (percent < Rarity.COMMON.getRarity()) {
			double p = Math.random();
			if (p < Rarity.COMMON.getRarity()) {
				return makeSpaghetti();
			} else {
				return makeCheeseSteak();
			}
		}
		if (percent < Rarity.ULTRA_COMMON.getRarity()) {
			return makeGranolaBar();
		}

		return null;
	}

	public static Item makeRandomWeapon() {
		double percent = Math.random();

		if (percent < Rarity.VERY_RARE.getRarity()) {
			return makeBaguette();
		}
		if (percent < Rarity.UNCOMMON.getRarity()) {
			return makeStaff();
		}
		if (percent < Rarity.COMMON.getRarity()) {
			return makeLongSword();
		}
		if (percent < Rarity.VERY_COMMON.getRarity()) {
			return makeShortSword();
		}

		return null;
	}

	public static Item makeRandomArmor() {
		double percent = Math.random();

		if (percent < Rarity.RARE.getRarity()) {
			return makePlatemail();
		}
		if (percent < Rarity.UNCOMMON.getRarity()) {
			return makeChainmail();
		}
		if (percent < Rarity.ULTRA_COMMON.getRarity()) {
			return makeTunic();
		}

		return null;
	}
}
