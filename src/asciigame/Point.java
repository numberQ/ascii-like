package asciigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

	private int x, y, z;

	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }

	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		if (z != other.z) {
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
					point = new Point(x + dx, y + dy, z);
					points.add(point);
				}
			}
		}

		// Shuffle so the first point isn't always the top left one
		Collections.shuffle(points);

		return points;
	}
}
