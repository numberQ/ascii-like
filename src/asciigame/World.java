package asciigame;

import asciigame.creatures.Creature;
import java.util.*;

public class World {

	private Tile[][][] tiles;
	private int depth;
	private int width;
	private int height;
	private List<Creature> creatures;

	public int getDepth() {
		return depth;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public List<Creature> getCreatures() {
		return creatures;
	}

	public World(Tile[][][] tiles) {
		this.tiles = tiles;
		this.depth = tiles.length;
		this.width = tiles[0].length;
		this.height = tiles[0][0].length;
		this.creatures = new ArrayList<>();
	}

	public Tile getTile(int z, int x, int y) {
		if (z < 0 || z >= depth || x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.BOUNDS;
		} else {
			return tiles[z][x][y];
		}
	}

	public Creature getCreature(int z, int x, int y) {
		for (Creature c : creatures) {
			if (c.getZ() == z && c.getX() == x && c.getY() == y) {
				return c;
			}
		}

		return null;
	}

	public void killCreature(Creature creature) {
		creatures.remove(creature);
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
		creatures.add(creature);
	}

	public void addAtLocation(Creature creature, int z, int x, int y) {
		creature.setZ(z);
		creature.setX(x);
		creature.setY(y);
		creatures.add(creature);
	}

	public void update() {
		List<Creature> toUpdate = new ArrayList<>(creatures);
		for (Creature c : toUpdate) {
			c.update();
		}
	}
}
