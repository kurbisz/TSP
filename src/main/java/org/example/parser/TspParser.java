package org.example.parser;

import org.example.data.TspData;

public class TspParser extends Parser {


	public TspParser(String data) {
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
			if(line.contains("EDGE_WEIGHT_SECTION")) {
				break;
			}
		}
		if(dim < 0) return null;
		int distance[][] = getDistances(spl, counter, dim);
		return new TspData(dim, distance);
	}

	private int[][] getDistances(String[] spl, int counter, int dim) {
		int[][] dist = new int[dim][dim];
		int x = 0, y = 0;
		for(int i = counter; i < spl.length; i++) {
			String str = spl[i];
			String splitLine[] = str.split("\\s+");
			for(String numberStr : splitLine) {
				if(numberStr.equals("")) continue;
				else if(numberStr.equals("EOF")) break;
				int distance = Integer.parseInt(numberStr);
				dist[x][y] = distance;
				dist[y][x] = distance;
				x++;
				if(x == y+1) {
					x = 0;
					y++;
				}
			}
		}
		return dist;
	}

}
