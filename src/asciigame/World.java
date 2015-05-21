package asciigame;

import asciigame.creatures.Creature;

public class World {

	private Tile[][] tiles;
	private int width;
	private int height;

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public World(Tile[][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.BOUNDS;
		} else {
			return tiles[x][y];
		}
	}

	public void dig(int x, int y) {
		if (getTile(x, y).isDiggable()) {
			tiles[x][y] = Tile.FLOOR;
		}
	}

	public void addAtEmptyLocation(Creature creature) {
		int x, y;

		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} while (!getTile(x, y).isWalkable());

		creature.setX(x);
		creature.setY(y);
	}
}
