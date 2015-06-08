package asciigame;

import asciiPanel.AsciiPanel;
import java.awt.*;

public enum Tile {

	FLOOR ('.', AsciiPanel.brightWhite),
	WALL ('#', AsciiPanel.brightWhite),
	STAIRS_DOWN ('<', AsciiPanel.brightYellow),
	STAIRS_UP ('>', AsciiPanel.brightYellow),
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
				return true;
			default:
				return false;
		}
	}
}
