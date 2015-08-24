package asciigame;

public enum Rarity {

	FUNGUS_SPAWN_RATE(0.003),
	ULTRA_RARE(0.05),
	VERY_RARE(0.1),
	RARE(0.25),
	UNCOMMON(0.4),
	COMMON(0.5),
	VERY_COMMON(0.7),
	ULTRA_COMMON(0.9);

	private double rarity;
	public double getRarity() { return rarity; }

	Rarity (double rarity) {
		this.rarity = rarity;
	}
}
