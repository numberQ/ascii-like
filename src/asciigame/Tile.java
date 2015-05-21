package asciigame;

import asciiPanel.AsciiPanel;

import java.awt.*;

public enum Tile {

	FLOOR ('.', AsciiPanel.white),
	WALL ('#', AsciiPanel.white),
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
		if (this == FLOOR) {
			return true;
		}

		return false;
	}

	public boolean isDiggable() {
		if (this == WALL) {
			return true;
		}

		return false;
	}
}
