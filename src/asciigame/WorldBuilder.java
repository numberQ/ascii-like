package asciigame;

public class WorldBuilder {

	private Tile[][] tiles;
	private int width;
	private int height;
	private int smoothTimes = 8;

	public WorldBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
	}

	public World build() {
		return new World(tiles);
	}

	public WorldBuilder makeWorld() {
		return randomize().smooth(smoothTimes);
	}

	private WorldBuilder randomize() {
		double percent;
		double threshold = 0.5;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				percent = Math.random();
				tiles[x][y] = (percent < threshold) ? Tile.FLOOR : Tile.WALL;
			}
		}

		return this;
	}

	private WorldBuilder smooth(int times) {
		Tile[][] tempTiles = new Tile[width][height];
		int floors, walls;

		for (int time = 0; time < times; time++) {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					floors = 0;
					walls = 0;

					for (int dx = -1; dx <= 1; dx++) {
						for (int dy = -1; dy <= 1; dy++) {
							if (x + dx < 0 || x + dx >= width || y + dy < 0 || y + dy >= height) {
								continue;
							}

							if (tiles[x + dx][y + dy] == Tile.FLOOR) {
								floors++;
							} else {
								walls++;
							}
						}
					}

					if (floors >= walls) {
						tempTiles[x][y] = Tile.FLOOR;
					} else {
						tempTiles[x][y] = Tile.WALL;
					}
				}
			}

			tiles = tempTiles;
		}

		return this;
	}
}
