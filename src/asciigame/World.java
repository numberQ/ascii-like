package asciigame;

import asciigame.creatures.Creature;
import asciigame.items.Item;

import java.util.*;

public class World {

	private Tile[][][] tiles;
	private int depth;
	private int width;
	private int height;
	private Creature[][][] creatures;
	private Item[][][] items;

	public int getDepth() {
		return depth;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public World(Tile[][][] tiles) {
		this.tiles = tiles;
		this.depth = tiles.length;
		this.width = tiles[0].length;
		this.height = tiles[0][0].length;
		this.creatures = new Creature[depth][width][height];
		this.items = new Item[depth][width][height];
	}

	public Tile getTile(int z, int x, int y) {
		if (z < 0 || z >= depth || x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.BOUNDS;
		}

		return tiles[z][x][y];
	}

	public Creature getCreature(int z, int x, int y) {
		if (z < 0 || z >= depth || x < 0 || x >= width || y < 0 || y >= height) {
			return null;
		}

		return creatures[z][x][y];
	}

	public Item getItem(int z, int x, int y) {
		if (z < 0 || z >= depth || x < 0 || x >= width || y < 0 || y >= height) {
			return null;
		}

		return items[z][x][y];
	}

	public void moveCreature(Creature creature, int z, int x, int y) {
		creatures[creature.getZ()][creature.getX()][creature.getY()] = null;
		creatures[z][x][y] = creature;
	}

	public void killCreature(Creature creature) {
		creatures[creature.getZ()][creature.getX()][creature.getY()] = null;
	}

	public void dig(int z, int x, int y) {
		if (getTile(z, x, y).isDiggable()) {
			tiles[z][x][y] = Tile.FLOOR;
		}
	}

	public void addAtEmptyLocation(Creature creature, int z) {
		int x, y;

		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} while (!getTile(z, x, y).isWalkable() || getCreature(z, x, y) != null);

		creature.setZ(z);
		creature.setX(x);
		creature.setY(y);
		creatures[z][x][y] = creature;
	}

	public void addAtLocation(Creature creature, int z, int x, int y) {
		creature.setZ(z);
		creature.setX(x);
		creature.setY(y);
		creatures[z][x][y] = creature;
	}

	public void addAtEmptyLocation(Item item, int z) {
		int x, y;

		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} while (!getTile(z, x, y).isWalkable() || getItem(z, x, y) != null);

		items[z][x][y] = item;
	}

	public void update() {
		Creature creature;
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					creature = creatures[z][x][y];
					if (creature != null) {
						creature.update();
					}
				}
			}
		}
	}
}
