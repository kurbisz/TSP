package org.example.parser;

import org.example.Point;
import org.example.data.EucTspData;
import org.example.data.TspData;

public class EucParser extends Parser {

	public EucParser(String data) {
		super(data);
	}

	@Override
	public TspData getTspData() {
		String spl[] = data.split("\n");
		int dim = -1;
		int counter = 0;
		for(String line : spl) {
			counter++;
			if(line.contains("DIMENSION")) {
				try {
					dim = Integer.parseInt(line.split(":")[1].trim());
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					return null;
				}
			}
			if(line.contains("NODE_COORD_SECTION") || line.contains("NODE_CORD_SECTION")) {
				break;
			}
		}
		if(dim < 0) return null;
		Point points[] = getPoints(spl, counter, dim);
		int distance[][] = new int[dim][dim];
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(i==j) continue;
				distance[i][j] = points[i].getDistance(points[j]);
			}
		}
		return new EucTspData(dim, points, distance);
	}

	private Point[] getPoints(String[] spl, int counter, int dim) {
		Point points[] = new Point[dim];
		for(int i = 0; i < dim; i++) {
			String line[] = spl[i+counter].split(" ");
			points[i] = new Point(Double.parseDouble(line[1]), Double.parseDouble(line[2]));
		}
		return points;
	}


}
