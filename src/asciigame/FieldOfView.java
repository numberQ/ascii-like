package asciigame;

public class FieldOfView {

	private World world;
	private int depth;
	private boolean[][] visible;
	private Tile[][][] tiles;

	public FieldOfView(World world) {
		this.world = world;
		this.visible = new boolean[world.getWidth()][world.getHeight()];
		this.tiles = new Tile[world.getDepth()][world.getWidth()][world.getHeight()];

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				for (int z = 0; z < world.getDepth(); z++) {
					tiles[z][x][y] = Tile.UNKNOWN;
				}
				visible[x][y] = false;
			}
		}
	}

	public boolean isVisible(int z, int x, int y) {
		if (z != depth) {
			return false;
		}
		if (x < 0 || x >= world.getWidth()) {
			return false;
		}
		if (y < 0 || y >= world.getHeight()) {
			return false;
		}

		return visible[x][y];
	}

	public void update(int worldZ, int worldX, int worldY, int radius) {
		depth = worldZ;

		for (int x = -radius; x < radius; x++) {
			for (int y = -radius; y < radius; y++) {
				if (x * x + y * y > radius * radius
						|| worldX + x < 0 || worldX + x >= world.getWidth()
						|| worldY + y < 0 || worldY + y >= world.getHeight()) {
					continue;
				}

				Point creaturePoint = new Point(worldZ, worldX, worldY);
				Point lookPoint = new Point (worldZ, worldX + x, worldY + y);
				Line lineOfSight = new Line(creaturePoint, lookPoint).construct();
				boolean hitWall = false;
				for (Point point : lineOfSight.getPoints()) {
					Tile tile = world.getTile(point.getZ(), point.getX(), point.getY());
					visible[point.getX()][point.getY()] = true;
					tiles[point.getZ()][point.getX()][point.getY()] = tile;

					if (hitWall) {
						break;
					}
					if (!tile.isTransparent()) {
						hitWall = true;
					}
				}
			}
		}
	}
}
