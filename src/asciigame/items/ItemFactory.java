package asciigame.items;

import asciiPanel.AsciiPanel;
import asciigame.Rarity;
import asciigame.World;

public class ItemFactory {

	private static World world;
	private static int layer;

	public static void setWorld(World world) { ItemFactory.world = world; }
	public static void setLayer(int layer) { ItemFactory.layer = layer; }

	public static Item makeVictoryItem() {
		Item victoryItem = new Item('*', AsciiPanel.brightYellow, "MacGuffin");
		world.addAtEmptyLocation(victoryItem, world.getDepth() - 1);
		return victoryItem;
	}

	public static Item makeRock() {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, layer);
		return rock;
	}

	public static Item makePick() {
		int attack = 3;
		int durability = 250;
		Item pick = new Item ('/', AsciiPanel.brightGreen, "pick");
		pick.setAttack(attack);
		pick.setDurability(durability);
		pick.setDurabilityMax(durability);
		world.addAtEmptyLocation(pick, layer);
		return pick;
	}

	public static Item makeCheeseSteak() {
		int nutrition = 150;
		Item cheeseSteak = new Item('c', AsciiPanel.brightCyan, "cheese steak");
		cheeseSteak.setNutrition(nutrition);
		world.addAtEmptyLocation(cheeseSteak, layer);
		return cheeseSteak;
	}

	public static Item makeSpaghetti() {
		int nutrition = 140;
		Item spaghetti = new Item('s', AsciiPanel.brightCyan, "spaghetti");
		spaghetti.setNutrition(nutrition);
		world.addAtEmptyLocation(spaghetti, layer);
		return spaghetti;
	}

	public static Item makeGranolaBar() {
		int nutrition = 50;
		Item granolaBar = new Item('g', AsciiPanel.brightCyan, "granola bar");
		granolaBar.setNutrition(nutrition);
		world.addAtEmptyLocation(granolaBar, layer);
		return granolaBar;
	}

	public static Item makeAmbrosia() {
		int nutrition = 1000;
		Item ambrosia = new Item('A', AsciiPanel.brightYellow, "ambrosia");
		ambrosia.setNutrition(nutrition);
		world.addAtEmptyLocation(ambrosia, layer);
		return ambrosia;
	}

	public static Item makeTastyRock() {
		int nutrition = 3;
		Item tastyRock = new Item(',', AsciiPanel.brightCyan, "tasty rock");
		tastyRock.setNutrition(nutrition);
		world.addAtEmptyLocation(tastyRock, layer);
		return tastyRock;
	}

	public static Item makeShortSword() {
		int attack = 5;
		int durability = 20;
		Item dagger = new Item('/', AsciiPanel.white, "short sword");
		dagger.setAttack(attack);
		dagger.setDurability(durability);
		dagger.setDurabilityMax(durability);
		world.addAtEmptyLocation(dagger, layer);
		return dagger;
	}

	public static Item makeLongSword() {
		int attack = 10;
		int durability = 50;
		Item longSword = new Item('/', AsciiPanel.brightWhite, "long sword");
		longSword.setAttack(attack);
		longSword.setDurability(durability);
		longSword.setDurabilityMax(durability);
		world.addAtEmptyLocation(longSword, layer);
		return longSword;
	}

	public static Item makeStaff() {
		int attack = 5;
		int defense = 3;
		int durability = 50;
		Item staff = new Item('/', AsciiPanel.yellow, "staff");
		staff.setAttack(attack);
		staff.setDefense(defense);
		staff.setDurability(durability);
		staff.setDurabilityMax(durability);
		world.addAtEmptyLocation(staff, layer);
		return staff;
	}

	public static Item makeTunic() {
		int defense = 2;
		int durability = 10;
		Item tunic = new Item('[', AsciiPanel.brightGreen, "tunic");
		tunic.setDefense(defense);
		tunic.setDurability(durability);
		tunic.setDurabilityMax(durability);
		world.addAtEmptyLocation(tunic, layer);
		return tunic;
	}

	public static Item makeChainmail() {
		int defense = 4;
		int durability = 50;
		Item chainmail = new Item('[', AsciiPanel.white, "chainmail");
		chainmail.setDefense(defense);
		chainmail.setDurability(durability);
		chainmail.setDurabilityMax(durability);
		world.addAtEmptyLocation(chainmail, layer);
		return chainmail;
	}

	public static Item makePlatemail() {
		int defense = 7;
		int durability = 100;
		Item platemail = new Item('[', AsciiPanel.brightWhite, "platemail");
		platemail.setDefense(defense);
		platemail.setDurability(durability);
		platemail.setDurabilityMax(durability);
		world.addAtEmptyLocation(platemail, layer);
		return platemail;
	}

	public static Item makeBaguette() {
		int attack = 4;
		int nutrition = 200;
		int durability = 5;
		Item baguette = new Item('/', AsciiPanel.yellow, "baguette");
		baguette.setAttack(attack);
		baguette.setNutrition(nutrition);
		baguette.setDurability(durability);
		baguette.setDurabilityMax(durability);
		world.addAtEmptyLocation(baguette, layer);
		return baguette;
	}

	public static Item makeRandomFood() {
		double percent = Math.random();

		if (percent > Rarity.ULTRA_RARE.getRarity()) {
			return makeAmbrosia();
		}
		if (percent > Rarity.VERY_RARE.getRarity()) {
			return makeTastyRock();
		}
		if (percent > Rarity.COMMON.getRarity()) {
			double p = Math.random();
			if (p > Rarity.COMMON.getRarity()) {
				return makeSpaghetti();
			} else {
				return makeCheeseSteak();
			}
		}
		if (percent > Rarity.VERY_COMMON.getRarity()) {
			return makeGranolaBar();
		}

		return null;
	}

	public static Item makeRandomWeapon() {
		double percent = Math.random();

		if (percent > Rarity.VERY_RARE.getRarity()) {
			return makeBaguette();
		}
		if (percent > Rarity.COMMON.getRarity()) {
			double p = Math.random();
			if (p > Rarity.RARE.getRarity()) {
				return makeLongSword();
			} else {
				return makeStaff();
			}
		}
		if (percent > Rarity.VERY_COMMON.getRarity()) {
			return makeShortSword();
		}

		return null;
	}

	public static Item makeRandomArmor() {
		double percent = Math.random();

		if (percent > Rarity.RARE.getRarity()) {
			return makePlatemail();
		}
		if (percent > Rarity.UNCOMMON.getRarity()) {
			return makeChainmail();
		}
		if (percent > Rarity.VERY_COMMON.getRarity()) {
			return makeTunic();
		}

		return null;
	}
}
