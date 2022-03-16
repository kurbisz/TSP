package org.example;

public class Point {

	private double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getDistance(Point p) {
		return (int) (0.5 + Math.sqrt(pow(x - p.getX()) + pow(y-p.getY())));
	}

	private double pow(double x) {
		return x*x;
	}

}
