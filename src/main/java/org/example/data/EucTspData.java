package org.example.data;

import org.example.Point;

public class EucTspData extends TspData {

	private Point[] points;

	public EucTspData(int n, Point[] points, int[][] dist) {
		super(n, dist);
		this.points = points;
	}

	public Point[] getPoints() {
		return points;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Type: Euc2d\n");
		builder.append(n);
		builder.append('\n');
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				builder.append(distance[i][j]);
				if(distance[i][j] < 1000) builder.append('\t');
				builder.append('\t');
			}
			builder.append('\n');
		}
		builder.append('\n');
		for(int i = 0; i < n; i++) {
			builder.append((i+1));
			builder.append(' ');
			builder.append(points[i].getX());
			builder.append(' ');
			builder.append(points[i].getY());
			builder.append('\n');
		}
		return builder.toString();
	}

}
