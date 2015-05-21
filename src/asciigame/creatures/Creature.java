package asciigame.creatures;

import asciigame.World;
import java.awt.*;

public class Creature {

	private World world;
	private int x;
	private int y;
	private char glyph;
	private Color color;
	private CreatureAi ai;

	public int getX() 						 { return x; }
	public void setX(int x)					 { this.x = x; }
	public int getY() 						 { return y; }
	public void setY(int y)					 { this.y = y; }
	public char getGlyph() 					 { return glyph; }
	public Color getColor() 				 { return color; }
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

	public Creature(World world, char glyph, Color color) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
	}

	public void dig(int worldX, int worldY) {
		world.dig(worldX, worldY);
	}

	public void moveBy(int moveX, int moveY) {
		moveX = x + moveX;
		moveY = y + moveY;
		ai.onEnter(moveX, moveY, world.getTile(moveX, moveY));
	}

	public void update() {
		ai.onUpdate();
	}
}
