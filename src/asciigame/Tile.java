package asciigame;

import asciiPanel.AsciiPanel;
import java.awt.*;

public enum Tile {

	FLOOR ('.', AsciiPanel.brightWhite),
	WALL ('#', AsciiPanel.brightWhite),
	BOUNDS ('X', AsciiPanel.brightBlack);

	private char glyph;
	public char glyph() {
		return glyph;
	}

	private Color color;
	public Color color() {
		return color;
	}

	Tile (char glyph, Color color) {
		this.glyph = glyph;
		this.color = color;
	}

	public boolean isWalkable() {
		switch (this) {
			case FLOOR:
				return true;
			default:
				return false;
		}
	}

	public boolean isDiggable() {
		switch (this) {
			case WALL:
				return true;
			default:
				return false;
		}
	}
}
