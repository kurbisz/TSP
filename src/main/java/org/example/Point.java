package org.example;

import org.example.drawer.Drawable;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Point extends Ellipse2D.Double implements Drawable {

	private double x, y;
	Color color;

	public enum State{
		VISITED,
		NORMAL
	}

	public Point(double x, double y, State state) {
		this.x = x;
		this.y = y;
		setState(state);
		setFrame(x,y, pointSize, pointSize);
	}

	public Point(double x, double y){
		this.x = x;
		this.y = y;
		setState(State.NORMAL);
		setFrame(x,y, pointSize, pointSize);
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

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setPaint(color);
		g2d.fill(this);
		g2d.draw(this);
	}

	public void setState(State state){
		if(state == State.NORMAL) color = Color.BLACK;
		if(state == State.VISITED) color = Color.RED;
	}
}
