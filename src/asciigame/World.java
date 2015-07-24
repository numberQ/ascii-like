package asciigame;

import asciigame.creatures.Creature;
import asciigame.items.Item;
import asciigame.items.ItemPile;
import asciigame.utility.Point;

public class World {

	private Tile[][][] tiles;
	private int depth;
	private int width;
	private int height;
	private Creature[][][] creatures;
	private ItemPile[][][] items;
	private Point playerSpawn;

	public int getDepth()  { return depth; }
	public int getWidth()  { return width; }
	public int getHeight() { return height; }

	public World(Tile[][][] tiles, Point playerSpawn) {
		this.tiles = tiles;
		this.depth = tiles.length;
		this.width = tiles[0].length;
		this.height = tiles[0][0].length;
		this.creatures = new Creature[depth][width][height];
		this.items = new ItemPile[depth][width][height];
		this.playerSpawn = playerSpawn;
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

	public ItemPile getItems(int z, int x, int y) {
		if (z < 0 || z >= depth || x < 0 || x >= width || y < 0 || y >= height || items[z][x][y] == null) {
			return null;
		}

		return items[z][x][y];
	}

	public Item getTopItem(int z, int x, int y) {
		if (z < 0 || z >= depth || x < 0 || x >= width || y < 0 || y >= height || items[z][x][y] == null) {
			return null;
		}

		return items[z][x][y].getTopItem();
	}

	public void moveCreature(Creature creature, int z, int x, int y) {
		creatures[creature.getZ()][creature.getX()][creature.getY()] = null;
		creatures[z][x][y] = creature;
	}

	public void removeCreature(Creature creature) {
		creatures[creature.getZ()][creature.getX()][creature.getY()] = null;
	}

	public void removeItem(int z, int x, int y, Item item) {
		items[z][x][y].removeItem(item);
	}

	public void dig(int z, int x, int y) {
		if (getTile(z, x, y).isDiggable()) {
			tiles[z][x][y] = Tile.FLOOR;
		}
	}

	public void addPlayer(Creature player) {
		int x = playerSpawn.getX(), y = playerSpawn.getY();
		player.setZ(0);
		player.setX(x);
		player.setY(y);
		creatures[0][x][y] = player;
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
		} while (!getTile(z, x, y).isWalkable() || getTile(z, x, y).isStairs() || !hasNoItems(z, x, y));

		if (items[z][x][y] == null) {
			items[z][x][y] = new ItemPile();
		}
		items[z][x][y].addItem(item);
	}

	public void addAtLocation(Item item, int z, int x, int y) {
		if (items[z][x][y] == null) {
			items[z][x][y] = new ItemPile();
		}
		items[z][x][y].addItem(item);
	}

	public boolean hasNoItems(int z, int x, int y) {
		if (items[z][x][y] == null) {
			return true;
		}

		return items[z][x][y].hasNoItems();
	}

	public void update() {
		Creature creature;
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					creature = creatures[z][x][y];
					if (creature != null && !creature.isUpdated()) {
						creature.update();
						creature.setUpdated(true);
					}
				}
			}
		}

		// Flag all creatures as able to be updated again
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					creature = creatures[z][x][y];
					if (creature != null) {
						creature.setUpdated(false);
					}
				}
			}
		}
	}
}
