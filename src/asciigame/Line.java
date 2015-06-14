package asciigame;

import java.util.ArrayList;
import java.util.List;

public class Line {

	private int z;
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	private List<Point> points;
	private double threshold;

	public List<Point> getPoints() { return points; }

	public Line(int z, int x0, int y0, int x1, int y1) {
		this.z = z;
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		points = new ArrayList<>();
		threshold = 0.5;
	}

	public Line construct() {
		/*
			What does any of this mean???
		 */

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		int error = dx - dy;
		int err;

		while (true) {
			points.add(new Point(z, x0, y0));

			if (x0 == x1 && y0 == y1) {
				break;
			}

			err = error * 2;
			if (err > -dx) {
				error -= dy;
				x0 += sx;
			}
			if (err < dx) {
				error += dx;
				y0 += sy;
			}
		}

		return this;
	}
}
