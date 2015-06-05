package asciigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

	private int z, x, y;

	public int getZ() { return z; }
	public int getX() { return x; }
	public int getY() { return y; }

	public Point(int z, int x, int y) {
		this.z = z;
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + z;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof Point)) {
			return false;
		}

		Point other = (Point) object;
		if (z != other.z) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}

		return true;
	}

	public List<Point> getAdjacent() {
		List<Point> points = new ArrayList<>();
		Point point;

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (!(dx == 0 && dy == 0)) {
					point = new Point(z, x + dx, y + dy);
					points.add(point);
				}
			}
		}

		// Shuffle so the first point isn't always the top left one
		Collections.shuffle(points);

		return points;
	}
}
