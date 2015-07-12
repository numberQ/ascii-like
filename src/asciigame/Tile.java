package asciigame;

import asciiPanel.AsciiPanel;
import java.awt.*;

public enum Tile {

	// For abnormal characters, see:
	// http://en.wikipedia.org/wiki/Code_page_437
	// Ascii numbers correspond to codes on that page.
	FLOOR ('.', AsciiPanel.brightWhite),
	WALL ('#', AsciiPanel.brightWhite),
	GLASS ('+', AsciiPanel.white),
	STAIRS_UP ((char)24, AsciiPanel.brightYellow),
	STAIRS_DOWN ((char)25, AsciiPanel.brightYellow),
	BOUNDS ('X', AsciiPanel.brightBlack),
	UNKNOWN(' ', AsciiPanel.brightBlack);

	private char glyph;
	public char getGlyph() {
		return glyph;
	}

	private Color color;
	public Color getColor() {
		return color;
	}

	Tile (char glyph, Color color) {
		this.glyph = glyph;
		this.color = color;
	}

	public boolean isWalkable() {
		switch (this) {
			case FLOOR:
			case STAIRS_UP:
			case STAIRS_DOWN:
				return true;
			default:
				return false;
		}
	}

	public boolean isStairs() {
		switch (this) {
			case STAIRS_UP:
			case STAIRS_DOWN:
				return true;
			default:
				return false;
		}
	}

	public boolean isDiggable() {
		switch (this) {
			case WALL:
			case GLASS:
				return true;
			default:
				return false;
		}
	}

	public boolean isTransparent() {
		switch (this) {
			case FLOOR:
			case STAIRS_UP:
			case STAIRS_DOWN:
			case GLASS:
				return true;
			default:
				return false;
		}
	}
}
