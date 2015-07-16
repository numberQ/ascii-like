package asciigame;

public enum Rarity {

	// Due to how it works into calculations in the code,
	// actual rarity is 1 - rarity
	VERY_COMMON (0.1),
	COMMON (0.5),
	UNCOMMON (0.6),
	RARE (0.75),
	VERY_RARE (0.9),
	ULTRA_RARE (0.95);

	private double rarity;
	public double getRarity() {
		return rarity;
	}

	Rarity (double rarity) {
		this.rarity = rarity;
	}
}
