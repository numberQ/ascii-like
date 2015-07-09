package asciigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldBuilder {

	private int depth;
	private int width;
	private int height;
	private Tile[][][] tiles;
	private int regionId;
	private int smoothTimes;
	private int[][][] regions;
	private Point playerSpawn;

	public WorldBuilder(int depth, int width, int height) {
		this.depth = depth;
		this.width = width;
		this.height = height;
		this.tiles = new Tile[depth][width][height];
		this.regionId = -1;
		this.smoothTimes = 8;
	}

	public World build() {
		return new World(tiles, playerSpawn);
	}

	public WorldBuilder makeWorld() {
		return randomize()
				.smooth(smoothTimes)
				.createRegions()
				.connectRegions()
				.makePlayerSpawn();
	}

	private WorldBuilder randomize() {
		double percent;
		double threshold = 0.5;

		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					percent = Math.random();
					tiles[z][x][y] = (percent < threshold) ? Tile.FLOOR : Tile.WALL;
				}
			}
		}

		return this;
	}

	private WorldBuilder smooth(int times) {
		Tile[][][] tempTiles = new Tile[depth][width][height];
		int floors, walls;

		for (int time = 0; time < times; time++) {

			for (int z = 0; z < depth; z++) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						floors = 0;
						walls = 0;

						for (int dx = -1; dx <= 1; dx++) {
							for (int dy = -1; dy <= 1; dy++) {
								if (x + dx < 0 || x + dx >= width || y + dy < 0 || y + dy >= height) {
									continue;
								}

								if (tiles[z][x + dx][y + dy] == Tile.FLOOR) {
									floors++;
								} else {
									walls++;
								}
							}
						}

						if (floors >= walls) {
							tempTiles[z][x][y] = Tile.FLOOR;
						} else {
							tempTiles[z][x][y] = Tile.WALL;
						}
					}
				}
			}

			tiles = tempTiles;
		}

		return this;
	}

	private WorldBuilder createRegions() {
		regions = new int[depth][width][height];

		// Initialize all cells as having no region
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					regions[z][x][y] = regionId;
				}
			}
		}

		// Find all regions and assign IDs
		int size;
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {

					// A region is an area where you can walk from one end to the other without digging.
					// Also, we only want to fill regions without IDs, hence the -1 check.
					if (tiles[z][x][y].isWalkable() && regions[z][x][y] == -1) {
						regionId++;
						size = fillRegion(z, x, y);

						if (size < 15) {
							removeRegion(z);
							regionId--;
						}
					}
				}
			}
		}

		return this;
	}

	private int fillRegion(int z, int x, int y) {
		int size = 1;
		int nz, nx, ny;
		List<Point> openPoints = new ArrayList<>();
		Point point = new Point(z, x, y);
		openPoints.add(point);
		regions[z][x][y] = regionId;

		while (!openPoints.isEmpty()) {
			point = openPoints.remove(0);

			for (Point neighbor : point.getAdjacent()) {
				nz = neighbor.getZ();
				nx = neighbor.getX();
				ny = neighbor.getY();
				if (nz < 0 || nz >= depth
						|| nx < 0 || nx >= width
						|| ny < 0 || ny >= height) {
					continue;
				}
				if (regions[nz][nx][ny] > -1
						|| tiles[nz][nx][ny] == Tile.WALL) {
					continue;
				}

				size++;
				regions[nz][nx][ny] = regionId;
				openPoints.add(neighbor);
			}
		}

		return size;
	}

	private void removeRegion(int z) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (regions[z][x][y] == regionId) {
					regions[z][x][y] = -1;
					tiles[z][x][y] = Tile.WALL;
				}
			}
		}
	}

	public WorldBuilder connectRegions() {
		for (int z = 0; z < depth - 1; z++) {
			connectRegionsDown(z);
		}

		return this;
	}

	private void connectRegionsDown(int z) {
		List<String> connections = new ArrayList<>();
		String connection;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				connection = regions[z][x][y] + "," + regions[z + 1][x][y];

				if (!connections.contains(connection)
						&& tiles[z][x][y] == Tile.FLOOR
						&& tiles[z + 1][x][y] == Tile.FLOOR) {
					connections.add(connection);
					connectRegionsDown(z, regions[z][x][y], regions[z + 1][x][y]);
				}
			}
		}
	}

	private void connectRegionsDown(int z, int regionUp, int regionDown) {
		List<Point> candidates = findRegionOverlaps(z, regionUp, regionDown);
		Point point;

		int numStairs = 0;
		int stairsRatio = candidates.size();
		do {
			point = candidates.remove(0);
			int x = point.getX();
			int y = point.getY();
			tiles[z][x][y] = Tile.STAIRS_DOWN;
			tiles[z + 1][x][y] = Tile.STAIRS_UP;
			numStairs++;
		} while (candidates.size() / numStairs > stairsRatio);
	}

	private List<Point> findRegionOverlaps(int z, int regionUp, int regionDown) {
		List<Point> candidates = new ArrayList<>();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[z][x][y] == Tile.FLOOR
						&& tiles[z + 1][x][y] == Tile.FLOOR
						&& regions[z][x][y] == regionUp
						&& regions[z + 1][x][y] == regionDown) {
					candidates.add(new Point(z, x, y));
				}
			}
		}

		Collections.shuffle(candidates);
		return candidates;
	}

	private WorldBuilder makePlayerSpawn() {
		int x, y;

		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} while (!tiles[0][x][y].isWalkable() || tiles[0][x][y].isStairs());

		tiles[0][x][y] = Tile.STAIRS_UP;
		playerSpawn = new Point(0, x, y);

		return this;
	}
}
