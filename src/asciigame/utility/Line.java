package asciigame.utility;

import java.util.ArrayList;
import java.util.List;

public class Line {

	private int z;
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	private List<Point> points;

	public List<Point> getPoints() { return points; }
	public int size() { return points.size(); }

	public Line(Point p1, Point p2) {
		this.z = p1.getZ();
		this.x0 = p1.getX();
		this.y0 = p1.getY();
		this.x1 = p2.getX();
		this.y1 = p2.getY();
		points = new ArrayList<>();
	}

	/*
		Construct a line using Bresenham's line drawing algorithm.
	 */
	public Line construct() {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		int signX = Integer.signum(x1 - x0);
		int signY = Integer.signum(y1 - y0);
		int x, y, exactValue;

		if (dx >= dy) {		// Line is more horizontal than vertical (or perfectly diagonal)
			y = y0;
			exactValue = dx / 2;
			for (x = x0; x != (x1 + signX); x += signX) {
				points.add(new Point(z, x, y));
				exactValue += dy;
				if (exactValue >= dx) {
					exactValue -= dx;
					y += signY;
				}
			}
		} else {			// Line is more vertical than horizontal
			x = x0;
			exactValue = dy / 2;
			for (y = y0; y != (y1 + signY); y += signY) {
				points.add(new Point(z, x, y));
				exactValue += dx;
				if (exactValue >= dy) {
					exactValue -= dy;
					x += signX;
				}
			}
		}

		return this;
	}
}
