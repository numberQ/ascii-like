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
		double dx = x1 - x0;
		double dy = y1 - y0;
		double slope = dy / dx;

		double error = 0;
		if (slope <= 1) {
			int y = y0;
			for (int x = x0; x <= x1; x++) {
				points.add(new Point(z, x, y));

				if (error + slope < threshold) {
					error += slope;
				} else {
					y++;
					error += slope - 1;
				}
			}
		} else {
			int x = x0;
			for (int y = y0; y <= y1; y++) {
				points.add(new Point(z, x, y));

				if (error + slope < threshold) {
					error += slope;
				} else {
					x++;
					error += slope - 1;
				}
			}
		}

		return this;
	}
}
