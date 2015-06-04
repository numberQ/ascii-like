package asciigame;

import asciigame.creatures.Creature;
import java.util.*;

public class World {

	private Tile[][] tiles;
	private int width;
	private int height;
	private List<Creature> creatures;

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public List<Creature> getCreatures() {
		return creatures;
	}

	public World(Tile[][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.creatures = new ArrayList<>();
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.BOUNDS;
		} else {
			return tiles[x][y];
		}
	}

	public Creature getCreature(int x, int y) {
		for (Creature c : creatures) {
			if (c.getX() == x && c.getY() == y) {
				return c;
			}
		}

		return null;
	}

	public void killCreature(Creature creature) {
		if (creature.getName() == "player") {

		}
		creatures.remove(creature);
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
		} while (!getTile(x, y).isWalkable() || getCreature(x, y) != null);

		creature.setX(x);
		creature.setY(y);
		creatures.add(creature);
	}

	public void addAtLocation(Creature creature, int x, int y) {
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
