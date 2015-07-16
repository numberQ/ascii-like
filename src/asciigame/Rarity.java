package asciigame;

public enum Rarity {

	// Due to how it works into calculations in the code,
	// actual rarity is 1 - rarity
	COMMON (0.3),
	UNCOMMON (0.5),
	RARE (0.75),
	VERY_RARE (0.9);

	private double rarity;
	public double getRarity() {
		return rarity;
	}

	Rarity (double rarity) {
		this.rarity = rarity;
	}
}
