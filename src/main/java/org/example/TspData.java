package org.example;

public class TspData {

	protected int n;
	protected int distance[][];

	public TspData(int n, int dist[][]) {
		this.n = n;
		this.distance = dist;
	}

	public int[][] getDistance() {
		return distance;
	}

	public int getSize() {
		return n;
	}

	/**
	 * Gives distance between two points.
	 * @param from index of 'from point'
	 * @param to index of 'to point'
	 * @return distance if points are valid; -2 otherwise
	 */
	public int getDistance(int from, int to) {
		if(from < 0 || from >= n || to < 0 || to >= n) return -2;
		return distance[from][to];
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
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
		return builder.toString();
	}

}
